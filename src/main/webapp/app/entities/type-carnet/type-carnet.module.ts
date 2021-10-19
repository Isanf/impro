import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { TypeCarnetComponent } from './type-carnet.component';
import { TypeCarnetDetailComponent } from './type-carnet-detail.component';
import { TypeCarnetUpdateComponent } from './type-carnet-update.component';
import { TypeCarnetDeleteDialogComponent } from './type-carnet-delete-dialog.component';
import { typeCarnetRoute } from './type-carnet.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule, NbSelectModule } from '@nebular/theme';
import { TypeCarnetFilter } from 'app/entities/type-carnet/typeCarnetFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(typeCarnetRoute),
    NbCardModule,
    NbFormFieldModule,
    NbIconModule,
    NbButtonModule,
    NbInputModule,
    NbSelectModule
  ],
  declarations: [
    TypeCarnetComponent,
    TypeCarnetDetailComponent,
    TypeCarnetUpdateComponent,
    TypeCarnetDeleteDialogComponent,
    TypeCarnetFilter
  ],
  entryComponents: [TypeCarnetDeleteDialogComponent]
})
export class ImproTypeCarnetModule {}
