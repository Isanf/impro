import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

@Component({
  selector: 'jhi-info-commande-carnet-a-souche-detail',
  templateUrl: './info-commande-carnet-a-souche-detail.component.html'
})
export class InfoCommandeCarnetASoucheDetailComponent implements OnInit {
  infoCommandeCarnetASouche: IInfoCommandeCarnetASouche | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoCommandeCarnetASouche }) => (this.infoCommandeCarnetASouche = infoCommandeCarnetASouche));
  }

  previousState(): void {
    window.history.back();
  }
}
