import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVehiculeOccasional, VehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';
import { VehiculeOccasionalService } from './vehicule-occasional.service';

@Component({
  selector: 'jhi-vehicule-occasional-update',
  templateUrl: './vehicule-occasional-update.component.html'
})
export class VehiculeOccasionalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    createdAt: []
  });

  constructor(
    protected vehiculeOccasionalService: VehiculeOccasionalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehiculeOccasional }) => {
      if (!vehiculeOccasional.id) {
        const today = moment().startOf('day');
        vehiculeOccasional.createdAt = today;
      }

      this.updateForm(vehiculeOccasional);
    });
  }

  updateForm(vehiculeOccasional: IVehiculeOccasional): void {
    this.editForm.patchValue({
      id: vehiculeOccasional.id,
      chassis: vehiculeOccasional.chassis,
      marque: vehiculeOccasional.marque,
      model: vehiculeOccasional.model,
      createdAt: vehiculeOccasional.createdAt ? vehiculeOccasional.createdAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehiculeOccasional = this.createFromForm();
    if (vehiculeOccasional.id !== undefined) {
      this.subscribeToSaveResponse(this.vehiculeOccasionalService.update(vehiculeOccasional));
    } else {
      this.subscribeToSaveResponse(this.vehiculeOccasionalService.create(vehiculeOccasional));
    }
  }

  private createFromForm(): IVehiculeOccasional {
    return {
      ...new VehiculeOccasional(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editForm.get(['chassis'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      model: this.editForm.get(['model'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehiculeOccasional>>): void {
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
