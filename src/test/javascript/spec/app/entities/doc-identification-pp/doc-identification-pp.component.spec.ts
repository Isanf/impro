import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap, Data } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { DocIdentificationPPComponent } from 'app/entities/doc-identification-pp/doc-identification-pp.component';
import { DocIdentificationPPService } from 'app/entities/doc-identification-pp/doc-identification-pp.service';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

describe('Component Tests', () => {
  describe('DocIdentificationPP Management Component', () => {
    let comp: DocIdentificationPPComponent;
    let fixture: ComponentFixture<DocIdentificationPPComponent>;
    let service: DocIdentificationPPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [DocIdentificationPPComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              },
              queryParamMap: {
                subscribe: (fn: (value: Data) => void) =>
                  fn(
                    convertToParamMap({
                      page: '1',
                      size: '1',
                      sort: 'id,desc'
                    })
                  )
              }
            }
          }
        ]
      })
        .overrideTemplate(DocIdentificationPPComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocIdentificationPPComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocIdentificationPPService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocIdentificationPP(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.docIdentificationPPS && comp.docIdentificationPPS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocIdentificationPP(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.docIdentificationPPS && comp.docIdentificationPPS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
