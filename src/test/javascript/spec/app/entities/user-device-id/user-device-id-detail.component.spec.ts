import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { UserDeviceIdDetailComponent } from 'app/entities/user-device-id/user-device-id-detail.component';
import { UserDeviceId } from 'app/shared/model/user-device-id.model';

describe('Component Tests', () => {
  describe('UserDeviceId Management Detail Component', () => {
    let comp: UserDeviceIdDetailComponent;
    let fixture: ComponentFixture<UserDeviceIdDetailComponent>;
    const route = ({ data: of({ userDeviceId: new UserDeviceId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [UserDeviceIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserDeviceIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserDeviceIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userDeviceId on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userDeviceId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
