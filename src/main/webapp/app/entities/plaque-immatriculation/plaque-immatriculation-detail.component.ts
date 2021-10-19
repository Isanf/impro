import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

@Component({
  selector: 'jhi-plaque-immatriculation-detail',
  templateUrl: './plaque-immatriculation-detail.component.html'
})
export class PlaqueImmatriculationDetailComponent implements OnInit {
  plaqueImmatriculation: IPlaqueImmatriculation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plaqueImmatriculation }) => (this.plaqueImmatriculation = plaqueImmatriculation));
  }

  previousState(): void {
    window.history.back();
  }
}
