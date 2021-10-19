import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { UserOtpComponent } from './user-otp.component';
import { UserOtpDetailComponent } from './user-otp-detail.component';
import { UserOtpUpdateComponent } from './user-otp-update.component';
import { UserOtpDeleteDialogComponent } from './user-otp-delete-dialog.component';
import { userOtpRoute } from './user-otp.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(userOtpRoute)],
  declarations: [UserOtpComponent, UserOtpDetailComponent, UserOtpUpdateComponent, UserOtpDeleteDialogComponent],
  entryComponents: [UserOtpDeleteDialogComponent]
})
export class ImproUserOtpModule {}
