import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

@Component({
  selector: 'jhi-info-commande-vehicule-detail',
  templateUrl: './info-commande-vehicule-detail.component.html'
})
export class InfoCommandeVehiculeDetailComponent implements OnInit {
  infoCommandeVehicule: IInfoCommandeVehicule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoCommandeVehicule }) => (this.infoCommandeVehicule = infoCommandeVehicule));
  }

  previousState(): void {
    window.history.back();
  }
}
