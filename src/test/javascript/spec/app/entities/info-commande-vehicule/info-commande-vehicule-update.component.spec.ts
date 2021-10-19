import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { InfoCommandeVehiculeUpdateComponent } from 'app/entities/info-commande-vehicule/info-commande-vehicule-update.component';
import { InfoCommandeVehiculeService } from 'app/entities/info-commande-vehicule/info-commande-vehicule.service';
import { InfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

describe('Component Tests', () => {
  describe('InfoCommandeVehicule Management Update Component', () => {
    let comp: InfoCommandeVehiculeUpdateComponent;
    let fixture: ComponentFixture<InfoCommandeVehiculeUpdateComponent>;
    let service: InfoCommandeVehiculeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [InfoCommandeVehiculeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InfoCommandeVehiculeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InfoCommandeVehiculeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InfoCommandeVehiculeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InfoCommandeVehicule(123);
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
        const entity = new InfoCommandeVehicule();
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
