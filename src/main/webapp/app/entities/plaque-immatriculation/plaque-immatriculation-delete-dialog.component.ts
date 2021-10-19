import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { PlaqueImmatriculationService } from './plaque-immatriculation.service';

@Component({
  templateUrl: './plaque-immatriculation-delete-dialog.component.html'
})
export class PlaqueImmatriculationDeleteDialogComponent {
  plaqueImmatriculation?: IPlaqueImmatriculation;

  constructor(
    protected plaqueImmatriculationService: PlaqueImmatriculationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plaqueImmatriculationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'plaqueImmatriculationListModification',
        content: 'Delete an PlaqueImmatriculation'
      });
      this.activeModal.close();
    });
  }
}
