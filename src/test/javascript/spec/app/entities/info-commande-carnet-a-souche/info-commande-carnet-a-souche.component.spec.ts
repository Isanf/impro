import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap, Data } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { InfoCommandeCarnetASoucheComponent } from 'app/entities/info-commande-carnet-a-souche/info-commande-carnet-a-souche.component';
import { InfoCommandeCarnetASoucheService } from 'app/entities/info-commande-carnet-a-souche/info-commande-carnet-a-souche.service';
import { InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

describe('Component Tests', () => {
  describe('InfoCommandeCarnetASouche Management Component', () => {
    let comp: InfoCommandeCarnetASoucheComponent;
    let fixture: ComponentFixture<InfoCommandeCarnetASoucheComponent>;
    let service: InfoCommandeCarnetASoucheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [InfoCommandeCarnetASoucheComponent],
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
        .overrideTemplate(InfoCommandeCarnetASoucheComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InfoCommandeCarnetASoucheComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InfoCommandeCarnetASoucheService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InfoCommandeCarnetASouche(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.infoCommandeCarnetASouches && comp.infoCommandeCarnetASouches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InfoCommandeCarnetASouche(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.infoCommandeCarnetASouches && comp.infoCommandeCarnetASouches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
