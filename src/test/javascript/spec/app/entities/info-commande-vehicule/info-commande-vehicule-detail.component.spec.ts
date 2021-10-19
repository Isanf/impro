import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { InfoCommandeVehiculeDetailComponent } from 'app/entities/info-commande-vehicule/info-commande-vehicule-detail.component';
import { InfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

describe('Component Tests', () => {
  describe('InfoCommandeVehicule Management Detail Component', () => {
    let comp: InfoCommandeVehiculeDetailComponent;
    let fixture: ComponentFixture<InfoCommandeVehiculeDetailComponent>;
    const route = ({ data: of({ infoCommandeVehicule: new InfoCommandeVehicule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [InfoCommandeVehiculeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InfoCommandeVehiculeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InfoCommandeVehiculeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load infoCommandeVehicule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.infoCommandeVehicule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
