import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';

@Component({
  templateUrl: './stock-delete-dialog.component.html'
})
export class StockDeleteDialogComponent {
  stock: IStock;

  constructor(protected stockService: StockService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'stockListModification',
        content: 'Deleted an stock'
      });
      this.activeModal.dismiss(true);
    });
  }
}
