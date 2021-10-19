import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICarnetASouche, CarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { CarnetASoucheService } from './carnet-a-souche.service';
import { CarnetASoucheComponent } from './carnet-a-souche.component';
import { CarnetASoucheDetailComponent } from './carnet-a-souche-detail.component';
import { CarnetASoucheUpdateComponent } from './carnet-a-souche-update.component';

@Injectable({ providedIn: 'root' })
export class CarnetASoucheResolve implements Resolve<ICarnetASouche> {
  constructor(private service: CarnetASoucheService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarnetASouche> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((carnetASouche: HttpResponse<CarnetASouche>) => {
          if (carnetASouche.body) {
            return of(carnetASouche.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CarnetASouche());
  }
}

export const carnetASoucheRoute: Routes = [
  {
    path: '',
    component: CarnetASoucheComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DG_DGTTM'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.carnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarnetASoucheDetailComponent,
    resolve: {
      carnetASouche: CarnetASoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.carnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarnetASoucheUpdateComponent,
    resolve: {
      carnetASouche: CarnetASoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.carnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarnetASoucheUpdateComponent,
    resolve: {
      carnetASouche: CarnetASoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.carnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
