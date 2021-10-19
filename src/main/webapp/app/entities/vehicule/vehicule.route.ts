import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from './vehicule.service';
import { VehiculeComponent } from './vehicule.component';
import { VehiculeDetailComponent } from './vehicule-detail.component';
import { VehiculeUpdateComponent } from './vehicule-update.component';
import { IVehicule } from 'app/shared/model/vehicule.model';

@Injectable({ providedIn: 'root' })
export class VehiculeResolve implements Resolve<IVehicule> {
  constructor(private service: VehiculeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((vehicule: HttpResponse<Vehicule>) => vehicule.body));
    }
    return of(new Vehicule());
  }
}

export const vehiculeRoute: Routes = [
  {
    path: '',
    component: VehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehiculeDetailComponent,
    resolve: {
      vehicule: VehiculeResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehiculeUpdateComponent,
    resolve: {
      vehicule: VehiculeResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehiculeUpdateComponent,
    resolve: {
      vehicule: VehiculeResolve
    },
    data: {
      authorities: ['FIRSTLOGIN_OK'],
      pageTitle: 'improApp.vehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
