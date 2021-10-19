import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserDeviceId, UserDeviceId } from 'app/shared/model/user-device-id.model';
import { UserDeviceIdService } from './user-device-id.service';
import { UserDeviceIdComponent } from './user-device-id.component';
import { UserDeviceIdDetailComponent } from './user-device-id-detail.component';
import { UserDeviceIdUpdateComponent } from './user-device-id-update.component';

@Injectable({ providedIn: 'root' })
export class UserDeviceIdResolve implements Resolve<IUserDeviceId> {
  constructor(private service: UserDeviceIdService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserDeviceId> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userDeviceId: HttpResponse<UserDeviceId>) => {
          if (userDeviceId.body) {
            return of(userDeviceId.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserDeviceId());
  }
}

export const userDeviceIdRoute: Routes = [
  {
    path: '',
    component: UserDeviceIdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userDeviceId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserDeviceIdDetailComponent,
    resolve: {
      userDeviceId: UserDeviceIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userDeviceId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserDeviceIdUpdateComponent,
    resolve: {
      userDeviceId: UserDeviceIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userDeviceId.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserDeviceIdUpdateComponent,
    resolve: {
      userDeviceId: UserDeviceIdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userDeviceId.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
