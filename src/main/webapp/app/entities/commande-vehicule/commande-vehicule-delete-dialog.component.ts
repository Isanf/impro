import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { CommandeVehiculeService } from './commande-vehicule.service';

@Component({
  templateUrl: './commande-vehicule-delete-dialog.component.html'
})
export class CommandeVehiculeDeleteDialogComponent {
  commandeVehicule?: ICommandeVehicule;

  constructor(
    protected commandeVehiculeService: CommandeVehiculeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeVehiculeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'commandeVehiculeListModification',
        content: 'Delete on CommandeVehicule'
      });
      this.activeModal.close();
    });
  }
}
