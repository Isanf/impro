import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { PlaqueGarageComponent } from './plaque-garage.component';
import { PlaqueGarageDetailComponent } from './plaque-garage-detail.component';
import { PlaqueGarageUpdateComponent } from './plaque-garage-update.component';
import { PlaqueGarageDeleteDialogComponent } from './plaque-garage-delete-dialog.component';
import { plaqueGarageRoute } from './plaque-garage.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule } from '@nebular/theme';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(plaqueGarageRoute),
    NbFormFieldModule,
    NbInputModule,
    NbButtonModule,
    NbIconModule,
    NbCardModule
  ],
  declarations: [PlaqueGarageComponent, PlaqueGarageDetailComponent, PlaqueGarageUpdateComponent, PlaqueGarageDeleteDialogComponent],
  entryComponents: [PlaqueGarageDeleteDialogComponent]
})
export class ImproPlaqueGarageModule {}
