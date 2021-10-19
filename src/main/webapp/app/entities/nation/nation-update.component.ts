import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INation, Nation } from 'app/shared/model/nation.model';
import { NationService } from './nation.service';

@Component({
  selector: 'jhi-nation-update',
  templateUrl: './nation-update.component.html'
})
export class NationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iso: [],
    name: [],
    nicename: [],
    iso3: [],
    numcode: [],
    phonecode: []
  });

  constructor(protected nationService: NationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nation }) => {
      this.updateForm(nation);
    });
  }

  updateForm(nation: INation): void {
    this.editForm.patchValue({
      id: nation.id,
      iso: nation.iso,
      name: nation.name,
      nicename: nation.nicename,
      iso3: nation.iso3,
      numcode: nation.numcode,
      phonecode: nation.phonecode
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nation = this.createFromForm();
    if (nation.id !== undefined) {
      this.subscribeToSaveResponse(this.nationService.update(nation));
    } else {
      this.subscribeToSaveResponse(this.nationService.create(nation));
    }
  }

  private createFromForm(): INation {
    return {
      ...new Nation(),
      id: this.editForm.get(['id'])!.value,
      iso: this.editForm.get(['iso'])!.value,
      name: this.editForm.get(['name'])!.value,
      nicename: this.editForm.get(['nicename'])!.value,
      iso3: this.editForm.get(['iso3'])!.value,
      numcode: this.editForm.get(['numcode'])!.value,
      phonecode: this.editForm.get(['phonecode'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INation>>): void {
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
