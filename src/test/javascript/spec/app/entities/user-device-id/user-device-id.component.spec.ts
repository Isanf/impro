import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ImproTestModule } from '../../../test.module';
import { UserDeviceIdComponent } from 'app/entities/user-device-id/user-device-id.component';
import { UserDeviceIdService } from 'app/entities/user-device-id/user-device-id.service';
import { UserDeviceId } from 'app/shared/model/user-device-id.model';

describe('Component Tests', () => {
  describe('UserDeviceId Management Component', () => {
    let comp: UserDeviceIdComponent;
    let fixture: ComponentFixture<UserDeviceIdComponent>;
    let service: UserDeviceIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [UserDeviceIdComponent]
      })
        .overrideTemplate(UserDeviceIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserDeviceIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserDeviceIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserDeviceId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userDeviceIds && comp.userDeviceIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
