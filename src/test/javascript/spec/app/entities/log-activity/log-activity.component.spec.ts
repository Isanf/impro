import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ImproTestModule } from '../../../test.module';
import { LogActivityComponent } from 'app/entities/log-activity/log-activity.component';
import { LogActivityService } from 'app/entities/log-activity/log-activity.service';
import { LogActivity } from 'app/shared/model/log-activity.model';

describe('Component Tests', () => {
  describe('LogActivity Management Component', () => {
    let comp: LogActivityComponent;
    let fixture: ComponentFixture<LogActivityComponent>;
    let service: LogActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LogActivityComponent]
      })
        .overrideTemplate(LogActivityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogActivityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogActivityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LogActivity(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.logActivities && comp.logActivities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
