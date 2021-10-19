import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategorieOrganisation, CategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { CategorieOrganisationService } from './categorie-organisation.service';
import { CategorieOrganisationComponent } from './categorie-organisation.component';
import { CategorieOrganisationDetailComponent } from './categorie-organisation-detail.component';
import { CategorieOrganisationUpdateComponent } from './categorie-organisation-update.component';

@Injectable({ providedIn: 'root' })
export class CategorieOrganisationResolve implements Resolve<ICategorieOrganisation> {
  constructor(private service: CategorieOrganisationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieOrganisation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categorieOrganisation: HttpResponse<CategorieOrganisation>) => {
          if (categorieOrganisation.body) {
            return of(categorieOrganisation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieOrganisation());
  }
}

export const categorieOrganisationRoute: Routes = [
  {
    path: '',
    component: CategorieOrganisationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.categorieOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategorieOrganisationDetailComponent,
    resolve: {
      categorieOrganisation: CategorieOrganisationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.categorieOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategorieOrganisationUpdateComponent,
    resolve: {
      categorieOrganisation: CategorieOrganisationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.categorieOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategorieOrganisationUpdateComponent,
    resolve: {
      categorieOrganisation: CategorieOrganisationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.categorieOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
