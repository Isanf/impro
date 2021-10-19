import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrixCertificat } from 'app/shared/model/prix-certificat.model';
import { PrixCertificatService } from './prix-certificat.service';

@Component({
  templateUrl: './prix-certificat-delete-dialog.component.html'
})
export class PrixCertificatDeleteDialogComponent {
  prixCertificat?: IPrixCertificat;

  constructor(
    protected prixCertificatService: PrixCertificatService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prixCertificatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prixCertificatListModification');
      this.activeModal.close();
    });
  }
}
