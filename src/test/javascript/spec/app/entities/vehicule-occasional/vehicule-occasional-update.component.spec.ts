import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VehiculeOccasionalUpdateComponent } from 'app/entities/vehicule-occasional/vehicule-occasional-update.component';
import { VehiculeOccasionalService } from 'app/entities/vehicule-occasional/vehicule-occasional.service';
import { VehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';

describe('Component Tests', () => {
  describe('VehiculeOccasional Management Update Component', () => {
    let comp: VehiculeOccasionalUpdateComponent;
    let fixture: ComponentFixture<VehiculeOccasionalUpdateComponent>;
    let service: VehiculeOccasionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeOccasionalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VehiculeOccasionalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VehiculeOccasionalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeOccasionalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VehiculeOccasional(123);
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
        const entity = new VehiculeOccasional();
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
