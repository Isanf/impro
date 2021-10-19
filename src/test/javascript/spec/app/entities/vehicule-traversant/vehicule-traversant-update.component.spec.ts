import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeTraversantUpdateComponent } from 'app/entities/vehicule-traversant/vehicule-traversant-update.component';
import { VehiculeTraversantService } from 'app/entities/vehicule-traversant/vehicule-traversant.service';
import { VehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';

describe('Component Tests', () => {
  describe('VehiculeTraversant Management Update Component', () => {
    let comp: VehiculeTraversantUpdateComponent;
    let fixture: ComponentFixture<VehiculeTraversantUpdateComponent>;
    let service: VehiculeTraversantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeTraversantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehiculeTraversantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehiculeTraversantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeTraversantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VehiculeTraversant(123);
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
        const entity = new VehiculeTraversant();
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
