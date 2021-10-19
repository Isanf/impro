import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserDeviceId } from 'app/shared/model/user-device-id.model';
import { UserDeviceIdService } from './user-device-id.service';

@Component({
  templateUrl: './user-device-id-delete-dialog.component.html'
})
export class UserDeviceIdDeleteDialogComponent {
  userDeviceId?: IUserDeviceId;

  constructor(
    protected userDeviceIdService: UserDeviceIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userDeviceIdService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userDeviceIdListModification');
      this.activeModal.close();
    });
  }
}
