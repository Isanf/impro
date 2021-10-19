import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInfoCommandeCarnetASouche, InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { InfoCommandeCarnetASoucheService } from './info-commande-carnet-a-souche.service';
import { InfoCommandeCarnetASoucheComponent } from './info-commande-carnet-a-souche.component';
import { InfoCommandeCarnetASoucheDetailComponent } from './info-commande-carnet-a-souche-detail.component';
import { InfoCommandeCarnetASoucheUpdateComponent } from './info-commande-carnet-a-souche-update.component';

@Injectable({ providedIn: 'root' })
export class InfoCommandeCarnetASoucheResolve implements Resolve<IInfoCommandeCarnetASouche> {
  constructor(private service: InfoCommandeCarnetASoucheService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInfoCommandeCarnetASouche> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((infoCommandeCarnetASouche: HttpResponse<InfoCommandeCarnetASouche>) => {
          if (infoCommandeCarnetASouche.body) {
            return of(infoCommandeCarnetASouche.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InfoCommandeCarnetASouche());
  }
}

export const infoCommandeCarnetASoucheRoute: Routes = [
  {
    path: '',
    component: InfoCommandeCarnetASoucheComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.infoCommandeCarnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InfoCommandeCarnetASoucheDetailComponent,
    resolve: {
      infoCommandeCarnetASouche: InfoCommandeCarnetASoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.infoCommandeCarnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InfoCommandeCarnetASoucheUpdateComponent,
    resolve: {
      infoCommandeCarnetASouche: InfoCommandeCarnetASoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.infoCommandeCarnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InfoCommandeCarnetASoucheUpdateComponent,
    resolve: {
      infoCommandeCarnetASouche: InfoCommandeCarnetASoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.infoCommandeCarnetASouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
