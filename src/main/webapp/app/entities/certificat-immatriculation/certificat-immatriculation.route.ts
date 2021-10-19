import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICertificatImmatriculation, CertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';
import { CertificatImmatriculationService } from './certificat-immatriculation.service';
import { CertificatImmatriculationComponent } from './certificat-immatriculation.component';
import { CertificatImmatriculationDetailComponent } from './certificat-immatriculation-detail.component';
import { CertificatImmatriculationUpdateComponent } from './certificat-immatriculation-update.component';

@Injectable({ providedIn: 'root' })
export class CertificatImmatriculationResolve implements Resolve<ICertificatImmatriculation> {
  constructor(private service: CertificatImmatriculationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICertificatImmatriculation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((certificatImmatriculation: HttpResponse<CertificatImmatriculation>) => {
          if (certificatImmatriculation.body) {
            return of(certificatImmatriculation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CertificatImmatriculation());
  }
}

export const certificatImmatriculationRoute: Routes = [
  {
    path: '',
    component: CertificatImmatriculationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.certificatImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CertificatImmatriculationDetailComponent,
    resolve: {
      certificatImmatriculation: CertificatImmatriculationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.certificatImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CertificatImmatriculationUpdateComponent,
    resolve: {
      certificatImmatriculation: CertificatImmatriculationResolve
    },
    data: {
      authorities: [''],
      pageTitle: 'improApp.certificatImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CertificatImmatriculationUpdateComponent,
    resolve: {
      certificatImmatriculation: CertificatImmatriculationResolve
    },
    data: {
      authorities: [''],
      pageTitle: 'improApp.certificatImmatriculation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
