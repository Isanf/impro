import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVente, Vente } from 'app/shared/model/vente.model';
import { VenteService } from './vente.service';
import { VenteComponent } from './vente.component';
import { VenteDetailComponent } from './vente-detail.component';
import { VenteUpdateComponent } from './vente-update.component';

@Injectable({ providedIn: 'root' })
export class VenteResolve implements Resolve<IVente> {
  constructor(private service: VenteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vente: HttpResponse<Vente>) => {
          if (vente.body) {
            return of(vente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vente());
  }
}

export const venteRoute: Routes = [
  {
    path: '',
    component: VenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.vente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VenteDetailComponent,
    resolve: {
      vente: VenteResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.vente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VenteUpdateComponent,
    resolve: {
      vente: VenteResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.vente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VenteUpdateComponent,
    resolve: {
      vente: VenteResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.vente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
