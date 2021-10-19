import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CarnetASoucheComponent } from './carnet-a-souche.component';
import { CarnetASoucheDetailComponent } from './carnet-a-souche-detail.component';
import { CarnetASoucheUpdateComponent } from './carnet-a-souche-update.component';
import { CarnetASoucheDeleteDialogComponent } from './carnet-a-souche-delete-dialog.component';
import { carnetASoucheRoute } from './carnet-a-souche.route';
import {
  NbButtonModule,
  NbCardModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbSpinnerModule
} from '@nebular/theme';
import { CarnetFilter } from 'app/entities/carnet-a-souche/carnetFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(carnetASoucheRoute),
    NbCardModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbButtonModule,
    NbSelectModule,
    NbSpinnerModule
  ],
  declarations: [
    CarnetASoucheComponent,
    CarnetASoucheDetailComponent,
    CarnetASoucheUpdateComponent,
    CarnetASoucheDeleteDialogComponent,
    CarnetFilter
  ],
  entryComponents: [CarnetASoucheDeleteDialogComponent]
})
export class ImproCarnetASoucheModule {}
