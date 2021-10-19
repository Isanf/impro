import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { ProfilUpdateComponent } from 'app/entities/profil/profil-update.component';
import { ProfilService } from 'app/entities/profil/profil.service';
import { Profil } from 'app/shared/model/profil.model';

describe('Component Tests', () => {
  describe('Profil Management Update Component', () => {
    let comp: ProfilUpdateComponent;
    let fixture: ComponentFixture<ProfilUpdateComponent>;
    let service: ProfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [ProfilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Profil(123);
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
        const entity = new Profil();
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
