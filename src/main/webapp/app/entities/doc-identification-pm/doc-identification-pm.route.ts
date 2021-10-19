import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocIdentificationPM, DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { DocIdentificationPMService } from './doc-identification-pm.service';
import { DocIdentificationPMComponent } from './doc-identification-pm.component';
import { DocIdentificationPMDetailComponent } from './doc-identification-pm-detail.component';
import { DocIdentificationPMUpdateComponent } from './doc-identification-pm-update.component';

@Injectable({ providedIn: 'root' })
export class DocIdentificationPMResolve implements Resolve<IDocIdentificationPM> {
  constructor(private service: DocIdentificationPMService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocIdentificationPM> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((docIdentificationPM: HttpResponse<DocIdentificationPM>) => {
          if (docIdentificationPM.body) {
            return of(docIdentificationPM.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocIdentificationPM());
  }
}

export const docIdentificationPMRoute: Routes = [
  {
    path: '',
    component: DocIdentificationPMComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.docIdentificationPM.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocIdentificationPMDetailComponent,
    resolve: {
      docIdentificationPM: DocIdentificationPMResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.docIdentificationPM.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocIdentificationPMUpdateComponent,
    resolve: {
      docIdentificationPM: DocIdentificationPMResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.docIdentificationPM.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocIdentificationPMUpdateComponent,
    resolve: {
      docIdentificationPM: DocIdentificationPMResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR', 'STHGUICHET'],
      pageTitle: 'improApp.docIdentificationPM.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
