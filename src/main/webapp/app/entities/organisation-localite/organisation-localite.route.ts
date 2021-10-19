import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrganisationLocalite, OrganisationLocalite } from 'app/shared/model/organisation-localite.model';
import { OrganisationLocaliteService } from './organisation-localite.service';
import { OrganisationLocaliteComponent } from './organisation-localite.component';
import { OrganisationLocaliteDetailComponent } from './organisation-localite-detail.component';
import { OrganisationLocaliteUpdateComponent } from './organisation-localite-update.component';

@Injectable({ providedIn: 'root' })
export class OrganisationLocaliteResolve implements Resolve<IOrganisationLocalite> {
  constructor(private service: OrganisationLocaliteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganisationLocalite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((organisationLocalite: HttpResponse<OrganisationLocalite>) => {
          if (organisationLocalite.body) {
            return of(organisationLocalite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrganisationLocalite());
  }
}

export const organisationLocaliteRoute: Routes = [
  {
    path: '',
    component: OrganisationLocaliteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.organisationLocalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrganisationLocaliteDetailComponent,
    resolve: {
      organisationLocalite: OrganisationLocaliteResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.organisationLocalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrganisationLocaliteUpdateComponent,
    resolve: {
      organisationLocalite: OrganisationLocaliteResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.organisationLocalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrganisationLocaliteUpdateComponent,
    resolve: {
      organisationLocalite: OrganisationLocaliteResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.organisationLocalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
