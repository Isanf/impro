import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';
import { VehiculeOccasionalService } from './vehicule-occasional.service';

@Component({
  templateUrl: './vehicule-occasional-delete-dialog.component.html'
})
export class VehiculeOccasionalDeleteDialogComponent {
  vehiculeOccasional?: IVehiculeOccasional;

  constructor(
    protected vehiculeOccasionalService: VehiculeOccasionalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehiculeOccasionalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vehiculeOccasionalListModification');
      this.activeModal.close();
    });
  }
}
