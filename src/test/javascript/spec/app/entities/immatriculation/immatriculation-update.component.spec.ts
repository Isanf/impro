import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { ImmatriculationUpdateComponent } from 'app/entities/immatriculation/immatriculation-update.component';
import { ImmatriculationService } from 'app/entities/immatriculation/immatriculation.service';
import { Immatriculation } from 'app/shared/model/immatriculation.model';

describe('Component Tests', () => {
  describe('Immatriculation Management Update Component', () => {
    let comp: ImmatriculationUpdateComponent;
    let fixture: ComponentFixture<ImmatriculationUpdateComponent>;
    let service: ImmatriculationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [ImmatriculationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImmatriculationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImmatriculationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImmatriculationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Immatriculation(123);
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
        const entity = new Immatriculation();
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
