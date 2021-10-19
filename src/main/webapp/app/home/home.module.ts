import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { NbAccordionModule, NbButtonModule, NbCardModule, NbLayoutModule, NbSelectModule, NbSidebarModule } from '@nebular/theme';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    ImproSharedModule,
    BrowserAnimationsModule,
    RouterModule.forChild([HOME_ROUTE]),
    NbLayoutModule,
    NbSidebarModule,
    NbCardModule,
    NbSelectModule,
    NbButtonModule,
    NbAccordionModule
    //NgCircleProgressModule
  ],
  declarations: [HomeComponent]
})
export class ImproHomeModule {}
