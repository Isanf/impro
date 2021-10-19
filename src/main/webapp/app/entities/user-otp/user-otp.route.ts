import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserOtp, UserOtp } from 'app/shared/model/user-otp.model';
import { UserOtpService } from './user-otp.service';
import { UserOtpComponent } from './user-otp.component';
import { UserOtpDetailComponent } from './user-otp-detail.component';
import { UserOtpUpdateComponent } from './user-otp-update.component';

@Injectable({ providedIn: 'root' })
export class UserOtpResolve implements Resolve<IUserOtp> {
  constructor(private service: UserOtpService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserOtp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userOtp: HttpResponse<UserOtp>) => {
          if (userOtp.body) {
            return of(userOtp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserOtp());
  }
}

export const userOtpRoute: Routes = [
  {
    path: '',
    component: UserOtpComponent,
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.userOtp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserOtpDetailComponent,
    resolve: {
      userOtp: UserOtpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userOtp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserOtpUpdateComponent,
    resolve: {
      userOtp: UserOtpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userOtp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserOtpUpdateComponent,
    resolve: {
      userOtp: UserOtpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'improApp.userOtp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
