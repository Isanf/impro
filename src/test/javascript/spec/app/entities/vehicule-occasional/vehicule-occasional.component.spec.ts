import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { VehiculeOccasionalComponent } from 'app/entities/vehicule-occasional/vehicule-occasional.component';
import { VehiculeOccasionalService } from 'app/entities/vehicule-occasional/vehicule-occasional.service';
import { VehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';

describe('Component Tests', () => {
  describe('VehiculeOccasional Management Component', () => {
    let comp: VehiculeOccasionalComponent;
    let fixture: ComponentFixture<VehiculeOccasionalComponent>;
    let service: VehiculeOccasionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeOccasionalComponent],
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
        .overrideTemplate(VehiculeOccasionalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehiculeOccasionalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeOccasionalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehiculeOccasional(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehiculeOccasionals && comp.vehiculeOccasionals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VehiculeOccasional(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vehiculeOccasionals && comp.vehiculeOccasionals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
