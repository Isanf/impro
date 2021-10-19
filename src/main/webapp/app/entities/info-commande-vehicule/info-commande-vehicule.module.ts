import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { InfoCommandeVehiculeComponent } from './info-commande-vehicule.component';
import { InfoCommandeVehiculeDetailComponent } from './info-commande-vehicule-detail.component';
import { InfoCommandeVehiculeUpdateComponent } from './info-commande-vehicule-update.component';
import { InfoCommandeVehiculeDeleteDialogComponent } from './info-commande-vehicule-delete-dialog.component';
import { infoCommandeVehiculeRoute } from './info-commande-vehicule.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(infoCommandeVehiculeRoute)],
  declarations: [
    InfoCommandeVehiculeComponent,
    InfoCommandeVehiculeDetailComponent,
    InfoCommandeVehiculeUpdateComponent,
    InfoCommandeVehiculeDeleteDialogComponent
  ],
  entryComponents: [InfoCommandeVehiculeDeleteDialogComponent]
})
export class ImproInfoCommandeVehiculeModule {}
