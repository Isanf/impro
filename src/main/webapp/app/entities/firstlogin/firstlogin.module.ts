import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { FirstloginComponent } from './firstlogin.component';
import { FirstloginDetailComponent } from './firstlogin-detail.component';
import { FirstloginUpdateComponent } from './firstlogin-update.component';
import { FirstloginDeleteDialogComponent } from './firstlogin-delete-dialog.component';
import { firstloginRoute } from './firstlogin.route';
import { NbButtonModule, NbCardModule } from '@nebular/theme';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(firstloginRoute), NbCardModule, NbButtonModule],
  declarations: [FirstloginComponent, FirstloginDetailComponent, FirstloginUpdateComponent, FirstloginDeleteDialogComponent],
  entryComponents: [FirstloginDeleteDialogComponent]
})
export class ImproFirstloginModule {}
