import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMarqueVehicule, MarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { MarqueVehiculeService } from './marque-vehicule.service';

@Component({
  selector: 'jhi-marque-vehicule-update',
  templateUrl: './marque-vehicule-update.component.html'
})
export class MarqueVehiculeUpdateComponent implements OnInit {
  isSaving = false;
  fileToUpload: File = null;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    file: []
  });

  constructor(protected marqueVehiculeService: MarqueVehiculeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ marqueVehicule }) => {
      this.updateForm(marqueVehicule);
    });
  }

  updateForm(marqueVehicule: IMarqueVehicule): void {
    this.editForm.patchValue({
      id: marqueVehicule.id,
      code: marqueVehicule.code,
      libelle: marqueVehicule.libelle
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const marqueVehicule = this.createFromForm();
    if (marqueVehicule.id !== undefined) {
      this.subscribeToSaveResponse(this.marqueVehiculeService.update(marqueVehicule));
    } else {
      //this.subscribeToSaveResponse(this.marqueVehiculeService.create(marqueVehicule));
      this.subscribeToSaveResponse(this.marqueVehiculeService.createFile(this.fileToUpload));
    }
  }

  private createFromForm(): IMarqueVehicule {
    return {
      ...new MarqueVehicule(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      fichierMarque: this.editForm.get(['file']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMarqueVehicule>>): void {
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
