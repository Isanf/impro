import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollaboration } from 'app/shared/model/collaboration.model';
import { CollaborationService } from './collaboration.service';

@Component({
  templateUrl: './collaboration-delete-dialog.component.html'
})
export class CollaborationDeleteDialogComponent {
  collaboration?: ICollaboration;

  constructor(
    protected collaborationService: CollaborationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collaborationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'collaborationListModification',
        content: 'Delete an Collaboration'
      });
      this.activeModal.close();
    });
  }
}
