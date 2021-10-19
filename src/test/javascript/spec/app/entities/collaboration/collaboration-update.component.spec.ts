import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CollaborationUpdateComponent } from 'app/entities/collaboration/collaboration-update.component';
import { CollaborationService } from 'app/entities/collaboration/collaboration.service';
import { Collaboration } from 'app/shared/model/collaboration.model';

describe('Component Tests', () => {
  describe('Collaboration Management Update Component', () => {
    let comp: CollaborationUpdateComponent;
    let fixture: ComponentFixture<CollaborationUpdateComponent>;
    let service: CollaborationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CollaborationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CollaborationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollaborationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollaborationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Collaboration(123);
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
        const entity = new Collaboration();
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
