import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVehiculeOccasion, VehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';
import { VehiculeOccasionService } from './vehicule-occasion.service';
import { VehiculeOccasionComponent } from './vehicule-occasion.component';
import { VehiculeOccasionDetailComponent } from './vehicule-occasion-detail.component';
import { VehiculeOccasionUpdateComponent } from './vehicule-occasion-update.component';

@Injectable({ providedIn: 'root' })
export class VehiculeOccasionResolve implements Resolve<IVehiculeOccasion> {
  constructor(private service: VehiculeOccasionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehiculeOccasion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vehiculeOccasion: HttpResponse<VehiculeOccasion>) => {
          if (vehiculeOccasion.body) {
            return of(vehiculeOccasion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VehiculeOccasion());
  }
}

export const vehiculeOccasionRoute: Routes = [
  {
    path: '',
    component: VehiculeOccasionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.vehiculeOccasion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehiculeOccasionDetailComponent,
    resolve: {
      vehiculeOccasion: VehiculeOccasionResolve
    },
    data: {
      authorities: ['STHGUICHET'],
      pageTitle: 'improApp.vehiculeOccasion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehiculeOccasionUpdateComponent,
    resolve: {
      vehiculeOccasion: VehiculeOccasionResolve
    },
    data: {
      authorities: ['STHGUICHET'],
      pageTitle: 'improApp.vehiculeOccasion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehiculeOccasionUpdateComponent,
    resolve: {
      vehiculeOccasion: VehiculeOccasionResolve
    },
    data: {
      authorities: ['STHGUICHET'],
      pageTitle: 'improApp.vehiculeOccasion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
