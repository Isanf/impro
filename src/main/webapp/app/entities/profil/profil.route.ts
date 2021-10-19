import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Profil } from 'app/shared/model/profil.model';
import { ProfilService } from './profil.service';
import { ProfilComponent } from './profil.component';
import { ProfilDetailComponent } from './profil-detail.component';
import { ProfilUpdateComponent } from './profil-update.component';
import { IProfil } from 'app/shared/model/profil.model';

@Injectable({ providedIn: 'root' })
export class ProfilResolve implements Resolve<IProfil> {
  constructor(private service: ProfilService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfil> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profil: HttpResponse<Profil>) => profil.body));
    }
    return of(new Profil());
  }
}

export const profilRoute: Routes = [
  {
    path: '',
    component: ProfilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.profil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfilDetailComponent,
    resolve: {
      profil: ProfilResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      pageTitle: 'improApp.profil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfilUpdateComponent,
    resolve: {
      profil: ProfilResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      pageTitle: 'improApp.profil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfilUpdateComponent,
    resolve: {
      profil: ProfilResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      pageTitle: 'improApp.profil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
