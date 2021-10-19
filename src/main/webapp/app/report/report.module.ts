import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { REPORT_ROUTE } from 'app/report/report.route';
import { ReportComponent } from 'app/report/report.component';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild([REPORT_ROUTE])],
  declarations: [ReportComponent]
})
export class ImproReportModule {}
