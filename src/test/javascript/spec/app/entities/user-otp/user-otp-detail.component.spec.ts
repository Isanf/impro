import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { UserOtpDetailComponent } from 'app/entities/user-otp/user-otp-detail.component';
import { UserOtp } from 'app/shared/model/user-otp.model';

describe('Component Tests', () => {
  describe('UserOtp Management Detail Component', () => {
    let comp: UserOtpDetailComponent;
    let fixture: ComponentFixture<UserOtpDetailComponent>;
    const route = ({ data: of({ userOtp: new UserOtp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [UserOtpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserOtpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserOtpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userOtp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userOtp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
