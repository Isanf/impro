import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ImproSharedModule } from 'app/shared/shared.module';
import { ImproCoreModule } from 'app/core/core.module';
import { ImproAppRoutingModule } from './app-routing.module';
import { ImproHomeModule } from './home/home.module';
import { ImproEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbIconModule,
  NbLayoutModule,
  NbMenuModule,
  NbSearchModule,
  NbSelectModule,
  NbSidebarModule,
  NbThemeModule,
  NbToastrModule,
  NbUserModule,
  NbSpinnerModule,
  NbDatepickerModule
} from '@nebular/theme';
import { PagesModule } from 'app/layouts/sidebar/pages.module';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { ImproReportModule } from 'app/report/report.module';
import { ImproLivraisonCarnetSoucheModule } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    BrowserModule,
    ImproSharedModule,
    ImproCoreModule,
    ImproHomeModule,
    BrowserAnimationsModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ImproEntityModule,
    ImproAppRoutingModule,
    NbThemeModule.forRoot(),
    NbSidebarModule.forRoot(),
    NbLayoutModule,
    NbMenuModule.forRoot(),
    NbButtonModule,
    NbIconModule,
    NbActionsModule,
    NbSearchModule,
    NbUserModule,
    NbEvaIconsModule,
    PagesModule,
    ImproReportModule,
    NbSelectModule,
    NbToastrModule.forRoot(),
    NbCardModule,
    ImproLivraisonCarnetSoucheModule,
    NgMultiSelectDropDownModule.forRoot(),
    NbSpinnerModule,
    NbDatepickerModule.forRoot(),
    HttpClientModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  exports: [NavbarComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class ImproAppModule {}
