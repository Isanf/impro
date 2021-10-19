import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { TypeActeurComponent } from './type-acteur.component';
import { TypeActeurDetailComponent } from './type-acteur-detail.component';
import { TypeActeurUpdateComponent } from './type-acteur-update.component';
import { TypeActeurDeleteDialogComponent } from './type-acteur-delete-dialog.component';
import { typeActeurRoute } from './type-acteur.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule, NbSelectModule } from '@nebular/theme';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ActeurFilter } from 'app/entities/type-acteur/acteurFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(typeActeurRoute),
    NgbModalModule,
    NbInputModule,
    NbSelectModule,
    NbCardModule,
    NbButtonModule,
    NbFormFieldModule,
    NbIconModule
  ],
  declarations: [TypeActeurComponent, TypeActeurDetailComponent, TypeActeurUpdateComponent, TypeActeurDeleteDialogComponent, ActeurFilter],
  entryComponents: [TypeActeurDeleteDialogComponent, TypeActeurUpdateComponent]
})
export class ImproTypeActeurModule {}
