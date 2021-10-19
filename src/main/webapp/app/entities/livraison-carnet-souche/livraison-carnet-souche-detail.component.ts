import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

@Component({
  selector: 'jhi-livraison-carnet-souche-detail',
  templateUrl: './livraison-carnet-souche-detail.component.html'
})
export class LivraisonCarnetSoucheDetailComponent implements OnInit {
  livraisonCarnetSouche: ILivraisonCarnetSouche | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonCarnetSouche }) => (this.livraisonCarnetSouche = livraisonCarnetSouche));
  }

  previousState(): void {
    window.history.back();
  }
}
