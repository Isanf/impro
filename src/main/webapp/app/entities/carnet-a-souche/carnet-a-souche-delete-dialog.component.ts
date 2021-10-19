import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { CarnetASoucheService } from './carnet-a-souche.service';

@Component({
  templateUrl: './carnet-a-souche-delete-dialog.component.html'
})
export class CarnetASoucheDeleteDialogComponent {
  carnetASouche?: ICarnetASouche;

  constructor(
    protected carnetASoucheService: CarnetASoucheService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number): void {
    this.carnetASoucheService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'carnetASoucheListModification',
        conten: 'Deleted an carnetASouche'
      });
      this.activeModal.dismiss(true);
    });
  }
}
