import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { VehiculeOccasionComponent } from './vehicule-occasion.component';
import { VehiculeOccasionDetailComponent } from './vehicule-occasion-detail.component';
import { VehiculeOccasionUpdateComponent } from './vehicule-occasion-update.component';
import { VehiculeOccasionDeleteDialogComponent } from './vehicule-occasion-delete-dialog.component';
import { vehiculeOccasionRoute } from './vehicule-occasion.route';
import {
  NbAutocompleteModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbOptionModule,
  NbSelectModule,
  NbStepperModule,
  NbTabsetModule
} from '@nebular/theme';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(vehiculeOccasionRoute),
    NbCardModule,
    NbButtonModule,
    NbInputModule,
    NbOptionModule,
    NbSelectModule,
    NbCheckboxModule,
    NbTabsetModule,
    NbStepperModule,
    NbIconModule,
    NbFormFieldModule,
    NbAutocompleteModule
  ],
  declarations: [
    VehiculeOccasionComponent,
    VehiculeOccasionDetailComponent,
    VehiculeOccasionUpdateComponent,
    VehiculeOccasionDeleteDialogComponent
  ],
  entryComponents: [VehiculeOccasionDeleteDialogComponent]
})
export class ImproVehiculeOccasionModule {}
