import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { PrixCertificatComponent } from './prix-certificat.component';
import { PrixCertificatDetailComponent } from './prix-certificat-detail.component';
import { PrixCertificatUpdateComponent } from './prix-certificat-update.component';
import { PrixCertificatDeleteDialogComponent } from './prix-certificat-delete-dialog.component';
import { prixCertificatRoute } from './prix-certificat.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(prixCertificatRoute)],
  declarations: [
    PrixCertificatComponent,
    PrixCertificatDetailComponent,
    PrixCertificatUpdateComponent,
    PrixCertificatDeleteDialogComponent
  ],
  entryComponents: [PrixCertificatDeleteDialogComponent]
})
export class ImproPrixCertificatModule {}
