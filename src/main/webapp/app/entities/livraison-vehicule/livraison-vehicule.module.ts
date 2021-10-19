import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { LivraisonVehiculeComponent } from './livraison-vehicule.component';
import { LivraisonVehiculeDetailComponent } from './livraison-vehicule-detail.component';
import { LivraisonVehiculeUpdateComponent } from './livraison-vehicule-update.component';
import { LivraisonVehiculeDeleteDialogComponent } from './livraison-vehicule-delete-dialog.component';
import { livraisonVehiculeRoute } from './livraison-vehicule.route';
import {
  NbAlertModule,
  NbButtonModule,
  NbCardModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbStepperModule
} from '@nebular/theme';
import { LivraisonVehiculeFilter } from 'app/entities/livraison-vehicule/livraisonVehiculeFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(livraisonVehiculeRoute),
    NbIconModule,
    NbStepperModule,
    NbAlertModule,
    NbCardModule,
    NbSelectModule,
    NbFormFieldModule,
    NbInputModule,
    NbButtonModule
  ],
  declarations: [
    LivraisonVehiculeComponent,
    LivraisonVehiculeDetailComponent,
    LivraisonVehiculeUpdateComponent,
    LivraisonVehiculeDeleteDialogComponent,
    LivraisonVehiculeFilter
  ],
  entryComponents: [LivraisonVehiculeDeleteDialogComponent]
})
export class ImproLivraisonVehiculeModule {}
