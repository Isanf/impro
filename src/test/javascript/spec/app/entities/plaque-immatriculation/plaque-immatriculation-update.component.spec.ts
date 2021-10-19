import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PlaqueImmatriculationUpdateComponent } from 'app/entities/plaque-immatriculation/plaque-immatriculation-update.component';
import { PlaqueImmatriculationService } from 'app/entities/plaque-immatriculation/plaque-immatriculation.service';
import { PlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

describe('Component Tests', () => {
  describe('PlaqueImmatriculation Management Update Component', () => {
    let comp: PlaqueImmatriculationUpdateComponent;
    let fixture: ComponentFixture<PlaqueImmatriculationUpdateComponent>;
    let service: PlaqueImmatriculationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PlaqueImmatriculationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlaqueImmatriculationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlaqueImmatriculationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlaqueImmatriculationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlaqueImmatriculation(123);
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
        const entity = new PlaqueImmatriculation();
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
