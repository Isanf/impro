import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFirstlogin } from 'app/shared/model/firstlogin.model';
import { FirstloginService } from './firstlogin.service';

@Component({
  templateUrl: './firstlogin-delete-dialog.component.html'
})
export class FirstloginDeleteDialogComponent {
  firstlogin?: IFirstlogin;

  constructor(
    protected firstloginService: FirstloginService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.firstloginService.delete(id).subscribe(() => {
      this.eventManager.broadcast('firstloginListModification');
      this.activeModal.close();
    });
  }
}
