import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPPService } from './doc-identification-pp.service';

@Component({
  templateUrl: './doc-identification-pp-delete-dialog.component.html'
})
export class DocIdentificationPPDeleteDialogComponent {
  docIdentificationPP?: IDocIdentificationPP;

  constructor(
    protected docIdentificationPPService: DocIdentificationPPService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.docIdentificationPPService.delete(id).subscribe(() => {
      this.eventManager.broadcast('docIdentificationPPListModification');
      this.activeModal.close();
    });
  }
}
