import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CategorieOrganisationUpdateComponent } from 'app/entities/categorie-organisation/categorie-organisation-update.component';
import { CategorieOrganisationService } from 'app/entities/categorie-organisation/categorie-organisation.service';
import { CategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

describe('Component Tests', () => {
  describe('CategorieOrganisation Management Update Component', () => {
    let comp: CategorieOrganisationUpdateComponent;
    let fixture: ComponentFixture<CategorieOrganisationUpdateComponent>;
    let service: CategorieOrganisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CategorieOrganisationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategorieOrganisationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieOrganisationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieOrganisationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorieOrganisation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorieOrganisation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
