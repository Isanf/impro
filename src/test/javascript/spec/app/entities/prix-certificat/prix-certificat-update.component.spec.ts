import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { PrixCertificatUpdateComponent } from 'app/entities/prix-certificat/prix-certificat-update.component';
import { PrixCertificatService } from 'app/entities/prix-certificat/prix-certificat.service';
import { PrixCertificat } from 'app/shared/model/prix-certificat.model';

describe('Component Tests', () => {
  describe('PrixCertificat Management Update Component', () => {
    let comp: PrixCertificatUpdateComponent;
    let fixture: ComponentFixture<PrixCertificatUpdateComponent>;
    let service: PrixCertificatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PrixCertificatUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrixCertificatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrixCertificatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrixCertificatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrixCertificat(123);
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
        const entity = new PrixCertificat();
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
