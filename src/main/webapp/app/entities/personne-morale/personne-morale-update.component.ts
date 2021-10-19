import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPersonneMorale, PersonneMorale } from 'app/shared/model/personne-morale.model';
import { PersonneMoraleService } from './personne-morale.service';

@Component({
  selector: 'jhi-personne-morale-update',
  templateUrl: './personne-morale-update.component.html'
})
export class PersonneMoraleUpdateComponent implements OnInit {
  isSaving: boolean;
  dateCreateDp: any;

  editForm = this.fb.group({
    id: [],
    numeroIFU: [],
    denomination: [],
    dateCreate: []
  });

  constructor(protected personneMoraleService: PersonneMoraleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personneMorale }) => {
      this.updateForm(personneMorale);
    });
  }

  updateForm(personneMorale: IPersonneMorale) {
    this.editForm.patchValue({
      id: personneMorale.id,
      numeroIFU: personneMorale.numeroIFU,
      denomination: personneMorale.denomination,
      dateCreate: personneMorale.dateCreate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personneMorale = this.createFromForm();
    if (personneMorale.id !== undefined) {
      this.subscribeToSaveResponse(this.personneMoraleService.update(personneMorale));
    } else {
      this.subscribeToSaveResponse(this.personneMoraleService.create(personneMorale));
    }
  }

  private createFromForm(): IPersonneMorale {
    return {
      ...new PersonneMorale(),
      id: this.editForm.get(['id']).value,
      numeroIFU: this.editForm.get(['numeroIFU']).value,
      denomination: this.editForm.get(['denomination']).value,
      dateCreate: this.editForm.get(['dateCreate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonneMorale>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
