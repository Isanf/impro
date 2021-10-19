import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { LogActivityComponent } from './log-activity.component';
import { LogActivityDetailComponent } from './log-activity-detail.component';
import { LogActivityUpdateComponent } from './log-activity-update.component';
import { LogActivityDeleteDialogComponent } from './log-activity-delete-dialog.component';
import { logActivityRoute } from './log-activity.route';
import { NbButtonModule, NbCardModule } from '@nebular/theme';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(logActivityRoute), NbButtonModule, NbCardModule],
  declarations: [LogActivityComponent, LogActivityDetailComponent, LogActivityUpdateComponent, LogActivityDeleteDialogComponent],
  entryComponents: [LogActivityDeleteDialogComponent]
})
export class ImproLogActivityModule {}
