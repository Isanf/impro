import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeActeur } from 'app/shared/model/type-acteur.model';
import { TypeActeurService } from './type-acteur.service';

@Component({
  templateUrl: './type-acteur-delete-dialog.component.html'
})
export class TypeActeurDeleteDialogComponent {
  typeActeur?: ITypeActeur;

  constructor(
    protected typeActeurService: TypeActeurService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeActeurService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'typeActeurListModification',
        content: 'Delete an TypeActeur'
      });
      this.activeModal.close();
    });
  }
}
