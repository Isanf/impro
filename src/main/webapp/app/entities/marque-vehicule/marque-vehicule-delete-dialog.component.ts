import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { MarqueVehiculeService } from './marque-vehicule.service';

@Component({
  templateUrl: './marque-vehicule-delete-dialog.component.html'
})
export class MarqueVehiculeDeleteDialogComponent {
  marqueVehicule?: IMarqueVehicule;

  constructor(
    protected marqueVehiculeService: MarqueVehiculeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.marqueVehiculeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'marqueVehiculeListModification',
        content: 'Delete an MarqueVehicule'
      });
      this.activeModal.close();
    });
  }
}
