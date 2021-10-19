import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocIdentificationPP, DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPPService } from './doc-identification-pp.service';
import { DocIdentificationPPComponent } from './doc-identification-pp.component';
import { DocIdentificationPPDetailComponent } from './doc-identification-pp-detail.component';
import { DocIdentificationPPUpdateComponent } from './doc-identification-pp-update.component';

@Injectable({ providedIn: 'root' })
export class DocIdentificationPPResolve implements Resolve<IDocIdentificationPP> {
  constructor(private service: DocIdentificationPPService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocIdentificationPP> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((docIdentificationPP: HttpResponse<DocIdentificationPP>) => {
          if (docIdentificationPP.body) {
            return of(docIdentificationPP.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocIdentificationPP());
  }
}

export const docIdentificationPPRoute: Routes = [
  {
    path: '',
    component: DocIdentificationPPComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.docIdentificationPP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocIdentificationPPDetailComponent,
    resolve: {
      docIdentificationPP: DocIdentificationPPResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.docIdentificationPP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocIdentificationPPUpdateComponent,
    resolve: {
      docIdentificationPP: DocIdentificationPPResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.docIdentificationPP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocIdentificationPPUpdateComponent,
    resolve: {
      docIdentificationPP: DocIdentificationPPResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.docIdentificationPP.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
