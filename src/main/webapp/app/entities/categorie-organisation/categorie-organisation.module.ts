import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CategorieOrganisationComponent } from './categorie-organisation.component';
import { CategorieOrganisationDetailComponent } from './categorie-organisation-detail.component';
import { CategorieOrganisationUpdateComponent } from './categorie-organisation-update.component';
import { CategorieOrganisationDeleteDialogComponent } from './categorie-organisation-delete-dialog.component';
import { categorieOrganisationRoute } from './categorie-organisation.route';
import { NbButtonModule, NbCardModule, NbFormFieldModule, NbIconModule, NbInputModule, NbSelectModule } from '@nebular/theme';
import { CategorieFilter } from 'app/entities/categorie-organisation/categorieFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(categorieOrganisationRoute),
    NbCardModule,
    NbButtonModule,
    NbFormFieldModule,
    NbIconModule,
    NbInputModule,
    NbSelectModule
  ],
  declarations: [
    CategorieOrganisationComponent,
    CategorieOrganisationDetailComponent,
    CategorieOrganisationUpdateComponent,
    CategorieOrganisationDeleteDialogComponent,
    CategorieFilter
  ],
  entryComponents: [CategorieOrganisationDeleteDialogComponent]
})
export class ImproCategorieOrganisationModule {}
