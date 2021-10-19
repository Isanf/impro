import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserOtp } from 'app/shared/model/user-otp.model';
import { UserOtpService } from './user-otp.service';

@Component({
  templateUrl: './user-otp-delete-dialog.component.html'
})
export class UserOtpDeleteDialogComponent {
  userOtp?: IUserOtp;

  constructor(protected userOtpService: UserOtpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userOtpService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userOtpListModification');
      this.activeModal.close();
    });
  }
}
