import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { LivraisonVehiculeDetailComponent } from 'app/entities/livraison-vehicule/livraison-vehicule-detail.component';
import { LivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

describe('Component Tests', () => {
  describe('LivraisonVehicule Management Detail Component', () => {
    let comp: LivraisonVehiculeDetailComponent;
    let fixture: ComponentFixture<LivraisonVehiculeDetailComponent>;
    const route = ({ data: of({ livraisonVehicule: new LivraisonVehicule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonVehiculeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LivraisonVehiculeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivraisonVehiculeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load livraisonVehicule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.livraisonVehicule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
