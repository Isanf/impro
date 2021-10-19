import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';

@Component({
  selector: 'jhi-type-vehicule-detail',
  templateUrl: './type-vehicule-detail.component.html'
})
export class TypeVehiculeDetailComponent implements OnInit {
  typeVehicule: ITypeVehicule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeVehicule }) => (this.typeVehicule = typeVehicule));
  }

  previousState(): void {
    window.history.back();
  }
}
