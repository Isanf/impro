import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CarteWUpdateComponent } from 'app/entities/carte-w/carte-w-update.component';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { CarteW } from 'app/shared/model/carte-w.model';

describe('Component Tests', () => {
  describe('CarteW Management Update Component', () => {
    let comp: CarteWUpdateComponent;
    let fixture: ComponentFixture<CarteWUpdateComponent>;
    let service: CarteWService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CarteWUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CarteWUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarteWUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarteWService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CarteW(123);
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
        const entity = new CarteW();
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
