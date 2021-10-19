import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CarteWComponent } from './carte-w.component';
import { CarteWDetailComponent } from './carte-w-detail.component';
import { CarteWUpdateComponent } from './carte-w-update.component';
import { CarteWDeleteDialogComponent } from './carte-w-delete-dialog.component';
import { carteWRoute } from './carte-w.route';
import {
  NbButtonModule,
  NbCardModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbSpinnerModule,
  NbStepperModule
} from '@nebular/theme';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(carteWRoute),
    NbCardModule,
    NbStepperModule,
    NbInputModule,
    NbIconModule,
    NbFormFieldModule,
    NbSelectModule,
    NbButtonModule,
    NbSpinnerModule
  ],
  declarations: [CarteWComponent, CarteWDetailComponent, CarteWUpdateComponent, CarteWDeleteDialogComponent],
  entryComponents: [CarteWDeleteDialogComponent]
})
export class ImproCarteWModule {}
