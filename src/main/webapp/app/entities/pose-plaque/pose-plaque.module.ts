import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { PosePlaqueComponent } from './pose-plaque.component';
import { PosePlaqueDetailComponent } from './pose-plaque-detail.component';
import { PosePlaqueUpdateComponent } from './pose-plaque-update.component';
import { PosePlaqueDeleteDialogComponent } from './pose-plaque-delete-dialog.component';
import { posePlaqueRoute } from './pose-plaque.route';
import { NbButtonModule } from '@nebular/theme';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(posePlaqueRoute), NbButtonModule],
  declarations: [PosePlaqueComponent, PosePlaqueDetailComponent, PosePlaqueUpdateComponent, PosePlaqueDeleteDialogComponent],
  entryComponents: [PosePlaqueDeleteDialogComponent]
})
export class ImproPosePlaqueModule {}
