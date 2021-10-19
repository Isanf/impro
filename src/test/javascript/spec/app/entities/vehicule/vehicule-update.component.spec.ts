import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeUpdateComponent } from 'app/entities/vehicule/vehicule-update.component';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';
import { Vehicule } from 'app/shared/model/vehicule.model';

describe('Component Tests', () => {
  describe('Vehicule Management Update Component', () => {
    let comp: VehiculeUpdateComponent;
    let fixture: ComponentFixture<VehiculeUpdateComponent>;
    let service: VehiculeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehiculeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehiculeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vehicule(123);
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
        const entity = new Vehicule();
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
