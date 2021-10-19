import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeCarnet, TypeCarnet } from 'app/shared/model/type-carnet.model';
import { TypeCarnetService } from './type-carnet.service';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';

@Component({
  selector: 'jhi-type-carnet-update',
  templateUrl: './type-carnet-update.component.html'
})
export class TypeCarnetUpdateComponent implements OnInit {
  isSaving = false;
  typevehicules: ITypeVehicule[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    quantiteCertificat: [],
    typeVehiculeId: []
  });

  constructor(
    protected typeCarnetService: TypeCarnetService,
    protected typeVehiculeService: TypeVehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeCarnet }) => {
      this.updateForm(typeCarnet);

      this.typeVehiculeService.query().subscribe((res: HttpResponse<ITypeVehicule[]>) => (this.typevehicules = res.body || []));
    });
  }

  updateForm(typeCarnet: ITypeCarnet): void {
    this.editForm.patchValue({
      id: typeCarnet.id,
      code: typeCarnet.code,
      libelle: typeCarnet.libelle,
      quantiteCertificat: typeCarnet.quantiteCertificat,
      typeVehiculeId: typeCarnet.typeVehiculeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeCarnet = this.createFromForm();
    if (typeCarnet.id !== undefined) {
      this.subscribeToSaveResponse(this.typeCarnetService.update(typeCarnet));
    } else {
      this.subscribeToSaveResponse(this.typeCarnetService.create(typeCarnet));
    }
  }

  private createFromForm(): ITypeCarnet {
    return {
      ...new TypeCarnet(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      quantiteCertificat: this.editForm.get(['quantiteCertificat'])!.value,
      typeVehiculeId: this.editForm.get(['typeVehiculeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeCarnet>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITypeVehicule): any {
    return item.id;
  }
}
