import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeOccasionUpdateComponent } from 'app/entities/vehicule-occasion/vehicule-occasion-update.component';
import { VehiculeOccasionService } from 'app/entities/vehicule-occasion/vehicule-occasion.service';
import { VehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';

describe('Component Tests', () => {
  describe('VehiculeOccasion Management Update Component', () => {
    let comp: VehiculeOccasionUpdateComponent;
    let fixture: ComponentFixture<VehiculeOccasionUpdateComponent>;
    let service: VehiculeOccasionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeOccasionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehiculeOccasionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehiculeOccasionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeOccasionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VehiculeOccasion(123);
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
        const entity = new VehiculeOccasion();
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
