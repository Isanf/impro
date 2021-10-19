import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CommandeVehiculeUpdateComponent } from 'app/entities/commande-vehicule/commande-vehicule-update.component';
import { CommandeVehiculeService } from 'app/entities/commande-vehicule/commande-vehicule.service';
import { CommandeVehicule } from 'app/shared/model/commande-vehicule.model';

describe('Component Tests', () => {
  describe('CommandeVehicule Management Update Component', () => {
    let comp: CommandeVehiculeUpdateComponent;
    let fixture: ComponentFixture<CommandeVehiculeUpdateComponent>;
    let service: CommandeVehiculeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CommandeVehiculeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandeVehiculeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeVehiculeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeVehiculeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeVehicule(123);
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
        const entity = new CommandeVehicule();
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
