import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPersonnePhysique, PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonnePhysiqueService } from './personne-physique.service';
import { PersonnePhysiqueComponent } from './personne-physique.component';
import { PersonnePhysiqueDetailComponent } from './personne-physique-detail.component';
import { PersonnePhysiqueUpdateComponent } from './personne-physique-update.component';

@Injectable({ providedIn: 'root' })
export class PersonnePhysiqueResolve implements Resolve<IPersonnePhysique> {
  constructor(private service: PersonnePhysiqueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonnePhysique> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((personnePhysique: HttpResponse<PersonnePhysique>) => {
          if (personnePhysique.body) {
            return of(personnePhysique.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonnePhysique());
  }
}

export const personnePhysiqueRoute: Routes = [
  {
    path: '',
    component: PersonnePhysiqueComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.personnePhysique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonnePhysiqueDetailComponent,
    resolve: {
      personnePhysique: PersonnePhysiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.personnePhysique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonnePhysiqueUpdateComponent,
    resolve: {
      personnePhysique: PersonnePhysiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.personnePhysique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonnePhysiqueUpdateComponent,
    resolve: {
      personnePhysique: PersonnePhysiqueResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.personnePhysique.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
