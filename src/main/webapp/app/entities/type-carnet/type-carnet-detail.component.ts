import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeCarnet } from 'app/shared/model/type-carnet.model';

@Component({
  selector: 'jhi-type-carnet-detail',
  templateUrl: './type-carnet-detail.component.html'
})
export class TypeCarnetDetailComponent implements OnInit {
  typeCarnet: ITypeCarnet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCarnet }) => (this.typeCarnet = typeCarnet));
  }

  previousState(): void {
    window.history.back();
  }
}
