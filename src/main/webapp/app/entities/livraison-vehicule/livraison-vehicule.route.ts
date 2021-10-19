import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivraisonVehicule, LivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';
import { LivraisonVehiculeService } from './livraison-vehicule.service';
import { LivraisonVehiculeComponent } from './livraison-vehicule.component';
import { LivraisonVehiculeDetailComponent } from './livraison-vehicule-detail.component';
import { LivraisonVehiculeUpdateComponent } from './livraison-vehicule-update.component';

@Injectable({ providedIn: 'root' })
export class LivraisonVehiculeResolve implements Resolve<ILivraisonVehicule> {
  constructor(private service: LivraisonVehiculeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivraisonVehicule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livraisonVehicule: HttpResponse<LivraisonVehicule>) => {
          if (livraisonVehicule.body) {
            return of(livraisonVehicule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LivraisonVehicule());
  }
}

export const livraisonVehiculeRoute: Routes = [
  {
    path: '',
    component: LivraisonVehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.livraisonVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LivraisonVehiculeDetailComponent,
    resolve: {
      livraisonVehicule: LivraisonVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      pageTitle: 'improApp.livraisonVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LivraisonVehiculeUpdateComponent,
    resolve: {
      livraisonVehicule: LivraisonVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.livraisonVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LivraisonVehiculeUpdateComponent,
    resolve: {
      livraisonVehicule: LivraisonVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.livraisonVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
