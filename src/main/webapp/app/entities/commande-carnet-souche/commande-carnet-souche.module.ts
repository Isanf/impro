import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CommandeCarnetSoucheComponent } from './commande-carnet-souche.component';
import { CommandeCarnetSoucheDetailComponent } from './commande-carnet-souche-detail.component';
import { CommandeCarnetSoucheUpdateComponent } from './commande-carnet-souche-update.component';
import { CommandeCarnetSoucheDeleteDialogComponent } from './commande-carnet-souche-delete-dialog.component';
import { commandeCarnetSoucheRoute } from './commande-carnet-souche.route';
import {
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbListModule,
  NbSearchModule,
  NbSelectModule,
  NbSpinnerModule,
  NbStepperModule
} from '@nebular/theme';
import { CommandeCarnetSoucheFilter } from 'app/entities/commande-carnet-souche/commandeCarnetFilter';
@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(commandeCarnetSoucheRoute),
    NbCardModule,
    NbStepperModule,
    NbListModule,
    NbIconModule,
    NbButtonModule,
    NbSearchModule,
    NbFormFieldModule,
    NbInputModule,
    NbSelectModule,
    NbSpinnerModule,
    NbCheckboxModule
  ],
  declarations: [
    CommandeCarnetSoucheComponent,
    CommandeCarnetSoucheDetailComponent,
    CommandeCarnetSoucheUpdateComponent,
    CommandeCarnetSoucheDeleteDialogComponent,
    CommandeCarnetSoucheFilter
  ]
})
export class ImproCommandeCarnetSoucheModule {}
