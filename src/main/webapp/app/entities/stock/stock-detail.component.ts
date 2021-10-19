import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStock } from 'app/shared/model/stock.model';

@Component({
  selector: 'jhi-stock-detail',
  templateUrl: './stock-detail.component.html'
})
export class StockDetailComponent implements OnInit {
  stock: IStock;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.stock = stock;
    });
  }

  previousState() {
    window.history.back();
  }
}
