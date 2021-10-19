import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { PlaqueImmatriculationComponent } from './plaque-immatriculation.component';
import { PlaqueImmatriculationDetailComponent } from './plaque-immatriculation-detail.component';
import { PlaqueImmatriculationUpdateComponent } from './plaque-immatriculation-update.component';
import { PlaqueImmatriculationDeleteDialogComponent } from './plaque-immatriculation-delete-dialog.component';
import { plaqueImmatriculationRoute } from './plaque-immatriculation.route';
import {
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbStepperModule
} from '@nebular/theme';
import { PlaqueFilter } from 'app/entities/plaque-immatriculation/plaqueFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(plaqueImmatriculationRoute),
    NbCardModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbButtonModule,
    NbSelectModule,
    NbStepperModule,
    NbCheckboxModule
  ],
  declarations: [
    PlaqueImmatriculationComponent,
    PlaqueImmatriculationDetailComponent,
    PlaqueImmatriculationUpdateComponent,
    PlaqueImmatriculationDeleteDialogComponent,
    PlaqueFilter
  ],
  entryComponents: [PlaqueImmatriculationDeleteDialogComponent]
})
export class ImproPlaqueImmatriculationModule {}
