import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { ImmatriculationComponent } from './immatriculation.component';
import { ImmatriculationDetailComponent } from './immatriculation-detail.component';
import { ImmatriculationUpdateComponent } from './immatriculation-update.component';
import { ImmatriculationDeleteDialogComponent } from './immatriculation-delete-dialog.component';
import { immatriculationRoute } from './immatriculation.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule } from '@nebular/theme';
import { ImmatriculationFilter } from 'app/entities/immatriculation/immatriculationFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(immatriculationRoute),
    NbCardModule,
    NbButtonModule,
    NbFormFieldModule,
    NbInputModule,
    NbIconModule
  ],
  declarations: [
    ImmatriculationComponent,
    ImmatriculationDetailComponent,
    ImmatriculationUpdateComponent,
    ImmatriculationDeleteDialogComponent,
    ImmatriculationFilter
  ],
  entryComponents: [ImmatriculationDeleteDialogComponent]
})
export class ImproImmatriculationModule {}
