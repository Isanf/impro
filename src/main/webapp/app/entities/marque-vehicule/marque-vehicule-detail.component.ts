import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';

@Component({
  selector: 'jhi-marque-vehicule-detail',
  templateUrl: './marque-vehicule-detail.component.html'
})
export class MarqueVehiculeDetailComponent implements OnInit {
  marqueVehicule: IMarqueVehicule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marqueVehicule }) => (this.marqueVehicule = marqueVehicule));
  }

  previousState(): void {
    window.history.back();
  }
}
