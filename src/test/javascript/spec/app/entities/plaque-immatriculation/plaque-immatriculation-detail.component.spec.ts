import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PlaqueImmatriculationDetailComponent } from 'app/entities/plaque-immatriculation/plaque-immatriculation-detail.component';
import { PlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

describe('Component Tests', () => {
  describe('PlaqueImmatriculation Management Detail Component', () => {
    let comp: PlaqueImmatriculationDetailComponent;
    let fixture: ComponentFixture<PlaqueImmatriculationDetailComponent>;
    const route = ({ data: of({ plaqueImmatriculation: new PlaqueImmatriculation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PlaqueImmatriculationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlaqueImmatriculationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlaqueImmatriculationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plaqueImmatriculation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plaqueImmatriculation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
