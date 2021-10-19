import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY, Subscription } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICarteW, CarteW } from 'app/shared/model/carte-w.model';
import { CarteWService } from './carte-w.service';
import { CarteWComponent } from './carte-w.component';
import { CarteWDetailComponent } from './carte-w-detail.component';
import { CarteWUpdateComponent } from './carte-w-update.component';
import { NbDialogService } from '@nebular/theme';

@Injectable({ providedIn: 'root' })
export class CarteWResolve implements Resolve<ICarteW> {
  constructor(private service: CarteWService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarteW> | Observable<never> | Observable<any> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((carteW: HttpResponse<CarteW>) => {
          if (carteW.body) {
            return of(carteW.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CarteW());
  }
}

export const carteWRoute: Routes = [
  {
    path: '',
    component: CarteWComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.carteW.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarteWDetailComponent,
    resolve: {
      carteW: CarteWResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.carteW.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarteWUpdateComponent,
    resolve: {
      carteW: CarteWResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.carteW.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarteWUpdateComponent,
    resolve: {
      carteW: CarteWResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.carteW.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
