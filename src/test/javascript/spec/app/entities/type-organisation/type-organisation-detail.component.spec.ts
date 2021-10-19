import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeOrganisationDetailComponent } from 'app/entities/type-organisation/type-organisation-detail.component';
import { TypeOrganisation } from 'app/shared/model/type-organisation.model';

describe('Component Tests', () => {
  describe('TypeOrganisation Management Detail Component', () => {
    let comp: TypeOrganisationDetailComponent;
    let fixture: ComponentFixture<TypeOrganisationDetailComponent>;
    const route = ({ data: of({ typeOrganisation: new TypeOrganisation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeOrganisationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeOrganisationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeOrganisationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeOrganisation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeOrganisation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
