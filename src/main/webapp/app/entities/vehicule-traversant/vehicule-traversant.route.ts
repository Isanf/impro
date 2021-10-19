import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVehiculeTraversant, VehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';
import { VehiculeTraversantService } from './vehicule-traversant.service';
import { VehiculeTraversantComponent } from './vehicule-traversant.component';
import { VehiculeTraversantDetailComponent } from './vehicule-traversant-detail.component';
import { VehiculeTraversantUpdateComponent } from './vehicule-traversant-update.component';

@Injectable({ providedIn: 'root' })
export class VehiculeTraversantResolve implements Resolve<IVehiculeTraversant> {
  constructor(private service: VehiculeTraversantService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehiculeTraversant> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vehiculeTraversant: HttpResponse<VehiculeTraversant>) => {
          if (vehiculeTraversant.body) {
            return of(vehiculeTraversant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehiculeTraversant());
  }
}

export const vehiculeTraversantRoute: Routes = [
  {
    path: '',
    component: VehiculeTraversantComponent,
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.vehiculeTraversant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehiculeTraversantDetailComponent,
    resolve: {
      vehiculeTraversant: VehiculeTraversantResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.vehiculeTraversant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehiculeTraversantUpdateComponent,
    resolve: {
      vehiculeTraversant: VehiculeTraversantResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.vehiculeTraversant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehiculeTraversantUpdateComponent,
    resolve: {
      vehiculeTraversant: VehiculeTraversantResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'STHGUICHET'],
      pageTitle: 'improApp.vehiculeTraversant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
