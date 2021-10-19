import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICertificatImmatriculation, CertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';
import { CertificatImmatriculationService } from './certificat-immatriculation.service';
import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { CarnetASoucheService } from 'app/entities/carnet-a-souche/carnet-a-souche.service';

@Component({
  selector: 'jhi-certificat-immatriculation-update',
  templateUrl: './certificat-immatriculation-update.component.html'
})
export class CertificatImmatriculationUpdateComponent implements OnInit {
  isSaving = false;
  carnetasouches: ICarnetASouche[] = [];

  editForm = this.fb.group({
    id: [],
    numero: [],
    codeQr: [],
    carnetASoucheId: []
  });

  constructor(
    protected certificatImmatriculationService: CertificatImmatriculationService,
    protected carnetASoucheService: CarnetASoucheService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ certificatImmatriculation }) => {
      this.updateForm(certificatImmatriculation);

      this.carnetASoucheService.query().subscribe((res: HttpResponse<ICarnetASouche[]>) => (this.carnetasouches = res.body || []));
    });
  }

  updateForm(certificatImmatriculation: ICertificatImmatriculation): void {
    this.editForm.patchValue({
      id: certificatImmatriculation.id,
      numero: certificatImmatriculation.numero,
      codeQr: certificatImmatriculation.codeQr,
      carnetASoucheId: certificatImmatriculation.carnetASoucheId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const certificatImmatriculation = this.createFromForm();
    if (certificatImmatriculation.id !== undefined) {
      this.subscribeToSaveResponse(this.certificatImmatriculationService.update(certificatImmatriculation));
    } else {
      this.subscribeToSaveResponse(this.certificatImmatriculationService.create(certificatImmatriculation));
    }
  }

  private createFromForm(): ICertificatImmatriculation {
    return {
      ...new CertificatImmatriculation(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      codeQr: this.editForm.get(['codeQr'])!.value,
      carnetASoucheId: this.editForm.get(['carnetASoucheId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICertificatImmatriculation>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICarnetASouche): any {
    return item.id;
  }
}
