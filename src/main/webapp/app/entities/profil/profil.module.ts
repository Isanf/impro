import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { ProfilComponent } from './profil.component';
import { ProfilDetailComponent } from './profil-detail.component';
import { ProfilUpdateComponent } from './profil-update.component';
import { ProfilDeleteDialogComponent } from './profil-delete-dialog.component';
import { profilRoute } from './profil.route';
import { NbButtonModule, NbCardModule, NbTabsetModule } from '@nebular/theme';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(profilRoute), NbCardModule, NbTabsetModule, NbButtonModule],
  declarations: [ProfilComponent, ProfilDetailComponent, ProfilUpdateComponent, ProfilDeleteDialogComponent],
  entryComponents: [ProfilDeleteDialogComponent]
})
export class ImproProfilModule {}
