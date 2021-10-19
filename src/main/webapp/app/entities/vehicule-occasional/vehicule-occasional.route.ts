import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVehiculeOccasional, VehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';
import { VehiculeOccasionalService } from './vehicule-occasional.service';
import { VehiculeOccasionalComponent } from './vehicule-occasional.component';
import { VehiculeOccasionalDetailComponent } from './vehicule-occasional-detail.component';
import { VehiculeOccasionalUpdateComponent } from './vehicule-occasional-update.component';

@Injectable({ providedIn: 'root' })
export class VehiculeOccasionalResolve implements Resolve<IVehiculeOccasional> {
  constructor(private service: VehiculeOccasionalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehiculeOccasional> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vehiculeOccasional: HttpResponse<VehiculeOccasional>) => {
          if (vehiculeOccasional.body) {
            return of(vehiculeOccasional.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehiculeOccasional());
  }
}

export const vehiculeOccasionalRoute: Routes = [
  {
    path: '',
    component: VehiculeOccasionalComponent,
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.vehiculeOccasional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehiculeOccasionalDetailComponent,
    resolve: {
      vehiculeOccasional: VehiculeOccasionalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.vehiculeOccasional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehiculeOccasionalUpdateComponent,
    resolve: {
      vehiculeOccasional: VehiculeOccasionalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.vehiculeOccasional.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehiculeOccasionalUpdateComponent,
    resolve: {
      vehiculeOccasional: VehiculeOccasionalResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.vehiculeOccasional.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
