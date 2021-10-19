import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { PasswordComponent } from './password.component';

export const passwordRoute: Route = {
  path: 'password',
  component: PasswordComponent,
  data: {
    authorities: ['ROLE_ADMIN', 'DGTTM', 'CONCESSIONNAIRE', 'STHGUICHET', 'REVENDEUR', 'DOUANE', 'DG_DGTTM', 'DG_DOUANE'],
    pageTitle: 'global.menu.account.password'
  },
  canActivate: [UserRouteAccessService]
};
