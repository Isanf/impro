import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CategorieOrganisationDetailComponent } from 'app/entities/categorie-organisation/categorie-organisation-detail.component';
import { CategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

describe('Component Tests', () => {
  describe('CategorieOrganisation Management Detail Component', () => {
    let comp: CategorieOrganisationDetailComponent;
    let fixture: ComponentFixture<CategorieOrganisationDetailComponent>;
    const route = ({ data: of({ categorieOrganisation: new CategorieOrganisation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CategorieOrganisationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategorieOrganisationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieOrganisationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieOrganisation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieOrganisation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
