import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICollaboration, Collaboration } from 'app/shared/model/collaboration.model';
import { CollaborationService } from './collaboration.service';
import { CollaborationComponent } from './collaboration.component';
import { CollaborationDetailComponent } from './collaboration-detail.component';
import { CollaborationUpdateComponent } from './collaboration-update.component';

@Injectable({ providedIn: 'root' })
export class CollaborationResolve implements Resolve<ICollaboration> {
  constructor(private service: CollaborationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollaboration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collaboration: HttpResponse<Collaboration>) => {
          if (collaboration.body) {
            return of(collaboration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Collaboration());
  }
}

export const collaborationRoute: Routes = [
  {
    path: '',
    component: CollaborationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.collaboration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollaborationDetailComponent,
    resolve: {
      collaboration: CollaborationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.collaboration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollaborationUpdateComponent,
    resolve: {
      collaboration: CollaborationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.collaboration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollaborationUpdateComponent,
    resolve: {
      collaboration: CollaborationResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.collaboration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
