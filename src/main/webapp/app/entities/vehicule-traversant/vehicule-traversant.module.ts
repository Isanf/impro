import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { VehiculeTraversantComponent } from './vehicule-traversant.component';
import { VehiculeTraversantDetailComponent } from './vehicule-traversant-detail.component';
import { VehiculeTraversantUpdateComponent } from './vehicule-traversant-update.component';
import { VehiculeTraversantDeleteDialogComponent } from './vehicule-traversant-delete-dialog.component';
import { vehiculeTraversantRoute } from './vehicule-traversant.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(vehiculeTraversantRoute)],
  declarations: [
    VehiculeTraversantComponent,
    VehiculeTraversantDetailComponent,
    VehiculeTraversantUpdateComponent,
    VehiculeTraversantDeleteDialogComponent
  ],
  entryComponents: [VehiculeTraversantDeleteDialogComponent]
})
export class ImproVehiculeTraversantModule {}
