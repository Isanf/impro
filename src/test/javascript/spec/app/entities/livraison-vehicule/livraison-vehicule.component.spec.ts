import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { LivraisonVehiculeComponent } from 'app/entities/livraison-vehicule/livraison-vehicule.component';
import { LivraisonVehiculeService } from 'app/entities/livraison-vehicule/livraison-vehicule.service';
import { LivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

describe('Component Tests', () => {
  describe('LivraisonVehicule Management Component', () => {
    let comp: LivraisonVehiculeComponent;
    let fixture: ComponentFixture<LivraisonVehiculeComponent>;
    let service: LivraisonVehiculeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonVehiculeComponent],
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
              }
            }
          }
        ]
      })
        .overrideTemplate(LivraisonVehiculeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivraisonVehiculeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivraisonVehiculeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LivraisonVehicule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.livraisonVehicules && comp.livraisonVehicules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LivraisonVehicule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.livraisonVehicules && comp.livraisonVehicules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
