import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserDeviceId, UserDeviceId } from 'app/shared/model/user-device-id.model';
import { UserDeviceIdService } from './user-device-id.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-device-id-update',
  templateUrl: './user-device-id-update.component.html'
})
export class UserDeviceIdUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    adressMac: [],
    deviceId: [],
    userId: []
  });

  constructor(
    protected userDeviceIdService: UserDeviceIdService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDeviceId }) => {
      this.updateForm(userDeviceId);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userDeviceId: IUserDeviceId): void {
    this.editForm.patchValue({
      id: userDeviceId.id,
      adressMac: userDeviceId.adressMac,
      deviceId: userDeviceId.deviceId,
      userId: userDeviceId.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userDeviceId = this.createFromForm();
    if (userDeviceId.id !== undefined) {
      this.subscribeToSaveResponse(this.userDeviceIdService.update(userDeviceId));
    } else {
      this.subscribeToSaveResponse(this.userDeviceIdService.create(userDeviceId));
    }
  }

  private createFromForm(): IUserDeviceId {
    return {
      ...new UserDeviceId(),
      id: this.editForm.get(['id'])!.value,
      adressMac: this.editForm.get(['adressMac'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserDeviceId>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
