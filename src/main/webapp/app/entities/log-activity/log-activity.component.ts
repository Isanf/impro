import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILogActivity } from 'app/shared/model/log-activity.model';
import { LogActivityService } from './log-activity.service';
import { LogActivityDeleteDialogComponent } from './log-activity-delete-dialog.component';

@Component({
  selector: 'jhi-log-activity',
  templateUrl: './log-activity.component.html'
})
export class LogActivityComponent implements OnInit, OnDestroy {
  logActivities?: ILogActivity[];
  eventSubscriber?: Subscription;

  constructor(
    protected logActivityService: LogActivityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.logActivityService.query().subscribe((res: HttpResponse<ILogActivity[]>) => (this.logActivities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLogActivities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILogActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLogActivities(): void {
    this.eventSubscriber = this.eventManager.subscribe('logActivityListModification', () => this.loadAll());
  }

  delete(logActivity: ILogActivity): void {
    const modalRef = this.modalService.open(LogActivityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.logActivity = logActivity;
  }
}
