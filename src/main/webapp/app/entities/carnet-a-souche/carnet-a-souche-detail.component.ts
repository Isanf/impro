import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';

@Component({
  selector: 'jhi-carnet-a-souche-detail',
  templateUrl: './carnet-a-souche-detail.component.html'
})
export class CarnetASoucheDetailComponent implements OnInit {
  carnetASouche: ICarnetASouche | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carnetASouche }) => (this.carnetASouche = carnetASouche));
  }

  previousState(): void {
    window.history.back();
  }
}
