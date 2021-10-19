import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImmatriculation } from 'app/shared/model/immatriculation.model';
import { ImmatriculationService } from './immatriculation.service';

@Component({
  templateUrl: './immatriculation-delete-dialog.component.html'
})
export class ImmatriculationDeleteDialogComponent {
  immatriculation?: IImmatriculation;

  constructor(
    protected immatriculationService: ImmatriculationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.immatriculationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'immatriculationListModification',
        content: 'Delete an Immatriculation'
      });
      this.activeModal.close();
    });
  }
}
