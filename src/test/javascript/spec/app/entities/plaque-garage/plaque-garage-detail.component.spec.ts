import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PlaqueGarageDetailComponent } from 'app/entities/plaque-garage/plaque-garage-detail.component';
import { PlaqueGarage } from 'app/shared/model/plaque-garage.model';

describe('Component Tests', () => {
  describe('PlaqueGarage Management Detail Component', () => {
    let comp: PlaqueGarageDetailComponent;
    let fixture: ComponentFixture<PlaqueGarageDetailComponent>;
    const route = ({ data: of({ plaqueGarage: new PlaqueGarage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PlaqueGarageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlaqueGarageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlaqueGarageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load plaqueGarage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plaqueGarage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
