import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CertificatImmatriculationUpdateComponent } from 'app/entities/certificat-immatriculation/certificat-immatriculation-update.component';
import { CertificatImmatriculationService } from 'app/entities/certificat-immatriculation/certificat-immatriculation.service';
import { CertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';

describe('Component Tests', () => {
  describe('CertificatImmatriculation Management Update Component', () => {
    let comp: CertificatImmatriculationUpdateComponent;
    let fixture: ComponentFixture<CertificatImmatriculationUpdateComponent>;
    let service: CertificatImmatriculationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CertificatImmatriculationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CertificatImmatriculationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CertificatImmatriculationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CertificatImmatriculationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CertificatImmatriculation(123);
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
        const entity = new CertificatImmatriculation();
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
