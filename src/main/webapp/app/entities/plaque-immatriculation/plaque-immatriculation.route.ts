import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlaqueImmatriculation, PlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { PlaqueImmatriculationService } from './plaque-immatriculation.service';
import { PlaqueImmatriculationComponent } from './plaque-immatriculation.component';
import { PlaqueImmatriculationDetailComponent } from './plaque-immatriculation-detail.component';
import { PlaqueImmatriculationUpdateComponent } from './plaque-immatriculation-update.component';

@Injectable({ providedIn: 'root' })
export class PlaqueImmatriculationResolve implements Resolve<IPlaqueImmatriculation> {
  constructor(private service: PlaqueImmatriculationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlaqueImmatriculation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plaqueImmatriculation: HttpResponse<PlaqueImmatriculation>) => {
          if (plaqueImmatriculation.body) {
            return of(plaqueImmatriculation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlaqueImmatriculation());
  }
}

export const plaqueImmatriculationRoute: Routes = [
  {
    path: '',
    component: PlaqueImmatriculationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.plaqueImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlaqueImmatriculationDetailComponent,
    resolve: {
      plaqueImmatriculation: PlaqueImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.plaqueImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlaqueImmatriculationUpdateComponent,
    resolve: {
      plaqueImmatriculation: PlaqueImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.plaqueImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlaqueImmatriculationUpdateComponent,
    resolve: {
      plaqueImmatriculation: PlaqueImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.plaqueImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
