import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CarnetASoucheUpdateComponent } from 'app/entities/carnet-a-souche/carnet-a-souche-update.component';
import { CarnetASoucheService } from 'app/entities/carnet-a-souche/carnet-a-souche.service';
import { CarnetASouche } from 'app/shared/model/carnet-a-souche.model';

describe('Component Tests', () => {
  describe('CarnetASouche Management Update Component', () => {
    let comp: CarnetASoucheUpdateComponent;
    let fixture: ComponentFixture<CarnetASoucheUpdateComponent>;
    let service: CarnetASoucheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CarnetASoucheUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarnetASoucheUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarnetASoucheUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarnetASoucheService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarnetASouche(123);
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
        const entity = new CarnetASouche();
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
