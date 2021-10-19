import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserDeviceId } from 'app/shared/model/user-device-id.model';

@Component({
  selector: 'jhi-user-device-id-detail',
  templateUrl: './user-device-id-detail.component.html'
})
export class UserDeviceIdDetailComponent implements OnInit {
  userDeviceId: IUserDeviceId | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userDeviceId }) => (this.userDeviceId = userDeviceId));
  }

  previousState(): void {
    window.history.back();
  }
}
