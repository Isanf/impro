import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

@Component({
  selector: 'jhi-categorie-organisation-detail',
  templateUrl: './categorie-organisation-detail.component.html'
})
export class CategorieOrganisationDetailComponent implements OnInit {
  categorieOrganisation: ICategorieOrganisation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieOrganisation }) => (this.categorieOrganisation = categorieOrganisation));
  }

  previousState(): void {
    window.history.back();
  }
}
