import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeVehicule, TypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from './type-vehicule.service';

@Component({
  selector: 'jhi-type-vehicule-update',
  templateUrl: './type-vehicule-update.component.html'
})
export class TypeVehiculeUpdateComponent implements OnInit {
  isSaving = false;
  fileToUpload: File = null;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    nombrePlaque: [],
    estCycleMoteur: [],
    file: []
  });

  constructor(protected typeVehiculeService: TypeVehiculeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeVehicule }) => {
      this.updateForm(typeVehicule);
    });
  }

  updateForm(typeVehicule: ITypeVehicule): void {
    this.editForm.patchValue({
      id: typeVehicule.id,
      code: typeVehicule.code,
      libelle: typeVehicule.libelle,
      nombrePlaque: typeVehicule.nombrePlaque,
      estCycleMoteur: typeVehicule.estCycleMoteur
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeVehicule = this.createFromForm();
    if (typeVehicule.id !== undefined) {
      this.subscribeToSaveResponse(this.typeVehiculeService.update(typeVehicule));
    } else {
      this.subscribeToSaveResponse(this.typeVehiculeService.createFile(this.fileToUpload));
    }
  }

  private createFromForm(): ITypeVehicule {
    return {
      ...new TypeVehicule(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      nombrePlaque: this.editForm.get(['nombrePlaque'])!.value,
      estCycleMoteur: this.editForm.get(['estCycleMoteur'])!.value,
      fichierType: this.editForm.get(['file']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeVehicule>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
