import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlaqueGarage } from 'app/shared/model/plaque-garage.model';

@Component({
  selector: 'jhi-plaque-garage-detail',
  templateUrl: './plaque-garage-detail.component.html'
})
export class PlaqueGarageDetailComponent implements OnInit {
  plaqueGarage: IPlaqueGarage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plaqueGarage }) => (this.plaqueGarage = plaqueGarage));
  }

  previousState(): void {
    window.history.back();
  }
}
