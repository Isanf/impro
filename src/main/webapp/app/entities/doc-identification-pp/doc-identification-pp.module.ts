import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { DocIdentificationPPComponent } from './doc-identification-pp.component';
import { DocIdentificationPPDetailComponent } from './doc-identification-pp-detail.component';
import { DocIdentificationPPUpdateComponent } from './doc-identification-pp-update.component';
import { DocIdentificationPPDeleteDialogComponent } from './doc-identification-pp-delete-dialog.component';
import { docIdentificationPPRoute } from './doc-identification-pp.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(docIdentificationPPRoute)],
  declarations: [
    DocIdentificationPPComponent,
    DocIdentificationPPDetailComponent,
    DocIdentificationPPUpdateComponent,
    DocIdentificationPPDeleteDialogComponent
  ],
  entryComponents: [DocIdentificationPPDeleteDialogComponent]
})
export class ImproDocIdentificationPPModule {}
