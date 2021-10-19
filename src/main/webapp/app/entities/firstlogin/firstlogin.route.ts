import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFirstlogin, Firstlogin } from 'app/shared/model/firstlogin.model';
import { FirstloginService } from './firstlogin.service';
import { FirstloginComponent } from './firstlogin.component';
import { FirstloginDetailComponent } from './firstlogin-detail.component';
import { FirstloginUpdateComponent } from './firstlogin-update.component';

@Injectable({ providedIn: 'root' })
export class FirstloginResolve implements Resolve<IFirstlogin> {
  constructor(private service: FirstloginService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFirstlogin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((firstlogin: HttpResponse<Firstlogin>) => {
          if (firstlogin.body) {
            return of(firstlogin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Firstlogin());
  }
}

export const firstloginRoute: Routes = [
  {
    path: '',
    component: FirstloginComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.firstlogin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FirstloginDetailComponent,
    resolve: {
      firstlogin: FirstloginResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.firstlogin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FirstloginUpdateComponent,
    resolve: {
      firstlogin: FirstloginResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.firstlogin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FirstloginUpdateComponent,
    resolve: {
      firstlogin: FirstloginResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.firstlogin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
