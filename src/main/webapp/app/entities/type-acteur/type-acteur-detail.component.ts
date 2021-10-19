import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeActeur } from 'app/shared/model/type-acteur.model';

@Component({
  selector: 'jhi-type-acteur-detail',
  templateUrl: './type-acteur-detail.component.html'
})
export class TypeActeurDetailComponent implements OnInit {
  typeActeur: ITypeActeur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeActeur }) => (this.typeActeur = typeActeur));
  }

  previousState(): void {
    window.history.back();
  }
}
