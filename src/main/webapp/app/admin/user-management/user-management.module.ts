import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './user-management.component';
import { UserManagementDetailComponent } from './user-management-detail.component';
import { UserManagementUpdateComponent } from './user-management-update.component';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';
import {
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbStepperModule,
  NbTabsetModule
} from '@nebular/theme';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(userManagementRoute),
    NbCardModule,
    NbInputModule,
    NbIconModule,
    NbStepperModule,
    NbCheckboxModule,
    NbTabsetModule,
    NbSelectModule,
    NbButtonModule
  ],
  declarations: [
    UserManagementComponent,
    UserManagementDetailComponent,
    UserManagementUpdateComponent,
    UserManagementDeleteDialogComponent
  ],
  entryComponents: [UserManagementDeleteDialogComponent]
})
export class UserManagementModule {}
