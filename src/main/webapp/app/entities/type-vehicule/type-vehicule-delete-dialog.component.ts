import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from './type-vehicule.service';

@Component({
  templateUrl: './type-vehicule-delete-dialog.component.html'
})
export class TypeVehiculeDeleteDialogComponent {
  typeVehicule?: ITypeVehicule;

  constructor(
    protected typeVehiculeService: TypeVehiculeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeVehiculeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'typeVehiculeListModification',
        content: 'Delete an TypeVehicule'
      });
      this.activeModal.close();
    });
  }
}
