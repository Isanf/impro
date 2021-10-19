import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { ImmatriculationDetailComponent } from 'app/entities/immatriculation/immatriculation-detail.component';
import { Immatriculation } from 'app/shared/model/immatriculation.model';

describe('Component Tests', () => {
  describe('Immatriculation Management Detail Component', () => {
    let comp: ImmatriculationDetailComponent;
    let fixture: ComponentFixture<ImmatriculationDetailComponent>;
    const route = ({ data: of({ immatriculation: new Immatriculation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [ImmatriculationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImmatriculationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImmatriculationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load immatriculation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.immatriculation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
