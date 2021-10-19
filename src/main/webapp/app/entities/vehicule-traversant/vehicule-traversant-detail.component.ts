import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';

@Component({
  selector: 'jhi-vehicule-traversant-detail',
  templateUrl: './vehicule-traversant-detail.component.html'
})
export class VehiculeTraversantDetailComponent implements OnInit {
  vehiculeTraversant: IVehiculeTraversant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehiculeTraversant }) => (this.vehiculeTraversant = vehiculeTraversant));
  }

  previousState(): void {
    window.history.back();
  }
}
