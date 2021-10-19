import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PersonneMoraleDetailComponent } from 'app/entities/personne-morale/personne-morale-detail.component';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';

describe('Component Tests', () => {
  describe('PersonneMorale Management Detail Component', () => {
    let comp: PersonneMoraleDetailComponent;
    let fixture: ComponentFixture<PersonneMoraleDetailComponent>;
    const route = ({ data: of({ personneMorale: new PersonneMorale(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PersonneMoraleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonneMoraleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonneMoraleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personneMorale).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
