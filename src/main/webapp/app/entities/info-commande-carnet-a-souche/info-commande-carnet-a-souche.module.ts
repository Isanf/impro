import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { InfoCommandeCarnetASoucheComponent } from './info-commande-carnet-a-souche.component';
import { InfoCommandeCarnetASoucheDetailComponent } from './info-commande-carnet-a-souche-detail.component';
import { InfoCommandeCarnetASoucheUpdateComponent } from './info-commande-carnet-a-souche-update.component';
import { InfoCommandeCarnetASoucheDeleteDialogComponent } from './info-commande-carnet-a-souche-delete-dialog.component';
import { infoCommandeCarnetASoucheRoute } from './info-commande-carnet-a-souche.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(infoCommandeCarnetASoucheRoute)],
  declarations: [
    InfoCommandeCarnetASoucheComponent,
    InfoCommandeCarnetASoucheDetailComponent,
    InfoCommandeCarnetASoucheUpdateComponent,
    InfoCommandeCarnetASoucheDeleteDialogComponent
  ],
  entryComponents: [InfoCommandeCarnetASoucheDeleteDialogComponent]
})
export class ImproInfoCommandeCarnetASoucheModule {}
