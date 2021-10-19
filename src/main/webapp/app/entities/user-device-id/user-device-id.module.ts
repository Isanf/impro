import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { UserDeviceIdComponent } from './user-device-id.component';
import { UserDeviceIdDetailComponent } from './user-device-id-detail.component';
import { UserDeviceIdUpdateComponent } from './user-device-id-update.component';
import { UserDeviceIdDeleteDialogComponent } from './user-device-id-delete-dialog.component';
import { userDeviceIdRoute } from './user-device-id.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(userDeviceIdRoute)],
  declarations: [UserDeviceIdComponent, UserDeviceIdDetailComponent, UserDeviceIdUpdateComponent, UserDeviceIdDeleteDialogComponent],
  entryComponents: [UserDeviceIdDeleteDialogComponent]
})
export class ImproUserDeviceIdModule {}
