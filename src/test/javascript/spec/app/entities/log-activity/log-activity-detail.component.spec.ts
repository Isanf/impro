import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { LogActivityDetailComponent } from 'app/entities/log-activity/log-activity-detail.component';
import { LogActivity } from 'app/shared/model/log-activity.model';

describe('Component Tests', () => {
  describe('LogActivity Management Detail Component', () => {
    let comp: LogActivityDetailComponent;
    let fixture: ComponentFixture<LogActivityDetailComponent>;
    const route = ({ data: of({ logActivity: new LogActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LogActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LogActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
