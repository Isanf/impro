import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PersonnePhysiqueUpdateComponent } from 'app/entities/personne-physique/personne-physique-update.component';
import { PersonnePhysiqueService } from 'app/entities/personne-physique/personne-physique.service';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';

describe('Component Tests', () => {
  describe('PersonnePhysique Management Update Component', () => {
    let comp: PersonnePhysiqueUpdateComponent;
    let fixture: ComponentFixture<PersonnePhysiqueUpdateComponent>;
    let service: PersonnePhysiqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PersonnePhysiqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonnePhysiqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonnePhysiqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonnePhysiqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonnePhysique(123);
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
        const entity = new PersonnePhysique();
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
