import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CertificatImmatriculationComponent } from './certificat-immatriculation.component';
import { CertificatImmatriculationDetailComponent } from './certificat-immatriculation-detail.component';
import { CertificatImmatriculationUpdateComponent } from './certificat-immatriculation-update.component';
import { CertificatImmatriculationDeleteDialogComponent } from './certificat-immatriculation-delete-dialog.component';
import { certificatImmatriculationRoute } from './certificat-immatriculation.route';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(certificatImmatriculationRoute)],
  declarations: [
    CertificatImmatriculationComponent,
    CertificatImmatriculationDetailComponent,
    CertificatImmatriculationUpdateComponent,
    CertificatImmatriculationDeleteDialogComponent
  ],
  entryComponents: [CertificatImmatriculationDeleteDialogComponent]
})
export class ImproCertificatImmatriculationModule {}
