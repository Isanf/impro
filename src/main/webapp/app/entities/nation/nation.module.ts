import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { NationComponent } from './nation.component';
import { NationDetailComponent } from './nation-detail.component';
import { NationUpdateComponent } from './nation-update.component';
import { NationDeleteDialogComponent } from './nation-delete-dialog.component';
import { nationRoute } from './nation.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(nationRoute)],
  declarations: [NationComponent, NationDetailComponent, NationUpdateComponent, NationDeleteDialogComponent],
  entryComponents: [NationDeleteDialogComponent]
})
export class ImproNationModule {}
