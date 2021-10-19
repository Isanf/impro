import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMarqueVehicule, MarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { MarqueVehiculeService } from './marque-vehicule.service';
import { MarqueVehiculeComponent } from './marque-vehicule.component';
import { MarqueVehiculeDetailComponent } from './marque-vehicule-detail.component';
import { MarqueVehiculeUpdateComponent } from './marque-vehicule-update.component';

@Injectable({ providedIn: 'root' })
export class MarqueVehiculeResolve implements Resolve<IMarqueVehicule> {
  constructor(private service: MarqueVehiculeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMarqueVehicule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((marqueVehicule: HttpResponse<MarqueVehicule>) => {
          if (marqueVehicule.body) {
            return of(marqueVehicule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MarqueVehicule());
  }
}

export const marqueVehiculeRoute: Routes = [
  {
    path: '',
    component: MarqueVehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.marqueVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MarqueVehiculeDetailComponent,
    resolve: {
      marqueVehicule: MarqueVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.marqueVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MarqueVehiculeUpdateComponent,
    resolve: {
      marqueVehicule: MarqueVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.marqueVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MarqueVehiculeUpdateComponent,
    resolve: {
      marqueVehicule: MarqueVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.marqueVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
