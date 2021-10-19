import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';
import { VehiculeTraversantService } from './vehicule-traversant.service';

@Component({
  templateUrl: './vehicule-traversant-delete-dialog.component.html'
})
export class VehiculeTraversantDeleteDialogComponent {
  vehiculeTraversant?: IVehiculeTraversant;

  constructor(
    protected vehiculeTraversantService: VehiculeTraversantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehiculeTraversantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vehiculeTraversantListModification');
      this.activeModal.close();
    });
  }
}
