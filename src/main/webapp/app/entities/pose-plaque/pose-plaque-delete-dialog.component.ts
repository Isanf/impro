import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPosePlaque } from 'app/shared/model/pose-plaque.model';
import { PosePlaqueService } from './pose-plaque.service';

@Component({
  templateUrl: './pose-plaque-delete-dialog.component.html'
})
export class PosePlaqueDeleteDialogComponent {
  posePlaque: IPosePlaque;

  constructor(
    protected posePlaqueService: PosePlaqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.posePlaqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'posePlaqueListModification',
        content: 'Deleted an posePlaque'
      });
      this.activeModal.dismiss(true);
    });
  }
}
