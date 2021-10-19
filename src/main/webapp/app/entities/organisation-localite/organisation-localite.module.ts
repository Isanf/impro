import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { OrganisationLocaliteComponent } from './organisation-localite.component';
import { OrganisationLocaliteDetailComponent } from './organisation-localite-detail.component';
import { OrganisationLocaliteUpdateComponent } from './organisation-localite-update.component';
import { OrganisationLocaliteDeleteDialogComponent } from './organisation-localite-delete-dialog.component';
import { organisationLocaliteRoute } from './organisation-localite.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(organisationLocaliteRoute)],
  declarations: [
    OrganisationLocaliteComponent,
    OrganisationLocaliteDetailComponent,
    OrganisationLocaliteUpdateComponent,
    OrganisationLocaliteDeleteDialogComponent
  ],
  entryComponents: [OrganisationLocaliteDeleteDialogComponent]
})
export class ImproOrganisationLocaliteModule {}
