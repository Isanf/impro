import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from './vehicule.service';

@Component({
  templateUrl: './vehicule-delete-dialog.component.html'
})
export class VehiculeDeleteDialogComponent {
  vehicule: IVehicule;

  constructor(protected vehiculeService: VehiculeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vehiculeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'vehiculeListModification',
        content: 'Deleted an vehicule'
      });
      this.activeModal.dismiss(true);
    });
  }
}
