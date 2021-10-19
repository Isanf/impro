import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeVehicule, TypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from './type-vehicule.service';
import { TypeVehiculeComponent } from './type-vehicule.component';
import { TypeVehiculeDetailComponent } from './type-vehicule-detail.component';
import { TypeVehiculeUpdateComponent } from './type-vehicule-update.component';

@Injectable({ providedIn: 'root' })
export class TypeVehiculeResolve implements Resolve<ITypeVehicule> {
  constructor(private service: TypeVehiculeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeVehicule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeVehicule: HttpResponse<TypeVehicule>) => {
          if (typeVehicule.body) {
            return of(typeVehicule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeVehicule());
  }
}

export const typeVehiculeRoute: Routes = [
  {
    path: '',
    component: TypeVehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.typeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeVehiculeDetailComponent,
    resolve: {
      typeVehicule: TypeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.typeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeVehiculeUpdateComponent,
    resolve: {
      typeVehicule: TypeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.typeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeVehiculeUpdateComponent,
    resolve: {
      typeVehicule: TypeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.typeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
