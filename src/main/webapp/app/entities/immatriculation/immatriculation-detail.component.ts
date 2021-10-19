import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImmatriculation } from 'app/shared/model/immatriculation.model';

@Component({
  selector: 'jhi-immatriculation-detail',
  templateUrl: './immatriculation-detail.component.html'
})
export class ImmatriculationDetailComponent implements OnInit {
  immatriculation: IImmatriculation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ immatriculation }) => (this.immatriculation = immatriculation));
  }

  previousState(): void {
    window.history.back();
  }
}
