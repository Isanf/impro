import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from './profil.service';

@Component({
  templateUrl: './profil-delete-dialog.component.html'
})
export class ProfilDeleteDialogComponent {
  profil: IProfil;

  constructor(protected profilService: ProfilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profilService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'profilListModification',
        content: 'Deleted an profil'
      });
      this.activeModal.dismiss(true);
    });
  }
}
