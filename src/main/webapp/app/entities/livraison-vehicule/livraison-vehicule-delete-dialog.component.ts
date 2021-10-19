import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';
import { LivraisonVehiculeService } from './livraison-vehicule.service';

@Component({
  templateUrl: './livraison-vehicule-delete-dialog.component.html'
})
export class LivraisonVehiculeDeleteDialogComponent {
  livraisonVehicule?: ILivraisonVehicule;

  constructor(
    protected livraisonVehiculeService: LivraisonVehiculeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livraisonVehiculeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'livraisonVehiculeListModification',
        content: 'Delete an LivraisonVehicule'
      });
      this.activeModal.close();
    });
  }
}
