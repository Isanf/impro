import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';

@Component({
  selector: 'jhi-type-organisation-detail',
  templateUrl: './type-organisation-detail.component.html'
})
export class TypeOrganisationDetailComponent implements OnInit {
  typeOrganisation: ITypeOrganisation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeOrganisation }) => (this.typeOrganisation = typeOrganisation));
  }

  previousState(): void {
    window.history.back();
  }
}
