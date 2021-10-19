import { NgModule } from '@angular/core';
import { NbIconModule, NbMenuModule, NbThemeModule } from '@nebular/theme';
import { PagesRoutingModule } from 'app/layouts/sidebar/pages-routing.module';
import { PagesComponent } from 'app/layouts/sidebar/pages.component';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NgJhipsterModule } from 'ng-jhipster';
import { ImproSharedModule } from 'app/shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    PagesRoutingModule,
    NbThemeModule.forRoot(),
    NbMenuModule.forRoot(),
    NbIconModule,
    NbEvaIconsModule,
    NgJhipsterModule,
    ImproSharedModule,
    BrowserAnimationsModule
  ],
  declarations: [PagesComponent],
  exports: [PagesComponent]
})
export class PagesModule {}
