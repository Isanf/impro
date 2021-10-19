import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CarnetASoucheDetailComponent } from 'app/entities/carnet-a-souche/carnet-a-souche-detail.component';
import { CarnetASouche } from 'app/shared/model/carnet-a-souche.model';

describe('Component Tests', () => {
  describe('CarnetASouche Management Detail Component', () => {
    let comp: CarnetASoucheDetailComponent;
    let fixture: ComponentFixture<CarnetASoucheDetailComponent>;
    const route = ({ data: of({ carnetASouche: new CarnetASouche(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CarnetASoucheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarnetASoucheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarnetASoucheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load carnetASouche on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carnetASouche).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
