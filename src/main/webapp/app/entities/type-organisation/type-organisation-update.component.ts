import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeOrganisation, TypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeOrganisationService } from './type-organisation.service';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { CategorieOrganisationService } from 'app/entities/categorie-organisation/categorie-organisation.service';

@Component({
  selector: 'jhi-type-organisation-update',
  templateUrl: './type-organisation-update.component.html'
})
export class TypeOrganisationUpdateComponent implements OnInit {
  isSaving = false;
  categorieorganisations: ICategorieOrganisation[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [null, [Validators.maxLength(1000)]],
    niveau: [null, [Validators.required]],
    categorieOrganisationId: []
  });

  constructor(
    protected typeOrganisationService: TypeOrganisationService,
    protected categorieOrganisationService: CategorieOrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeOrganisation }) => {
      this.updateForm(typeOrganisation);

      this.categorieOrganisationService
        .query()
        .subscribe((res: HttpResponse<ICategorieOrganisation[]>) => (this.categorieorganisations = res.body || []));
    });
  }

  updateForm(typeOrganisation: ITypeOrganisation): void {
    this.editForm.patchValue({
      id: typeOrganisation.id,
      nom: typeOrganisation.nom,
      description: typeOrganisation.description,
      niveau: typeOrganisation.niveau,
      categorieOrganisationId: typeOrganisation.categorieOrganisationId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeOrganisation = this.createFromForm();
    if (typeOrganisation.id !== undefined) {
      this.subscribeToSaveResponse(this.typeOrganisationService.update(typeOrganisation));
    } else {
      this.subscribeToSaveResponse(this.typeOrganisationService.create(typeOrganisation));
    }
  }

  private createFromForm(): ITypeOrganisation {
    return {
      ...new TypeOrganisation(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      categorieOrganisationId: this.editForm.get(['categorieOrganisationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeOrganisation>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICategorieOrganisation): any {
    return item.id;
  }
}
