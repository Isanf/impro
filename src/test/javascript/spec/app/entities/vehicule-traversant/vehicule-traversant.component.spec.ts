import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { VehiculeTraversantComponent } from 'app/entities/vehicule-traversant/vehicule-traversant.component';
import { VehiculeTraversantService } from 'app/entities/vehicule-traversant/vehicule-traversant.service';
import { VehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';

describe('Component Tests', () => {
  describe('VehiculeTraversant Management Component', () => {
    let comp: VehiculeTraversantComponent;
    let fixture: ComponentFixture<VehiculeTraversantComponent>;
    let service: VehiculeTraversantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeTraversantComponent],
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
        .overrideTemplate(VehiculeTraversantComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehiculeTraversantComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeTraversantService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehiculeTraversant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehiculeTraversants && comp.vehiculeTraversants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehiculeTraversant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehiculeTraversants && comp.vehiculeTraversants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
