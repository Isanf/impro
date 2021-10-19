import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { LivraisonCarnetSoucheComponent } from './livraison-carnet-souche.component';
import { LivraisonCarnetSoucheDetailComponent } from './livraison-carnet-souche-detail.component';
import { LivraisonCarnetSoucheUpdateComponent } from './livraison-carnet-souche-update.component';
import { LivraisonCarnetSoucheDeleteDialogComponent } from './livraison-carnet-souche-delete-dialog.component';
import { livraisonCarnetSoucheRoute } from './livraison-carnet-souche.route';
import {
  NbButtonModule,
  NbCardModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbSelectModule,
  NbStepperModule
} from '@nebular/theme';
import { LivraisonCSFilter } from 'app/entities/livraison-carnet-souche/livraisonCSFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(livraisonCarnetSoucheRoute),
    NbInputModule,
    NbSelectModule,
    NbCardModule,
    NbStepperModule,
    NbButtonModule,
    NbFormFieldModule,
    NbIconModule
  ],
  declarations: [
    LivraisonCarnetSoucheComponent,
    LivraisonCarnetSoucheDetailComponent,
    LivraisonCarnetSoucheUpdateComponent,
    LivraisonCarnetSoucheDeleteDialogComponent,
    LivraisonCSFilter
  ],
  entryComponents: [LivraisonCarnetSoucheDeleteDialogComponent, LivraisonCarnetSoucheUpdateComponent]
})
export class ImproLivraisonCarnetSoucheModule {}
