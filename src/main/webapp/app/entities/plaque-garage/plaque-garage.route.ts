import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlaqueGarage, PlaqueGarage } from 'app/shared/model/plaque-garage.model';
import { PlaqueGarageService } from './plaque-garage.service';
import { PlaqueGarageComponent } from './plaque-garage.component';
import { PlaqueGarageDetailComponent } from './plaque-garage-detail.component';
import { PlaqueGarageUpdateComponent } from './plaque-garage-update.component';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { CarteW } from 'app/shared/model/carte-w.model';

@Injectable({ providedIn: 'root' })
export class PlaqueGarageResolve implements Resolve<IPlaqueGarage> {
  constructor(private service: PlaqueGarageService, private carteService: CarteWService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlaqueGarage> | Observable<never> {
    const id = route.params['id'];
    // const idd = route.params['id'];
    // if (id) {
    //   return this.service.find(id).pipe(
    //     flatMap((plaqueGarage: HttpResponse<PlaqueGarage>) => {
    //       if (plaqueGarage.body) {
    //         return of(plaqueGarage.body);
    //       } else {
    //         this.router.navigate(['404']);
    //         return EMPTY;
    //       }
    //     })
    //   );
    // }
    if (id) {
      return this.carteService.find(id).pipe(
        flatMap((carteW: HttpResponse<CarteW>) => {
          if (carteW.body) {
            return of(carteW.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlaqueGarage());
  }
}

export const plaqueGarageRoute: Routes = [
  {
    path: '',
    component: PlaqueGarageComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.plaqueGarage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlaqueGarageDetailComponent,
    resolve: {
      plaqueGarage: PlaqueGarageResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.plaqueGarage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlaqueGarageUpdateComponent,
    resolve: {
      plaqueGarage: PlaqueGarageResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.plaqueGarage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlaqueGarageUpdateComponent,
    resolve: {
      plaqueGarage: PlaqueGarageResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'improApp.plaqueGarage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
