import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { VenteComponent } from './vente.component';
import { VenteDetailComponent } from './vente-detail.component';
import { VenteUpdateComponent } from './vente-update.component';
import { VenteDeleteDialogComponent } from './vente-delete-dialog.component';
import { venteRoute } from './vente.route';
import {
  NbAutocompleteModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbDatepickerModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbSpinnerModule,
  NbStepperModule,
  NbTabsetModule
} from '@nebular/theme';
import { VenteFilter } from 'app/entities/vente/venteFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(venteRoute),
    NbTabsetModule,
    NbStepperModule,
    NbSelectModule,
    NbCardModule,
    NbButtonModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbCheckboxModule,
    NbDatepickerModule,
    NbAutocompleteModule,
    NbSpinnerModule
  ],
  declarations: [VenteComponent, VenteDetailComponent, VenteUpdateComponent, VenteDeleteDialogComponent, VenteFilter],
  entryComponents: [VenteDeleteDialogComponent]
})
export class ImproVenteModule {}
