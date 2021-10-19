import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { LogActivityUpdateComponent } from 'app/entities/log-activity/log-activity-update.component';
import { LogActivityService } from 'app/entities/log-activity/log-activity.service';
import { LogActivity } from 'app/shared/model/log-activity.model';

describe('Component Tests', () => {
  describe('LogActivity Management Update Component', () => {
    let comp: LogActivityUpdateComponent;
    let fixture: ComponentFixture<LogActivityUpdateComponent>;
    let service: LogActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LogActivityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LogActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LogActivity(123);
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
        const entity = new LogActivity();
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
