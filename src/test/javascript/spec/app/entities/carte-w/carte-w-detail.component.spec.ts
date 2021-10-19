import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CarteWDetailComponent } from 'app/entities/carte-w/carte-w-detail.component';
import { CarteW } from 'app/shared/model/carte-w.model';

describe('Component Tests', () => {
  describe('CarteW Management Detail Component', () => {
    let comp: CarteWDetailComponent;
    let fixture: ComponentFixture<CarteWDetailComponent>;
    const route = ({ data: of({ carteW: new CarteW(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CarteWDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CarteWDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarteWDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load carteW on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carteW).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
