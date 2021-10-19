import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { MarqueVehiculeDetailComponent } from 'app/entities/marque-vehicule/marque-vehicule-detail.component';
import { MarqueVehicule } from 'app/shared/model/marque-vehicule.model';

describe('Component Tests', () => {
  describe('MarqueVehicule Management Detail Component', () => {
    let comp: MarqueVehiculeDetailComponent;
    let fixture: ComponentFixture<MarqueVehiculeDetailComponent>;
    const route = ({ data: of({ marqueVehicule: new MarqueVehicule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [MarqueVehiculeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MarqueVehiculeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MarqueVehiculeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load marqueVehicule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.marqueVehicule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
