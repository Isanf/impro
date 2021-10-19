import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatistique } from 'app/shared/model/statistique.model';
import { StatistiqueService } from './statistique.service';

@Component({
  templateUrl: './statistique-delete-dialog.component.html'
})
export class StatistiqueDeleteDialogComponent {
  statistique?: IStatistique;

  constructor(
    protected statistiqueService: StatistiqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statistiqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('statistiqueListModification');
      this.activeModal.close();
    });
  }
}
