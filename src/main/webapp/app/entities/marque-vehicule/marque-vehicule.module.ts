import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { MarqueVehiculeComponent } from './marque-vehicule.component';
import { MarqueVehiculeDetailComponent } from './marque-vehicule-detail.component';
import { MarqueVehiculeUpdateComponent } from './marque-vehicule-update.component';
import { MarqueVehiculeDeleteDialogComponent } from './marque-vehicule-delete-dialog.component';
import { marqueVehiculeRoute } from './marque-vehicule.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule } from '@nebular/theme';
import { MarqueVehicule } from 'app/entities/marque-vehicule/marqueVehicule';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(marqueVehiculeRoute),
    NbCardModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbButtonModule
  ],
  declarations: [
    MarqueVehiculeComponent,
    MarqueVehiculeDetailComponent,
    MarqueVehiculeUpdateComponent,
    MarqueVehiculeDeleteDialogComponent,
    MarqueVehicule
  ],
  entryComponents: [MarqueVehiculeDeleteDialogComponent]
})
export class ImproMarqueVehiculeModule {}
