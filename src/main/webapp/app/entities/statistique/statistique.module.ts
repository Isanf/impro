import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { StatistiqueComponent } from './statistique.component';
import { StatistiqueDetailComponent } from './statistique-detail.component';
import { StatistiqueUpdateComponent } from './statistique-update.component';
import { StatistiqueDeleteDialogComponent } from './statistique-delete-dialog.component';
import { statistiqueRoute } from './statistique.route';
import { NbButtonModule, NbCardModule, NbSelectModule } from '@nebular/theme';
import { ChartModule } from 'primeng/chart';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(statistiqueRoute),
    NbCardModule,
    NbButtonModule,
    ChartModule,
    ButtonModule,
    NbSelectModule
  ],
  declarations: [StatistiqueComponent, StatistiqueDetailComponent, StatistiqueUpdateComponent, StatistiqueDeleteDialogComponent],
  entryComponents: [StatistiqueDeleteDialogComponent]
})
export class ImproStatistiqueModule {}
