import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { TypeCarnetUpdateComponent } from 'app/entities/type-carnet/type-carnet-update.component';
import { TypeCarnetService } from 'app/entities/type-carnet/type-carnet.service';
import { TypeCarnet } from 'app/shared/model/type-carnet.model';

describe('Component Tests', () => {
  describe('TypeCarnet Management Update Component', () => {
    let comp: TypeCarnetUpdateComponent;
    let fixture: ComponentFixture<TypeCarnetUpdateComponent>;
    let service: TypeCarnetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [TypeCarnetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeCarnetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeCarnetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeCarnetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeCarnet(123);
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
        const entity = new TypeCarnet();
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
