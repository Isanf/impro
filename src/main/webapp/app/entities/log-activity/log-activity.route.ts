import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILogActivity, LogActivity } from 'app/shared/model/log-activity.model';
import { LogActivityService } from './log-activity.service';
import { LogActivityComponent } from './log-activity.component';
import { LogActivityDetailComponent } from './log-activity-detail.component';
import { LogActivityUpdateComponent } from './log-activity-update.component';

@Injectable({ providedIn: 'root' })
export class LogActivityResolve implements Resolve<ILogActivity> {
  constructor(private service: LogActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((logActivity: HttpResponse<LogActivity>) => {
          if (logActivity.body) {
            return of(logActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogActivity());
  }
}

export const logActivityRoute: Routes = [
  {
    path: '',
    component: LogActivityComponent,
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.logActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LogActivityDetailComponent,
    resolve: {
      logActivity: LogActivityResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.logActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LogActivityUpdateComponent,
    resolve: {
      logActivity: LogActivityResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.logActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LogActivityUpdateComponent,
    resolve: {
      logActivity: LogActivityResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.logActivity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
