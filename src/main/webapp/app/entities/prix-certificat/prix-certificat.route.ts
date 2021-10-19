import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrixCertificat, PrixCertificat } from 'app/shared/model/prix-certificat.model';
import { PrixCertificatService } from './prix-certificat.service';
import { PrixCertificatComponent } from './prix-certificat.component';
import { PrixCertificatDetailComponent } from './prix-certificat-detail.component';
import { PrixCertificatUpdateComponent } from './prix-certificat-update.component';

@Injectable({ providedIn: 'root' })
export class PrixCertificatResolve implements Resolve<IPrixCertificat> {
  constructor(private service: PrixCertificatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrixCertificat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prixCertificat: HttpResponse<PrixCertificat>) => {
          if (prixCertificat.body) {
            return of(prixCertificat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PrixCertificat());
  }
}

export const prixCertificatRoute: Routes = [
  {
    path: '',
    component: PrixCertificatComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.prixCertificat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrixCertificatDetailComponent,
    resolve: {
      prixCertificat: PrixCertificatResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.prixCertificat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrixCertificatUpdateComponent,
    resolve: {
      prixCertificat: PrixCertificatResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.prixCertificat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrixCertificatUpdateComponent,
    resolve: {
      prixCertificat: PrixCertificatResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.prixCertificat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
