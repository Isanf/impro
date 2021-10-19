import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeOrganisationService } from './type-organisation.service';

@Component({
  templateUrl: './type-organisation-delete-dialog.component.html'
})
export class TypeOrganisationDeleteDialogComponent {
  typeOrganisation?: ITypeOrganisation;

  constructor(
    protected typeOrganisationService: TypeOrganisationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeOrganisationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'typeOrganisationListModification',
        content: 'Delete an TypeOrganisation'
      });
      this.activeModal.close();
    });
  }
}
