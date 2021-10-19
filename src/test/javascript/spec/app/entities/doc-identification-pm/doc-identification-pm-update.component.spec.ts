import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { DocIdentificationPMUpdateComponent } from 'app/entities/doc-identification-pm/doc-identification-pm-update.component';
import { DocIdentificationPMService } from 'app/entities/doc-identification-pm/doc-identification-pm.service';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';

describe('Component Tests', () => {
  describe('DocIdentificationPM Management Update Component', () => {
    let comp: DocIdentificationPMUpdateComponent;
    let fixture: ComponentFixture<DocIdentificationPMUpdateComponent>;
    let service: DocIdentificationPMService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [DocIdentificationPMUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocIdentificationPMUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocIdentificationPMUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocIdentificationPMService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocIdentificationPM(123);
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
        const entity = new DocIdentificationPM();
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
