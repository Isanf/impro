import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PosePlaque } from 'app/shared/model/pose-plaque.model';
import { PosePlaqueService } from './pose-plaque.service';
import { PosePlaqueComponent } from './pose-plaque.component';
import { PosePlaqueDetailComponent } from './pose-plaque-detail.component';
import { PosePlaqueUpdateComponent } from './pose-plaque-update.component';
import { IPosePlaque } from 'app/shared/model/pose-plaque.model';

@Injectable({ providedIn: 'root' })
export class PosePlaqueResolve implements Resolve<IPosePlaque> {
  constructor(private service: PosePlaqueService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPosePlaque> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((posePlaque: HttpResponse<PosePlaque>) => posePlaque.body));
    }
    return of(new PosePlaque());
  }
}

export const posePlaqueRoute: Routes = [
  {
    path: '',
    component: PosePlaqueComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.posePlaque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PosePlaqueDetailComponent,
    resolve: {
      posePlaque: PosePlaqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.posePlaque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PosePlaqueUpdateComponent,
    resolve: {
      posePlaque: PosePlaqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.posePlaque.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PosePlaqueUpdateComponent,
    resolve: {
      posePlaque: PosePlaqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.posePlaque.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
