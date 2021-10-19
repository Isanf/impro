import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVente } from 'app/shared/model/vente.model';
import { VenteService } from './vente.service';

@Component({
  templateUrl: './vente-delete-dialog.component.html'
})
export class VenteDeleteDialogComponent {
  vente?: IVente;

  constructor(protected venteService: VenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.venteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'venteListModification',
        content: 'Delete an Vente'
      });
      this.activeModal.close();
    });
  }
}
