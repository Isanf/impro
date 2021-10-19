import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPosePlaque, PosePlaque } from 'app/shared/model/pose-plaque.model';
import { PosePlaqueService } from './pose-plaque.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-pose-plaque-update',
  templateUrl: './pose-plaque-update.component.html'
})
export class PosePlaqueUpdateComponent implements OnInit {
  isSaving: boolean;

  organisations: IOrganisation[];

  editForm = this.fb.group({
    id: [],
    numeroPose: [],
    datePosePlaque: [],
    revendeurId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected posePlaqueService: PosePlaqueService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ posePlaque }) => {
      this.updateForm(posePlaque);
    });
    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(posePlaque: IPosePlaque) {
    this.editForm.patchValue({
      id: posePlaque.id,
      numeroPose: posePlaque.numeroPose,
      datePosePlaque: posePlaque.datePosePlaque != null ? posePlaque.datePosePlaque.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const posePlaque = this.createFromForm();
    if (posePlaque.id !== undefined) {
      this.subscribeToSaveResponse(this.posePlaqueService.update(posePlaque));
    } else {
      this.subscribeToSaveResponse(this.posePlaqueService.create(posePlaque));
    }
  }

  private createFromForm(): IPosePlaque {
    return {
      ...new PosePlaque(),
      id: this.editForm.get(['id']).value,
      numeroPose: this.editForm.get(['numeroPose']).value,
      datePosePlaque:
        this.editForm.get(['datePosePlaque']).value != null
          ? moment(this.editForm.get(['datePosePlaque']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPosePlaque>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrganisationById(index: number, item: IOrganisation) {
    return item.id;
  }
}
