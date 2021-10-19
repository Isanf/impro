import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserOtp } from 'app/shared/model/user-otp.model';

@Component({
  selector: 'jhi-user-otp-detail',
  templateUrl: './user-otp-detail.component.html'
})
export class UserOtpDetailComponent implements OnInit {
  userOtp: IUserOtp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userOtp }) => (this.userOtp = userOtp));
  }

  previousState(): void {
    window.history.back();
  }
}
