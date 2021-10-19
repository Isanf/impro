import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeVehiculeDetailComponent } from 'app/entities/type-vehicule/type-vehicule-detail.component';
import { TypeVehicule } from 'app/shared/model/type-vehicule.model';

describe('Component Tests', () => {
  describe('TypeVehicule Management Detail Component', () => {
    let comp: TypeVehiculeDetailComponent;
    let fixture: ComponentFixture<TypeVehiculeDetailComponent>;
    const route = ({ data: of({ typeVehicule: new TypeVehicule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeVehiculeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeVehiculeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeVehiculeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeVehicule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeVehicule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
