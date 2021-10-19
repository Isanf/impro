import { Route } from '@angular/router';

import { ReportComponent } from 'app/report/report.component';

export const REPORT_ROUTE: Route = {
  path: 'certificat/pdf',
  component: ReportComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
};
