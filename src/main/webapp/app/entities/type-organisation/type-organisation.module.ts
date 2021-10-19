import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { TypeOrganisationComponent } from './type-organisation.component';
import { TypeOrganisationDetailComponent } from './type-organisation-detail.component';
import { TypeOrganisationUpdateComponent } from './type-organisation-update.component';
import { TypeOrganisationDeleteDialogComponent } from './type-organisation-delete-dialog.component';
import { typeOrganisationRoute } from './type-organisation.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule, NbSelectModule } from '@nebular/theme';
import { TypeCategoFilter } from 'app/entities/type-organisation/typeCategoFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(typeOrganisationRoute),
    NbCardModule,
    NbButtonModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbSelectModule
  ],
  declarations: [
    TypeOrganisationComponent,
    TypeOrganisationDetailComponent,
    TypeOrganisationUpdateComponent,
    TypeOrganisationDeleteDialogComponent,
    TypeCategoFilter
  ],
  entryComponents: [TypeOrganisationDeleteDialogComponent]
})
export class ImproTypeOrganisationModule {}
