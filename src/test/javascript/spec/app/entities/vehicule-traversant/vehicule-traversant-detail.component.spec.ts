import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeTraversantDetailComponent } from 'app/entities/vehicule-traversant/vehicule-traversant-detail.component';
import { VehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';

describe('Component Tests', () => {
  describe('VehiculeTraversant Management Detail Component', () => {
    let comp: VehiculeTraversantDetailComponent;
    let fixture: ComponentFixture<VehiculeTraversantDetailComponent>;
    const route = ({ data: of({ vehiculeTraversant: new VehiculeTraversant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeTraversantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VehiculeTraversantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehiculeTraversantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vehiculeTraversant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vehiculeTraversant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
