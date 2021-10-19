import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeVehiculeUpdateComponent } from 'app/entities/type-vehicule/type-vehicule-update.component';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';
import { TypeVehicule } from 'app/shared/model/type-vehicule.model';

describe('Component Tests', () => {
  describe('TypeVehicule Management Update Component', () => {
    let comp: TypeVehiculeUpdateComponent;
    let fixture: ComponentFixture<TypeVehiculeUpdateComponent>;
    let service: TypeVehiculeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeVehiculeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeVehiculeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeVehiculeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeVehiculeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeVehicule(123);
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
        const entity = new TypeVehicule();
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
