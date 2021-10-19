import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategorieOrganisation, CategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { CategorieOrganisationService } from './categorie-organisation.service';

@Component({
  selector: 'jhi-categorie-organisation-update',
  templateUrl: './categorie-organisation-update.component.html'
})
export class CategorieOrganisationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    description: [],
    typeCategorieOrganisation: []
  });
  SUPERNET = 'SUPERNET';
  CONCESSIONNAIRE = 'CONCESSIONNAIRE';
  ETABLISSEMENT_SPECIALISE = 'ETABLISSEMENT_SPECIALISE';
  DGTTM = 'DGTTM';
  FDS = 'FDS';
  CCVA = 'CCVA';
  ASSURANCE = 'ASSURANCE';

  constructor(
    protected categorieOrganisationService: CategorieOrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieOrganisation }) => {
      this.updateForm(categorieOrganisation);
    });
  }

  updateForm(categorieOrganisation: ICategorieOrganisation): void {
    this.editForm.patchValue({
      id: categorieOrganisation.id,
      libelle: categorieOrganisation.libelle,
      description: categorieOrganisation.description,
      typeCategorieOrganisation: categorieOrganisation.typeCategorieOrganisation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieOrganisation = this.createFromForm();
    if (categorieOrganisation.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieOrganisationService.update(categorieOrganisation));
    } else {
      this.subscribeToSaveResponse(this.categorieOrganisationService.create(categorieOrganisation));
    }
  }

  private createFromForm(): ICategorieOrganisation {
    return {
      ...new CategorieOrganisation(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      description: this.editForm.get(['description'])!.value,
      typeCategorieOrganisation: this.editForm.get(['typeCategorieOrganisation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieOrganisation>>): void {
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
