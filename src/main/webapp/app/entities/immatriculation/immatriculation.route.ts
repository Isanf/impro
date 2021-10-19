import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IImmatriculation, Immatriculation } from 'app/shared/model/immatriculation.model';
import { ImmatriculationService } from './immatriculation.service';
import { ImmatriculationComponent } from './immatriculation.component';
import { ImmatriculationDetailComponent } from './immatriculation-detail.component';
import { ImmatriculationUpdateComponent } from './immatriculation-update.component';

@Injectable({ providedIn: 'root' })
export class ImmatriculationResolve implements Resolve<IImmatriculation> {
  constructor(private service: ImmatriculationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImmatriculation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((immatriculation: HttpResponse<Immatriculation>) => {
          if (immatriculation.body) {
            return of(immatriculation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Immatriculation());
  }
}

export const immatriculationRoute: Routes = [
  {
    path: '',
    component: ImmatriculationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.immatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImmatriculationDetailComponent,
    resolve: {
      immatriculation: ImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.immatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImmatriculationUpdateComponent,
    resolve: {
      immatriculation: ImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.immatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImmatriculationUpdateComponent,
    resolve: {
      immatriculation: ImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.immatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
