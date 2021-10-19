import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INation } from 'app/shared/model/nation.model';
import { NationService } from './nation.service';

@Component({
  templateUrl: './nation-delete-dialog.component.html'
})
export class NationDeleteDialogComponent {
  nation?: INation;

  constructor(protected nationService: NationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nationListModification');
      this.activeModal.close();
    });
  }
}
