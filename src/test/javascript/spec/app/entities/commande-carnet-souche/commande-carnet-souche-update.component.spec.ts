import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CommandeCarnetSoucheUpdateComponent } from 'app/entities/commande-carnet-souche/commande-carnet-souche-update.component';
import { CommandeCarnetSoucheService } from 'app/entities/commande-carnet-souche/commande-carnet-souche.service';
import { CommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

describe('Component Tests', () => {
  describe('CommandeCarnetSouche Management Update Component', () => {
    let comp: CommandeCarnetSoucheUpdateComponent;
    let fixture: ComponentFixture<CommandeCarnetSoucheUpdateComponent>;
    let service: CommandeCarnetSoucheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CommandeCarnetSoucheUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommandeCarnetSoucheUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeCarnetSoucheUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeCarnetSoucheService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommandeCarnetSouche(123);
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
        const entity = new CommandeCarnetSouche();
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
