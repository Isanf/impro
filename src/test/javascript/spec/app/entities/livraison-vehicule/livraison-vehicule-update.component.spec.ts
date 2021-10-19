import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { LivraisonVehiculeUpdateComponent } from 'app/entities/livraison-vehicule/livraison-vehicule-update.component';
import { LivraisonVehiculeService } from 'app/entities/livraison-vehicule/livraison-vehicule.service';
import { LivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

describe('Component Tests', () => {
  describe('LivraisonVehicule Management Update Component', () => {
    let comp: LivraisonVehiculeUpdateComponent;
    let fixture: ComponentFixture<LivraisonVehiculeUpdateComponent>;
    let service: LivraisonVehiculeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonVehiculeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LivraisonVehiculeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivraisonVehiculeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivraisonVehiculeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LivraisonVehicule(123);
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
        const entity = new LivraisonVehicule();
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
