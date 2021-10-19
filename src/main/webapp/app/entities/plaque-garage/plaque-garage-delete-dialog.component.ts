import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlaqueGarage } from 'app/shared/model/plaque-garage.model';
import { PlaqueGarageService } from './plaque-garage.service';

@Component({
  templateUrl: './plaque-garage-delete-dialog.component.html'
})
export class PlaqueGarageDeleteDialogComponent {
  plaqueGarage?: IPlaqueGarage;

  constructor(
    protected plaqueGarageService: PlaqueGarageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.plaqueGarageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('plaqueGarageListModification');
      this.activeModal.close();
    });
  }
}
