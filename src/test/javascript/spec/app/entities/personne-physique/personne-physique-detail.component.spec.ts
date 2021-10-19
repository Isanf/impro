import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PersonnePhysiqueDetailComponent } from 'app/entities/personne-physique/personne-physique-detail.component';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';

describe('Component Tests', () => {
  describe('PersonnePhysique Management Detail Component', () => {
    let comp: PersonnePhysiqueDetailComponent;
    let fixture: ComponentFixture<PersonnePhysiqueDetailComponent>;
    const route = ({ data: of({ personnePhysique: new PersonnePhysique(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PersonnePhysiqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonnePhysiqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonnePhysiqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load personnePhysique on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personnePhysique).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
