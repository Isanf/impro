import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeOrganisation, TypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeOrganisationService } from './type-organisation.service';
import { TypeOrganisationComponent } from './type-organisation.component';
import { TypeOrganisationDetailComponent } from './type-organisation-detail.component';
import { TypeOrganisationUpdateComponent } from './type-organisation-update.component';

@Injectable({ providedIn: 'root' })
export class TypeOrganisationResolve implements Resolve<ITypeOrganisation> {
  constructor(private service: TypeOrganisationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeOrganisation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeOrganisation: HttpResponse<TypeOrganisation>) => {
          if (typeOrganisation.body) {
            return of(typeOrganisation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeOrganisation());
  }
}

export const typeOrganisationRoute: Routes = [
  {
    path: '',
    component: TypeOrganisationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.typeOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeOrganisationDetailComponent,
    resolve: {
      typeOrganisation: TypeOrganisationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.typeOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeOrganisationUpdateComponent,
    resolve: {
      typeOrganisation: TypeOrganisationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.typeOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeOrganisationUpdateComponent,
    resolve: {
      typeOrganisation: TypeOrganisationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.typeOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
