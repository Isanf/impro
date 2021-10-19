import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { PrixCertificatComponent } from 'app/entities/prix-certificat/prix-certificat.component';
import { PrixCertificatService } from 'app/entities/prix-certificat/prix-certificat.service';
import { PrixCertificat } from 'app/shared/model/prix-certificat.model';

describe('Component Tests', () => {
  describe('PrixCertificat Management Component', () => {
    let comp: PrixCertificatComponent;
    let fixture: ComponentFixture<PrixCertificatComponent>;
    let service: PrixCertificatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PrixCertificatComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc'
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc'
                })
              )
            }
          }
        ]
      })
        .overrideTemplate(PrixCertificatComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrixCertificatComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrixCertificatService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PrixCertificat(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prixCertificats && comp.prixCertificats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PrixCertificat(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prixCertificats && comp.prixCertificats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
