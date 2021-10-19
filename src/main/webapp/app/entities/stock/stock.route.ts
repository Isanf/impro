import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockUpdateComponent } from './stock-update.component';
import { IStock } from 'app/shared/model/stock.model';

@Injectable({ providedIn: 'root' })
export class StockResolve implements Resolve<IStock> {
  constructor(private service: StockService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStock> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((stock: HttpResponse<Stock>) => stock.body));
    }
    return of(new Stock());
  }
}

export const stockRoute: Routes = [
  {
    path: '',
    component: StockComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockDetailComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE'],
      pageTitle: 'improApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
