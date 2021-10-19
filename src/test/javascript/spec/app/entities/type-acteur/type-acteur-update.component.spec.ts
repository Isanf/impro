import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeActeurUpdateComponent } from 'app/entities/type-acteur/type-acteur-update.component';
import { TypeActeurService } from 'app/entities/type-acteur/type-acteur.service';
import { TypeActeur } from 'app/shared/model/type-acteur.model';

describe('Component Tests', () => {
  describe('TypeActeur Management Update Component', () => {
    let comp: TypeActeurUpdateComponent;
    let fixture: ComponentFixture<TypeActeurUpdateComponent>;
    let service: TypeActeurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeActeurUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeActeurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeActeurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeActeurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeActeur(123);
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
        const entity = new TypeActeur();
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
