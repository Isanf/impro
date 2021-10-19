import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';
import { PersonneMoraleService } from './personne-morale.service';
import { PersonneMoraleComponent } from './personne-morale.component';
import { PersonneMoraleDetailComponent } from './personne-morale-detail.component';
import { PersonneMoraleUpdateComponent } from './personne-morale-update.component';
import { IPersonneMorale } from 'app/shared/model/personne-morale.model';

@Injectable({ providedIn: 'root' })
export class PersonneMoraleResolve implements Resolve<IPersonneMorale> {
  constructor(private service: PersonneMoraleService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonneMorale> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((personneMorale: HttpResponse<PersonneMorale>) => personneMorale.body));
    }
    return of(new PersonneMorale());
  }
}

export const personneMoraleRoute: Routes = [
  {
    path: '',
    component: PersonneMoraleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.personneMorale.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonneMoraleDetailComponent,
    resolve: {
      personneMorale: PersonneMoraleResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.personneMorale.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonneMoraleUpdateComponent,
    resolve: {
      personneMorale: PersonneMoraleResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.personneMorale.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonneMoraleUpdateComponent,
    resolve: {
      personneMorale: PersonneMoraleResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.personneMorale.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
