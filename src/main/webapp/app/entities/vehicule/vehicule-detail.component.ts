import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVehicule } from 'app/shared/model/vehicule.model';

@Component({
  selector: 'jhi-vehicule-detail',
  templateUrl: './vehicule-detail.component.html'
})
export class VehiculeDetailComponent implements OnInit {
  vehicule: IVehicule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicule }) => {
      this.vehicule = vehicule;
    });
  }

  previousState() {
    window.history.back();
  }
}
