import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrganisationLocalite, OrganisationLocalite } from 'app/shared/model/organisation-localite.model';
import { OrganisationLocaliteService } from './organisation-localite.service';

@Component({
  selector: 'jhi-organisation-localite-update',
  templateUrl: './organisation-localite-update.component.html'
})
export class OrganisationLocaliteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    nom: [],
    description: []
  });

  constructor(
    protected organisationLocaliteService: OrganisationLocaliteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisationLocalite }) => {
      this.updateForm(organisationLocalite);
    });
  }

  updateForm(organisationLocalite: IOrganisationLocalite): void {
    this.editForm.patchValue({
      id: organisationLocalite.id,
      code: organisationLocalite.code,
      nom: organisationLocalite.nom,
      description: organisationLocalite.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisationLocalite = this.createFromForm();
    if (organisationLocalite.id !== undefined) {
      this.subscribeToSaveResponse(this.organisationLocaliteService.update(organisationLocalite));
    } else {
      this.subscribeToSaveResponse(this.organisationLocaliteService.create(organisationLocalite));
    }
  }

  private createFromForm(): IOrganisationLocalite {
    return {
      ...new OrganisationLocalite(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisationLocalite>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
