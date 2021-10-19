import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { VehiculeComponent } from './vehicule.component';
import { VehiculeDetailComponent } from './vehicule-detail.component';
import { VehiculeUpdateComponent } from './vehicule-update.component';
import { VehiculeDeleteDialogComponent } from './vehicule-delete-dialog.component';
import { vehiculeRoute } from './vehicule.route';
import {
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule
} from '@nebular/theme';
import { VehiculeFilter } from 'app/entities/vehicule/vehiculeFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(vehiculeRoute),
    NbSelectModule,
    NbCardModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbButtonModule,
    NbCheckboxModule
  ],
  declarations: [VehiculeComponent, VehiculeDetailComponent, VehiculeUpdateComponent, VehiculeDeleteDialogComponent, VehiculeFilter],
  entryComponents: [VehiculeDeleteDialogComponent]
})
export class ImproVehiculeModule {}
