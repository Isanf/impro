import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { UserOtpUpdateComponent } from 'app/entities/user-otp/user-otp-update.component';
import { UserOtpService } from 'app/entities/user-otp/user-otp.service';
import { UserOtp } from 'app/shared/model/user-otp.model';

describe('Component Tests', () => {
  describe('UserOtp Management Update Component', () => {
    let comp: UserOtpUpdateComponent;
    let fixture: ComponentFixture<UserOtpUpdateComponent>;
    let service: UserOtpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [UserOtpUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserOtpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserOtpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserOtpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserOtp(123);
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
        const entity = new UserOtp();
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
