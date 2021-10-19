import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

@Component({
  selector: 'jhi-livraison-vehicule-detail',
  templateUrl: './livraison-vehicule-detail.component.html'
})
export class LivraisonVehiculeDetailComponent implements OnInit {
  livraisonVehicule: ILivraisonVehicule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonVehicule }) => (this.livraisonVehicule = livraisonVehicule));
  }

  previousState(): void {
    window.history.back();
  }
}
