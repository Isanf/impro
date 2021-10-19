import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { DocIdentificationPMComponent } from './doc-identification-pm.component';
import { DocIdentificationPMDetailComponent } from './doc-identification-pm-detail.component';
import { DocIdentificationPMUpdateComponent } from './doc-identification-pm-update.component';
import { DocIdentificationPMDeleteDialogComponent } from './doc-identification-pm-delete-dialog.component';
import { docIdentificationPMRoute } from './doc-identification-pm.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(docIdentificationPMRoute)],
  declarations: [
    DocIdentificationPMComponent,
    DocIdentificationPMDetailComponent,
    DocIdentificationPMUpdateComponent,
    DocIdentificationPMDeleteDialogComponent
  ],
  entryComponents: [DocIdentificationPMDeleteDialogComponent]
})
export class ImproDocIdentificationPMModule {}
