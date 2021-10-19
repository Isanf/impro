import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from './organisation.service';

@Component({
  templateUrl: './organisation-delete-dialog.component.html'
})
export class OrganisationDeleteDialogComponent {
  organisation?: IOrganisation;

  constructor(
    protected organisationService: OrganisationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organisationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'organisationListModification',
        content: 'Delete an Organisation'
      });
      this.activeModal.close();
    });
  }
}
