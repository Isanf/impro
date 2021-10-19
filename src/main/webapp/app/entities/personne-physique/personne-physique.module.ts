import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { PersonnePhysiqueComponent } from './personne-physique.component';
import { PersonnePhysiqueDetailComponent } from './personne-physique-detail.component';
import { PersonnePhysiqueUpdateComponent } from './personne-physique-update.component';
import { PersonnePhysiqueDeleteDialogComponent } from './personne-physique-delete-dialog.component';
import { personnePhysiqueRoute } from './personne-physique.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(personnePhysiqueRoute)],
  declarations: [
    PersonnePhysiqueComponent,
    PersonnePhysiqueDetailComponent,
    PersonnePhysiqueUpdateComponent,
    PersonnePhysiqueDeleteDialogComponent
  ],
  entryComponents: [PersonnePhysiqueDeleteDialogComponent]
})
export class ImproPersonnePhysiqueModule {}
