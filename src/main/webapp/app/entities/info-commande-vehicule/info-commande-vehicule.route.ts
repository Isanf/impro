import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInfoCommandeVehicule, InfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';
import { InfoCommandeVehiculeService } from './info-commande-vehicule.service';
import { InfoCommandeVehiculeComponent } from './info-commande-vehicule.component';
import { InfoCommandeVehiculeDetailComponent } from './info-commande-vehicule-detail.component';
import { InfoCommandeVehiculeUpdateComponent } from './info-commande-vehicule-update.component';

@Injectable({ providedIn: 'root' })
export class InfoCommandeVehiculeResolve implements Resolve<IInfoCommandeVehicule> {
  constructor(private service: InfoCommandeVehiculeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInfoCommandeVehicule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((infoCommandeVehicule: HttpResponse<InfoCommandeVehicule>) => {
          if (infoCommandeVehicule.body) {
            return of(infoCommandeVehicule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InfoCommandeVehicule());
  }
}

export const infoCommandeVehiculeRoute: Routes = [
  {
    path: '',
    component: InfoCommandeVehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.infoCommandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InfoCommandeVehiculeDetailComponent,
    resolve: {
      infoCommandeVehicule: InfoCommandeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.infoCommandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InfoCommandeVehiculeUpdateComponent,
    resolve: {
      infoCommandeVehicule: InfoCommandeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.infoCommandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InfoCommandeVehiculeUpdateComponent,
    resolve: {
      infoCommandeVehicule: InfoCommandeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.infoCommandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
