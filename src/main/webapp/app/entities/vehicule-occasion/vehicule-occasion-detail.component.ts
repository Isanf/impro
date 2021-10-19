import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';

@Component({
  selector: 'jhi-vehicule-occasion-detail',
  templateUrl: './vehicule-occasion-detail.component.html'
})
export class VehiculeOccasionDetailComponent implements OnInit {
  vehiculeOccasion: IVehiculeOccasion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehiculeOccasion }) => (this.vehiculeOccasion = vehiculeOccasion));
  }

  previousState(): void {
    window.history.back();
  }
}
