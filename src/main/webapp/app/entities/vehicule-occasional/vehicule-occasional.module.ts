import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { VehiculeOccasionalComponent } from './vehicule-occasional.component';
import { VehiculeOccasionalDetailComponent } from './vehicule-occasional-detail.component';
import { VehiculeOccasionalUpdateComponent } from './vehicule-occasional-update.component';
import { VehiculeOccasionalDeleteDialogComponent } from './vehicule-occasional-delete-dialog.component';
import { vehiculeOccasionalRoute } from './vehicule-occasional.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(vehiculeOccasionalRoute)],
  declarations: [
    VehiculeOccasionalComponent,
    VehiculeOccasionalDetailComponent,
    VehiculeOccasionalUpdateComponent,
    VehiculeOccasionalDeleteDialogComponent
  ],
  entryComponents: [VehiculeOccasionalDeleteDialogComponent]
})
export class ImproVehiculeOccasionalModule {}
