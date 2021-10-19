import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatistique, Statistique } from 'app/shared/model/statistique.model';
import { StatistiqueService } from './statistique.service';
import { StatistiqueComponent } from './statistique.component';
import { StatistiqueDetailComponent } from './statistique-detail.component';
import { StatistiqueUpdateComponent } from './statistique-update.component';

@Injectable({ providedIn: 'root' })
export class StatistiqueResolve implements Resolve<IStatistique> {
  constructor(private service: StatistiqueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatistique> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statistique: HttpResponse<Statistique>) => {
          if (statistique.body) {
            return of(statistique.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Statistique());
  }
}

export const statistiqueRoute: Routes = [
  {
    path: '',
    component: StatistiqueComponent,
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.statistique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StatistiqueDetailComponent,
    resolve: {
      statistique: StatistiqueResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.statistique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StatistiqueUpdateComponent,
    resolve: {
      statistique: StatistiqueResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.statistique.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StatistiqueUpdateComponent,
    resolve: {
      statistique: StatistiqueResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.statistique.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
