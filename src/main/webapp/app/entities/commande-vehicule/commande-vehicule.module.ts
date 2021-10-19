import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CommandeVehiculeComponent } from './commande-vehicule.component';
import { CommandeVehiculeDetailComponent } from './commande-vehicule-detail.component';
import { CommandeVehiculeUpdateComponent } from './commande-vehicule-update.component';
import { CommandeVehiculeDeleteDialogComponent } from './commande-vehicule-delete-dialog.component';
import { commandeVehiculeRoute } from './commande-vehicule.route';
import {
  NbAlertModule,
  NbButtonModule,
  NbCardModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbListModule,
  NbSelectModule,
  NbStepperModule
} from '@nebular/theme';
import { CommandeVehiculeFilter } from 'app/entities/commande-vehicule/commandeVehiculeFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(commandeVehiculeRoute),
    NbCardModule,
    NbStepperModule,
    NbIconModule,
    NbListModule,
    NbAlertModule,
    NbButtonModule,
    NbFormFieldModule,
    NbInputModule,
    NbSelectModule
  ],
  declarations: [
    CommandeVehiculeComponent,
    CommandeVehiculeDetailComponent,
    CommandeVehiculeUpdateComponent,
    CommandeVehiculeDeleteDialogComponent,
    CommandeVehiculeFilter
  ],
  entryComponents: [CommandeVehiculeDeleteDialogComponent]
})
export class ImproCommandeVehiculeModule {}
