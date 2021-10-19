import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { UserDeviceIdUpdateComponent } from 'app/entities/user-device-id/user-device-id-update.component';
import { UserDeviceIdService } from 'app/entities/user-device-id/user-device-id.service';
import { UserDeviceId } from 'app/shared/model/user-device-id.model';

describe('Component Tests', () => {
  describe('UserDeviceId Management Update Component', () => {
    let comp: UserDeviceIdUpdateComponent;
    let fixture: ComponentFixture<UserDeviceIdUpdateComponent>;
    let service: UserDeviceIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [UserDeviceIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserDeviceIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDeviceIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDeviceIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserDeviceId(123);
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
        const entity = new UserDeviceId();
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
