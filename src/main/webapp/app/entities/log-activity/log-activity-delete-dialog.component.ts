import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogActivity } from 'app/shared/model/log-activity.model';
import { LogActivityService } from './log-activity.service';

@Component({
  templateUrl: './log-activity-delete-dialog.component.html'
})
export class LogActivityDeleteDialogComponent {
  logActivity?: ILogActivity;

  constructor(
    protected logActivityService: LogActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('logActivityListModification');
      this.activeModal.close();
    });
  }
}
