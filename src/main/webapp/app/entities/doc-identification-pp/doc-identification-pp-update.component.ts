import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDocIdentificationPP, DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPPService } from './doc-identification-pp.service';

@Component({
  selector: 'jhi-doc-identification-pp-update',
  templateUrl: './doc-identification-pp-update.component.html'
})
export class DocIdentificationPPUpdateComponent implements OnInit {
  isSaving = false;
  dateEtablissementDp: any;

  editForm = this.fb.group({
    id: [],
    numeroDoc: [],
    nip: [],
    dateEtablissement: [],
    lieuEtablissement: [],
    autoriteEmettrice: [],
    typeDocIdentification: []
  });

  constructor(
    protected docIdentificationPPService: DocIdentificationPPService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docIdentificationPP }) => {
      this.updateForm(docIdentificationPP);
    });
  }

  updateForm(docIdentificationPP: IDocIdentificationPP): void {
    this.editForm.patchValue({
      id: docIdentificationPP.id,
      numeroDoc: docIdentificationPP.numeroDoc,
      nip: docIdentificationPP.nip,
      dateEtablissement: docIdentificationPP.dateEtablissement,
      lieuEtablissement: docIdentificationPP.lieuEtablissement,
      autoriteEmettrice: docIdentificationPP.autoriteEmettrice,
      typeDocIdentification: docIdentificationPP.typeDocIdentification
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const docIdentificationPP = this.createFromForm();
    if (docIdentificationPP.id !== undefined) {
      this.subscribeToSaveResponse(this.docIdentificationPPService.update(docIdentificationPP));
    } else {
      this.subscribeToSaveResponse(this.docIdentificationPPService.create(docIdentificationPP));
    }
  }

  private createFromForm(): IDocIdentificationPP {
    return {
      ...new DocIdentificationPP(),
      id: this.editForm.get(['id'])!.value,
      numeroDoc: this.editForm.get(['numeroDoc'])!.value,
      nip: this.editForm.get(['nip'])!.value,
      dateEtablissement: this.editForm.get(['dateEtablissement'])!.value,
      lieuEtablissement: this.editForm.get(['lieuEtablissement'])!.value,
      autoriteEmettrice: this.editForm.get(['autoriteEmettrice'])!.value,
      typeDocIdentification: this.editForm.get(['typeDocIdentification'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocIdentificationPP>>): void {
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
