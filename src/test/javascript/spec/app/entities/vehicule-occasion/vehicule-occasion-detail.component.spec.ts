import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeOccasionDetailComponent } from 'app/entities/vehicule-occasion/vehicule-occasion-detail.component';
import { VehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';

describe('Component Tests', () => {
  describe('VehiculeOccasion Management Detail Component', () => {
    let comp: VehiculeOccasionDetailComponent;
    let fixture: ComponentFixture<VehiculeOccasionDetailComponent>;
    const route = ({ data: of({ vehiculeOccasion: new VehiculeOccasion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeOccasionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VehiculeOccasionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehiculeOccasionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehiculeOccasion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehiculeOccasion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
