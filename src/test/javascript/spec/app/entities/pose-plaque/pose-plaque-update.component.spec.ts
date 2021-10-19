import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PosePlaqueUpdateComponent } from 'app/entities/pose-plaque/pose-plaque-update.component';
import { PosePlaqueService } from 'app/entities/pose-plaque/pose-plaque.service';
import { PosePlaque } from 'app/shared/model/pose-plaque.model';

describe('Component Tests', () => {
  describe('PosePlaque Management Update Component', () => {
    let comp: PosePlaqueUpdateComponent;
    let fixture: ComponentFixture<PosePlaqueUpdateComponent>;
    let service: PosePlaqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PosePlaqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PosePlaqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PosePlaqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PosePlaqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PosePlaque(123);
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
        const entity = new PosePlaque();
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
