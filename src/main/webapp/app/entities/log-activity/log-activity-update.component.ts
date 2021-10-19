import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILogActivity, LogActivity } from 'app/shared/model/log-activity.model';
import { LogActivityService } from './log-activity.service';

@Component({
  selector: 'jhi-log-activity-update',
  templateUrl: './log-activity-update.component.html'
})
export class LogActivityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    principal: [],
    url: [],
    action: [],
    ip: [],
    dateAction: []
  });

  constructor(protected logActivityService: LogActivityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logActivity }) => {
      if (!logActivity.id) {
        const today = moment().startOf('day');
        logActivity.dateAction = today;
      }

      this.updateForm(logActivity);
    });
  }

  updateForm(logActivity: ILogActivity): void {
    this.editForm.patchValue({
      id: logActivity.id,
      principal: logActivity.principal,
      url: logActivity.url,
      action: logActivity.action,
      ip: logActivity.ip,
      dateAction: logActivity.dateAction ? logActivity.dateAction.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logActivity = this.createFromForm();
    if (logActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.logActivityService.update(logActivity));
    } else {
      this.subscribeToSaveResponse(this.logActivityService.create(logActivity));
    }
  }

  private createFromForm(): ILogActivity {
    return {
      ...new LogActivity(),
      id: this.editForm.get(['id'])!.value,
      principal: this.editForm.get(['principal'])!.value,
      url: this.editForm.get(['url'])!.value,
      action: this.editForm.get(['action'])!.value,
      ip: this.editForm.get(['ip'])!.value,
      dateAction: this.editForm.get(['dateAction'])!.value ? moment(this.editForm.get(['dateAction'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogActivity>>): void {
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
