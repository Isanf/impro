import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { PersonneMoraleComponent } from './personne-morale.component';
import { PersonneMoraleDetailComponent } from './personne-morale-detail.component';
import { PersonneMoraleUpdateComponent } from './personne-morale-update.component';
import { PersonneMoraleDeleteDialogComponent } from './personne-morale-delete-dialog.component';
import { personneMoraleRoute } from './personne-morale.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(personneMoraleRoute)],
  declarations: [
    PersonneMoraleComponent,
    PersonneMoraleDetailComponent,
    PersonneMoraleUpdateComponent,
    PersonneMoraleDeleteDialogComponent
  ],
  entryComponents: [PersonneMoraleDeleteDialogComponent]
})
export class ImproPersonneMoraleModule {}
