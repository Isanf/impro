import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { InfoCommandeCarnetASoucheService } from './info-commande-carnet-a-souche.service';

@Component({
  templateUrl: './info-commande-carnet-a-souche-delete-dialog.component.html'
})
export class InfoCommandeCarnetASoucheDeleteDialogComponent {
  infoCommandeCarnetASouche?: IInfoCommandeCarnetASouche;

  constructor(
    protected infoCommandeCarnetASoucheService: InfoCommandeCarnetASoucheService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.infoCommandeCarnetASoucheService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'infoCommandeCarnetASoucheListModification',
        content: 'Delete an InfoCommandeCarnetASouche'
      });
      this.activeModal.close();
    });
  }
}
