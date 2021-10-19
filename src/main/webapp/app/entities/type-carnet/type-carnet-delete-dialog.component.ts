import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeCarnet } from 'app/shared/model/type-carnet.model';
import { TypeCarnetService } from './type-carnet.service';

@Component({
  templateUrl: './type-carnet-delete-dialog.component.html'
})
export class TypeCarnetDeleteDialogComponent {
  typeCarnet?: ITypeCarnet;

  constructor(
    protected typeCarnetService: TypeCarnetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeCarnetService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'typeCarnetListModification',
        content: 'Delete an TypeCarnet'
      });
      this.activeModal.close();
    });
  }
}
