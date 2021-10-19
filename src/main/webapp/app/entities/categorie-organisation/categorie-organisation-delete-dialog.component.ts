import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { CategorieOrganisationService } from './categorie-organisation.service';

@Component({
  templateUrl: './categorie-organisation-delete-dialog.component.html'
})
export class CategorieOrganisationDeleteDialogComponent {
  categorieOrganisation?: ICategorieOrganisation;

  constructor(
    protected categorieOrganisationService: CategorieOrganisationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieOrganisationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'categorieOrganisationListModification',
        content: 'Deleted an CategorieOrganisation'
      });
      this.activeModal.close();
    });
  }
}
