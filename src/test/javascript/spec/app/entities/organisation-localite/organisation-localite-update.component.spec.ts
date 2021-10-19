import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { OrganisationLocaliteUpdateComponent } from 'app/entities/organisation-localite/organisation-localite-update.component';
import { OrganisationLocaliteService } from 'app/entities/organisation-localite/organisation-localite.service';
import { OrganisationLocalite } from 'app/shared/model/organisation-localite.model';

describe('Component Tests', () => {
  describe('OrganisationLocalite Management Update Component', () => {
    let comp: OrganisationLocaliteUpdateComponent;
    let fixture: ComponentFixture<OrganisationLocaliteUpdateComponent>;
    let service: OrganisationLocaliteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [OrganisationLocaliteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrganisationLocaliteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisationLocaliteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganisationLocaliteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrganisationLocalite(123);
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
        const entity = new OrganisationLocalite();
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
