import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';

@Component({
  selector: 'jhi-vehicule-occasional-detail',
  templateUrl: './vehicule-occasional-detail.component.html'
})
export class VehiculeOccasionalDetailComponent implements OnInit {
  vehiculeOccasional: IVehiculeOccasional | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehiculeOccasional }) => (this.vehiculeOccasional = vehiculeOccasional));
  }

  previousState(): void {
    window.history.back();
  }
}
