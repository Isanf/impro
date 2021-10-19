import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDocIdentificationPM, DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { DocIdentificationPMService } from './doc-identification-pm.service';

@Component({
  selector: 'jhi-doc-identification-pm-update',
  templateUrl: './doc-identification-pm-update.component.html'
})
export class DocIdentificationPMUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [],
    numeroIFU: [],
    numeroRCCM: []
  });

  constructor(
    protected docIdentificationPMService: DocIdentificationPMService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docIdentificationPM }) => {
      this.updateForm(docIdentificationPM);
    });
  }

  updateForm(docIdentificationPM: IDocIdentificationPM): void {
    this.editForm.patchValue({
      id: docIdentificationPM.id,
      numero: docIdentificationPM.numero,
      numeroIFU: docIdentificationPM.numeroIFU,
      numeroRCCM: docIdentificationPM.numeroRCCM
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const docIdentificationPM = this.createFromForm();
    if (docIdentificationPM.id !== undefined) {
      this.subscribeToSaveResponse(this.docIdentificationPMService.update(docIdentificationPM));
    } else {
      this.subscribeToSaveResponse(this.docIdentificationPMService.create(docIdentificationPM));
    }
  }

  private createFromForm(): IDocIdentificationPM {
    return {
      ...new DocIdentificationPM(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      numeroIFU: this.editForm.get(['numeroIFU'])!.value,
      numeroRCCM: this.editForm.get(['numeroRCCM'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocIdentificationPM>>): void {
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
