import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INation, Nation } from 'app/shared/model/nation.model';
import { NationService } from './nation.service';
import { NationComponent } from './nation.component';
import { NationDetailComponent } from './nation-detail.component';
import { NationUpdateComponent } from './nation-update.component';

@Injectable({ providedIn: 'root' })
export class NationResolve implements Resolve<INation> {
  constructor(private service: NationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nation: HttpResponse<Nation>) => {
          if (nation.body) {
            return of(nation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nation());
  }
}

export const nationRoute: Routes = [
  {
    path: '',
    component: NationComponent,
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.nation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NationDetailComponent,
    resolve: {
      nation: NationResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.nation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NationUpdateComponent,
    resolve: {
      nation: NationResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.nation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NationUpdateComponent,
    resolve: {
      nation: NationResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.nation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
