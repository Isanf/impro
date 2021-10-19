import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVehiculeTraversant, VehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';
import { VehiculeTraversantService } from './vehicule-traversant.service';

@Component({
  selector: 'jhi-vehicule-traversant-update',
  templateUrl: './vehicule-traversant-update.component.html'
})
export class VehiculeTraversantUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    createdAt: []
  });

  constructor(
    protected vehiculeTraversantService: VehiculeTraversantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehiculeTraversant }) => {
      if (!vehiculeTraversant.id) {
        const today = moment().startOf('day');
        vehiculeTraversant.createdAt = today;
      }

      this.updateForm(vehiculeTraversant);
    });
  }

  updateForm(vehiculeTraversant: IVehiculeTraversant): void {
    this.editForm.patchValue({
      id: vehiculeTraversant.id,
      chassis: vehiculeTraversant.chassis,
      marque: vehiculeTraversant.marque,
      model: vehiculeTraversant.model,
      createdAt: vehiculeTraversant.createdAt ? vehiculeTraversant.createdAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehiculeTraversant = this.createFromForm();
    if (vehiculeTraversant.id !== undefined) {
      this.subscribeToSaveResponse(this.vehiculeTraversantService.update(vehiculeTraversant));
    } else {
      this.subscribeToSaveResponse(this.vehiculeTraversantService.create(vehiculeTraversant));
    }
  }

  private createFromForm(): IVehiculeTraversant {
    return {
      ...new VehiculeTraversant(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editForm.get(['chassis'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      model: this.editForm.get(['model'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehiculeTraversant>>): void {
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
