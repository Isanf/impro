import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { OrganisationUpdateComponent } from 'app/entities/organisation/organisation-update.component';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { Organisation } from 'app/shared/model/organisation.model';

describe('Component Tests', () => {
  describe('Organisation Management Update Component', () => {
    let comp: OrganisationUpdateComponent;
    let fixture: ComponentFixture<OrganisationUpdateComponent>;
    let service: OrganisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [OrganisationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrganisationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganisationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Organisation(123);
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
        const entity = new Organisation();
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
