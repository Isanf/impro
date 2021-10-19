import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeActeur, TypeActeur } from 'app/shared/model/type-acteur.model';
import { TypeActeurService } from './type-acteur.service';
import { TypeActeurComponent } from './type-acteur.component';
import { TypeActeurDetailComponent } from './type-acteur-detail.component';
import { TypeActeurUpdateComponent } from './type-acteur-update.component';

@Injectable({ providedIn: 'root' })
export class TypeActeurResolve implements Resolve<ITypeActeur> {
  constructor(private service: TypeActeurService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeActeur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeActeur: HttpResponse<TypeActeur>) => {
          if (typeActeur.body) {
            return of(typeActeur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeActeur());
  }
}

export const typeActeurRoute: Routes = [
  {
    path: '',
    component: TypeActeurComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.typeActeur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeActeurDetailComponent,
    resolve: {
      typeActeur: TypeActeurResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.typeActeur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeActeurUpdateComponent,
    resolve: {
      typeActeur: TypeActeurResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.typeActeur.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeActeurUpdateComponent,
    resolve: {
      typeActeur: TypeActeurResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.typeActeur.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
