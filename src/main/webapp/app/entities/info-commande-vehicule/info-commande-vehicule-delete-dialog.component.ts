import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';
import { InfoCommandeVehiculeService } from './info-commande-vehicule.service';

@Component({
  templateUrl: './info-commande-vehicule-delete-dialog.component.html'
})
export class InfoCommandeVehiculeDeleteDialogComponent {
  infoCommandeVehicule?: IInfoCommandeVehicule;

  constructor(
    protected infoCommandeVehiculeService: InfoCommandeVehiculeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.infoCommandeVehiculeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'infoCommandeVehiculeListModification',
        content: 'InfoCommandeVehicule'
      });
      this.activeModal.close();
    });
  }
}
