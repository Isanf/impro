import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILivraisonCarnetSouche, LivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LivraisonCarnetSoucheService } from './livraison-carnet-souche.service';
import { LivraisonCarnetSoucheComponent } from './livraison-carnet-souche.component';
import { LivraisonCarnetSoucheDetailComponent } from './livraison-carnet-souche-detail.component';
import { LivraisonCarnetSoucheUpdateComponent } from './livraison-carnet-souche-update.component';

@Injectable({ providedIn: 'root' })
export class LivraisonCarnetSoucheResolve implements Resolve<ILivraisonCarnetSouche> {
  constructor(private service: LivraisonCarnetSoucheService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILivraisonCarnetSouche> | Observable<never> {
    const id = route.params['id'];
    const com = route.params['com'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((livraisonCarnetSouche: HttpResponse<LivraisonCarnetSouche>) => {
          if (livraisonCarnetSouche.body) {
            return of(livraisonCarnetSouche.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LivraisonCarnetSouche());
  }
}

export const livraisonCarnetSoucheRoute: Routes = [
  {
    path: '',
    component: LivraisonCarnetSoucheComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DGTTM', 'DG_DGTTM'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.livraisonCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LivraisonCarnetSoucheDetailComponent,
    resolve: {
      livraisonCarnetSouche: LivraisonCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DGTTM'],
      pageTitle: 'improApp.livraisonCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LivraisonCarnetSoucheUpdateComponent,
    resolve: {
      livraisonCarnetSouche: LivraisonCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DGTTM'],
      pageTitle: 'improApp.livraisonCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LivraisonCarnetSoucheUpdateComponent,
    resolve: {
      livraisonCarnetSouche: LivraisonCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DGTTM'],
      pageTitle: 'improApp.livraisonCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':idd/livrer',
    component: LivraisonCarnetSoucheUpdateComponent,
    resolve: {
      livraisonCarnetSouche: LivraisonCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DGTTM'],
      pageTitle: 'improApp.livraisonCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'livre/edit',
    component: LivraisonCarnetSoucheUpdateComponent,
    resolve: {
      livraisonCarnetSouche: LivraisonCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET', 'DGTTM'],
      pageTitle: 'improApp.livraisonCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
