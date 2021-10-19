import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonneMorale } from 'app/shared/model/personne-morale.model';
import { PersonneMoraleService } from './personne-morale.service';

@Component({
  templateUrl: './personne-morale-delete-dialog.component.html'
})
export class PersonneMoraleDeleteDialogComponent {
  personneMorale: IPersonneMorale;

  constructor(
    protected personneMoraleService: PersonneMoraleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personneMoraleService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'personneMoraleListModification',
        content: 'Deleted an personneMorale'
      });
      this.activeModal.dismiss(true);
    });
  }
}
