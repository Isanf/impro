import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LivraisonCarnetSoucheService } from './livraison-carnet-souche.service';

@Component({
  templateUrl: './livraison-carnet-souche-delete-dialog.component.html'
})
export class LivraisonCarnetSoucheDeleteDialogComponent {
  livraisonCarnetSouche?: ILivraisonCarnetSouche;

  constructor(
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livraisonCarnetSoucheService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'livraisonCarnetSoucheListModification',
        content: 'Delete an LivraisonCarnetSouche'
      });
      this.activeModal.close();
    });
  }
}
