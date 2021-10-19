import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserOtp, UserOtp } from 'app/shared/model/user-otp.model';
import { UserOtpService } from './user-otp.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-otp-update',
  templateUrl: './user-otp-update.component.html'
})
export class UserOtpUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    otpNumber: [],
    otpUsed: [],
    userId: []
  });

  constructor(
    protected userOtpService: UserOtpService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userOtp }) => {
      this.updateForm(userOtp);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userOtp: IUserOtp): void {
    this.editForm.patchValue({
      id: userOtp.id,
      otpNumber: userOtp.otpNumber,
      otpUsed: userOtp.otpUsed,
      userId: userOtp.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userOtp = this.createFromForm();
    if (userOtp.id !== undefined) {
      this.subscribeToSaveResponse(this.userOtpService.update(userOtp));
    } else {
      this.subscribeToSaveResponse(this.userOtpService.create(userOtp));
    }
  }

  private createFromForm(): IUserOtp {
    return {
      ...new UserOtp(),
      id: this.editForm.get(['id'])!.value,
      otpNumber: this.editForm.get(['otpNumber'])!.value,
      otpUsed: this.editForm.get(['otpUsed'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserOtp>>): void {
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
