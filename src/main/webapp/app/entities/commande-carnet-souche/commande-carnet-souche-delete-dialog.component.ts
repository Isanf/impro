import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { CommandeCarnetSoucheService } from './commande-carnet-souche.service';

@Component({
  templateUrl: './commande-carnet-souche-delete-dialog.component.html'
})
export class CommandeCarnetSoucheDeleteDialogComponent {
  commandeCarnetSouche?: ICommandeCarnetSouche;

  constructor(
    protected commandeCarnetSoucheService: CommandeCarnetSoucheService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandeCarnetSoucheService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'commandeCarnetSoucheListModification',
        content: 'Delete an CommandeCarnetSouche'
      });
      this.activeModal.close();
    });
  }
}
