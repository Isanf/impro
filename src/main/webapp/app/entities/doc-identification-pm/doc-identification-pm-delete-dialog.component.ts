import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { DocIdentificationPMService } from './doc-identification-pm.service';

@Component({
  templateUrl: './doc-identification-pm-delete-dialog.component.html'
})
export class DocIdentificationPMDeleteDialogComponent {
  docIdentificationPM: IDocIdentificationPM;

  constructor(
    protected docIdentificationPMService: DocIdentificationPMService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.docIdentificationPMService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'docIdentificationPMListModification',
        content: 'Deleted an docIdentificationPM'
      });
      this.activeModal.dismiss(true);
    });
  }
}
