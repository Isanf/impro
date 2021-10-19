import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { OrganisationLocaliteDetailComponent } from 'app/entities/organisation-localite/organisation-localite-detail.component';
import { OrganisationLocalite } from 'app/shared/model/organisation-localite.model';

describe('Component Tests', () => {
  describe('OrganisationLocalite Management Detail Component', () => {
    let comp: OrganisationLocaliteDetailComponent;
    let fixture: ComponentFixture<OrganisationLocaliteDetailComponent>;
    const route = ({ data: of({ organisationLocalite: new OrganisationLocalite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [OrganisationLocaliteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrganisationLocaliteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrganisationLocaliteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load organisationLocalite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.organisationLocalite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
