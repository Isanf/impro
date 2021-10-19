import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarteW } from 'app/shared/model/carte-w.model';
import { CarteWService } from './carte-w.service';

@Component({
  templateUrl: './carte-w-delete-dialog.component.html'
})
export class CarteWDeleteDialogComponent {
  carteW?: ICarteW;

  constructor(protected carteWService: CarteWService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carteWService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'carteWListModification',
        content: 'Deleted an CarteW'
      });
      this.activeModal.close();
    });
  }
}
