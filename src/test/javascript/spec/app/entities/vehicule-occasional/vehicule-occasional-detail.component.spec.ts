import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeOccasionalDetailComponent } from 'app/entities/vehicule-occasional/vehicule-occasional-detail.component';
import { VehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';

describe('Component Tests', () => {
  describe('VehiculeOccasional Management Detail Component', () => {
    let comp: VehiculeOccasionalDetailComponent;
    let fixture: ComponentFixture<VehiculeOccasionalDetailComponent>;
    const route = ({ data: of({ vehiculeOccasional: new VehiculeOccasional(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeOccasionalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VehiculeOccasionalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehiculeOccasionalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehiculeOccasional on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehiculeOccasional).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
