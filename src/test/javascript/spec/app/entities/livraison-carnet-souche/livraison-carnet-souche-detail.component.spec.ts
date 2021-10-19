import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { LivraisonCarnetSoucheDetailComponent } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche-detail.component';
import { LivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

describe('Component Tests', () => {
  describe('LivraisonCarnetSouche Management Detail Component', () => {
    let comp: LivraisonCarnetSoucheDetailComponent;
    let fixture: ComponentFixture<LivraisonCarnetSoucheDetailComponent>;
    const route = ({ data: of({ livraisonCarnetSouche: new LivraisonCarnetSouche(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonCarnetSoucheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LivraisonCarnetSoucheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivraisonCarnetSoucheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load livraisonCarnetSouche on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.livraisonCarnetSouche).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
