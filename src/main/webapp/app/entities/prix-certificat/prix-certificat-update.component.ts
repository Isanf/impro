import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPrixCertificat, PrixCertificat } from 'app/shared/model/prix-certificat.model';
import { PrixCertificatService } from './prix-certificat.service';

@Component({
  selector: 'jhi-prix-certificat-update',
  templateUrl: './prix-certificat-update.component.html'
})
export class PrixCertificatUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    prix: [null, [Validators.required]],
    activated: []
  });

  constructor(protected prixCertificatService: PrixCertificatService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prixCertificat }) => {
      this.updateForm(prixCertificat);
    });
  }

  updateForm(prixCertificat: IPrixCertificat): void {
    this.editForm.patchValue({
      id: prixCertificat.id,
      prix: prixCertificat.prix,
      activated: prixCertificat.activated
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prixCertificat = this.createFromForm();
    if (prixCertificat.id !== undefined) {
      this.subscribeToSaveResponse(this.prixCertificatService.update(prixCertificat));
    } else {
      this.subscribeToSaveResponse(this.prixCertificatService.create(prixCertificat));
    }
  }

  private createFromForm(): IPrixCertificat {
    return {
      ...new PrixCertificat(),
      id: this.editForm.get(['id'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      activated: this.editForm.get(['activated'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrixCertificat>>): void {
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
