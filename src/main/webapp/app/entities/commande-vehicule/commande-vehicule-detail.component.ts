import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';

@Component({
  selector: 'jhi-commande-vehicule-detail',
  templateUrl: './commande-vehicule-detail.component.html'
})
export class CommandeVehiculeDetailComponent implements OnInit {
  commandeVehicule: ICommandeVehicule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeVehicule }) => (this.commandeVehicule = commandeVehicule));
  }

  previousState(): void {
    window.history.back();
  }
}
