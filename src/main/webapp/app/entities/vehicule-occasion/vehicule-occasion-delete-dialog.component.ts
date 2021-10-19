import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';
import { VehiculeOccasionService } from './vehicule-occasion.service';

@Component({
  templateUrl: './vehicule-occasion-delete-dialog.component.html'
})
export class VehiculeOccasionDeleteDialogComponent {
  vehiculeOccasion?: IVehiculeOccasion;

  constructor(
    protected vehiculeOccasionService: VehiculeOccasionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehiculeOccasionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vehiculeOccasionListModification');
      this.activeModal.close();
    });
  }
}
