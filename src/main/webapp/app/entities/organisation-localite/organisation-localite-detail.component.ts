import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganisationLocalite } from 'app/shared/model/organisation-localite.model';

@Component({
  selector: 'jhi-organisation-localite-detail',
  templateUrl: './organisation-localite-detail.component.html'
})
export class OrganisationLocaliteDetailComponent implements OnInit {
  organisationLocalite: IOrganisationLocalite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisationLocalite }) => (this.organisationLocalite = organisationLocalite));
  }

  previousState(): void {
    window.history.back();
  }
}
