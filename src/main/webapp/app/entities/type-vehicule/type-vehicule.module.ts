import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { TypeVehiculeComponent } from './type-vehicule.component';
import { TypeVehiculeDetailComponent } from './type-vehicule-detail.component';
import { TypeVehiculeUpdateComponent } from './type-vehicule-update.component';
import { TypeVehiculeDeleteDialogComponent } from './type-vehicule-delete-dialog.component';
import { typeVehiculeRoute } from './type-vehicule.route';
import { TyVehiculeFilter } from 'app/entities/type-vehicule/tyVehiculeFilter';
import { NbButtonModule, NbCardModule, NbCheckboxModule, NbFormFieldModule, NbIconModule, NbInputModule } from '@nebular/theme';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(typeVehiculeRoute),
    NbCardModule,
    NbFormFieldModule,
    NbIconModule,
    NbButtonModule,
    NbInputModule,
    NbCheckboxModule
  ],
  declarations: [
    TypeVehiculeComponent,
    TypeVehiculeDetailComponent,
    TypeVehiculeUpdateComponent,
    TypeVehiculeDeleteDialogComponent,
    TyVehiculeFilter
  ],
  entryComponents: [TypeVehiculeDeleteDialogComponent]
})
export class ImproTypeVehiculeModule {}
