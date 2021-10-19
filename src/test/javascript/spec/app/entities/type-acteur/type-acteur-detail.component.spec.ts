import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeActeurDetailComponent } from 'app/entities/type-acteur/type-acteur-detail.component';
import { TypeActeur } from 'app/shared/model/type-acteur.model';

describe('Component Tests', () => {
  describe('TypeActeur Management Detail Component', () => {
    let comp: TypeActeurDetailComponent;
    let fixture: ComponentFixture<TypeActeurDetailComponent>;
    const route = ({ data: of({ typeActeur: new TypeActeur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeActeurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeActeurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeActeurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeActeur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeActeur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
