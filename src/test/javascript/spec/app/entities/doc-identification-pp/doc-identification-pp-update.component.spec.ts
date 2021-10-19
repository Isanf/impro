import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { DocIdentificationPPUpdateComponent } from 'app/entities/doc-identification-pp/doc-identification-pp-update.component';
import { DocIdentificationPPService } from 'app/entities/doc-identification-pp/doc-identification-pp.service';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

describe('Component Tests', () => {
  describe('DocIdentificationPP Management Update Component', () => {
    let comp: DocIdentificationPPUpdateComponent;
    let fixture: ComponentFixture<DocIdentificationPPUpdateComponent>;
    let service: DocIdentificationPPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [DocIdentificationPPUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocIdentificationPPUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocIdentificationPPUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocIdentificationPPService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocIdentificationPP(123);
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
        const entity = new DocIdentificationPP();
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
