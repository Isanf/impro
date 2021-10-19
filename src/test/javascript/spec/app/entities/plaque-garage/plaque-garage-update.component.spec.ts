import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PlaqueGarageUpdateComponent } from 'app/entities/plaque-garage/plaque-garage-update.component';
import { PlaqueGarageService } from 'app/entities/plaque-garage/plaque-garage.service';
import { PlaqueGarage } from 'app/shared/model/plaque-garage.model';

describe('Component Tests', () => {
  describe('PlaqueGarage Management Update Component', () => {
    let comp: PlaqueGarageUpdateComponent;
    let fixture: ComponentFixture<PlaqueGarageUpdateComponent>;
    let service: PlaqueGarageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PlaqueGarageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlaqueGarageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlaqueGarageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlaqueGarageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlaqueGarage(123);
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
        const entity = new PlaqueGarage();
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
