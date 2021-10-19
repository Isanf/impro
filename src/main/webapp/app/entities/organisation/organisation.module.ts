import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { OrganisationComponent } from './organisation.component';
import { OrganisationDetailComponent } from './organisation-detail.component';
import { OrganisationUpdateComponent } from './organisation-update.component';
import { OrganisationDeleteDialogComponent } from './organisation-delete-dialog.component';
import { organisationRoute } from './organisation.route';
import {
  NbAutocompleteModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbIconModule,
  NbInputModule,
  NbOptionModule,
  NbSelectModule,
  NbStepperModule
} from '@nebular/theme';
import { OrganisationFilter } from 'app/entities/organisation/organisationFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(organisationRoute),
    NbCardModule,
    NbIconModule,
    NbStepperModule,
    NbOptionModule,
    NbSelectModule,
    NbButtonModule,
    NbFormFieldModule,
    NbInputModule,
    NbAutocompleteModule,
    NbCheckboxModule
  ],
  declarations: [
    OrganisationComponent,
    OrganisationDetailComponent,
    OrganisationUpdateComponent,
    OrganisationDeleteDialogComponent,
    OrganisationFilter
  ],
  entryComponents: [OrganisationDeleteDialogComponent]
})
export class ImproOrganisationModule {}
