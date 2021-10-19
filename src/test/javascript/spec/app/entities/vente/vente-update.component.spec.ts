import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { VenteUpdateComponent } from 'app/entities/vente/vente-update.component';
import { VenteService } from 'app/entities/vente/vente.service';
import { Vente } from 'app/shared/model/vente.model';

describe('Component Tests', () => {
  describe('Vente Management Update Component', () => {
    let comp: VenteUpdateComponent;
    let fixture: ComponentFixture<VenteUpdateComponent>;
    let service: VenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vente(123);
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
        const entity = new Vente();
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
