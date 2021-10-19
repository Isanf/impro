import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { InfoCommandeCarnetASoucheDetailComponent } from 'app/entities/info-commande-carnet-a-souche/info-commande-carnet-a-souche-detail.component';
import { InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

describe('Component Tests', () => {
  describe('InfoCommandeCarnetASouche Management Detail Component', () => {
    let comp: InfoCommandeCarnetASoucheDetailComponent;
    let fixture: ComponentFixture<InfoCommandeCarnetASoucheDetailComponent>;
    const route = ({ data: of({ infoCommandeCarnetASouche: new InfoCommandeCarnetASouche(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [InfoCommandeCarnetASoucheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InfoCommandeCarnetASoucheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InfoCommandeCarnetASoucheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load infoCommandeCarnetASouche on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.infoCommandeCarnetASouche).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
