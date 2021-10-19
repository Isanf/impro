import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeCarnet, TypeCarnet } from 'app/shared/model/type-carnet.model';
import { TypeCarnetService } from './type-carnet.service';
import { TypeCarnetComponent } from './type-carnet.component';
import { TypeCarnetDetailComponent } from './type-carnet-detail.component';
import { TypeCarnetUpdateComponent } from './type-carnet-update.component';

@Injectable({ providedIn: 'root' })
export class TypeCarnetResolve implements Resolve<ITypeCarnet> {
  constructor(private service: TypeCarnetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeCarnet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeCarnet: HttpResponse<TypeCarnet>) => {
          if (typeCarnet.body) {
            return of(typeCarnet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeCarnet());
  }
}

export const typeCarnetRoute: Routes = [
  {
    path: '',
    component: TypeCarnetComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.typeCarnet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeCarnetDetailComponent,
    resolve: {
      typeCarnet: TypeCarnetResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.typeCarnet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeCarnetUpdateComponent,
    resolve: {
      typeCarnet: TypeCarnetResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.typeCarnet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeCarnetUpdateComponent,
    resolve: {
      typeCarnet: TypeCarnetResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.typeCarnet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
