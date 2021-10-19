import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';
import { CertificatImmatriculationService } from './certificat-immatriculation.service';

@Component({
  templateUrl: './certificat-immatriculation-delete-dialog.component.html'
})
export class CertificatImmatriculationDeleteDialogComponent {
  certificatImmatriculation?: ICertificatImmatriculation;

  constructor(
    protected certificatImmatriculationService: CertificatImmatriculationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.certificatImmatriculationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'certificatImmatriculationListModification',
        content: 'Deleted an certificatImmatriculation'
      });
      this.activeModal.dismiss(true);
    });
  }
}
