import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { NationUpdateComponent } from 'app/entities/nation/nation-update.component';
import { NationService } from 'app/entities/nation/nation.service';
import { Nation } from 'app/shared/model/nation.model';

describe('Component Tests', () => {
  describe('Nation Management Update Component', () => {
    let comp: NationUpdateComponent;
    let fixture: ComponentFixture<NationUpdateComponent>;
    let service: NationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [NationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nation(123);
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
        const entity = new Nation();
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
