import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrganisationLocalite } from 'app/shared/model/organisation-localite.model';
import { OrganisationLocaliteService } from './organisation-localite.service';

@Component({
  templateUrl: './organisation-localite-delete-dialog.component.html'
})
export class OrganisationLocaliteDeleteDialogComponent {
  organisationLocalite?: IOrganisationLocalite;

  constructor(
    protected organisationLocaliteService: OrganisationLocaliteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organisationLocaliteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'organisationLocaliteListModification',
        content: 'Delete an OrganisationLocalite'
      });
      this.activeModal.close();
    });
  }
}
