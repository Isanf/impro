import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonnePhysiqueService } from './personne-physique.service';

@Component({
  templateUrl: './personne-physique-delete-dialog.component.html'
})
export class PersonnePhysiqueDeleteDialogComponent {
  personnePhysique: IPersonnePhysique;

  constructor(
    protected personnePhysiqueService: PersonnePhysiqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personnePhysiqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'personnePhysiqueListModification',
        content: 'Deleted an personnePhysique'
      });
      this.activeModal.dismiss(true);
    });
  }
}
