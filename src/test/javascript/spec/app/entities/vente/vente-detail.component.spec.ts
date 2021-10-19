import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VenteDetailComponent } from 'app/entities/vente/vente-detail.component';
import { Vente } from 'app/shared/model/vente.model';

describe('Component Tests', () => {
  describe('Vente Management Detail Component', () => {
    let comp: VenteDetailComponent;
    let fixture: ComponentFixture<VenteDetailComponent>;
    const route = ({ data: of({ vente: new Vente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
