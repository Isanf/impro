import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PersonneMoraleUpdateComponent } from 'app/entities/personne-morale/personne-morale-update.component';
import { PersonneMoraleService } from 'app/entities/personne-morale/personne-morale.service';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';

describe('Component Tests', () => {
  describe('PersonneMorale Management Update Component', () => {
    let comp: PersonneMoraleUpdateComponent;
    let fixture: ComponentFixture<PersonneMoraleUpdateComponent>;
    let service: PersonneMoraleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PersonneMoraleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonneMoraleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonneMoraleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonneMoraleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonneMorale(123);
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
        const entity = new PersonneMorale();
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
