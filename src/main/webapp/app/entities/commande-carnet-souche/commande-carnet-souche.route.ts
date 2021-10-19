import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY, Subscription } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeCarnetSouche, CommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { CommandeCarnetSoucheService } from './commande-carnet-souche.service';
import { CommandeCarnetSoucheComponent } from './commande-carnet-souche.component';
import { CommandeCarnetSoucheDetailComponent } from './commande-carnet-souche-detail.component';
import { CommandeCarnetSoucheUpdateComponent } from './commande-carnet-souche-update.component';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';

@Injectable({ providedIn: 'root' })
export class CommandeCarnetSoucheResolve implements Resolve<ICommandeCarnetSouche> {
  constructor(private service: CommandeCarnetSoucheService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeCarnetSouche> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeCarnetSouche: HttpResponse<CommandeCarnetSouche>) => {
          if (commandeCarnetSouche.body) {
            return of(commandeCarnetSouche.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeCarnetSouche());
  }
}

export const commandeCarnetSoucheRoute: Routes = [
  {
    path: '',
    component: CommandeCarnetSoucheComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.commandeCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandeCarnetSoucheDetailComponent,
    resolve: {
      commandeCarnetSouche: CommandeCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.commandeCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandeCarnetSoucheUpdateComponent,
    resolve: {
      commandeCarnetSouche: CommandeCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.commandeCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandeCarnetSoucheUpdateComponent,
    resolve: {
      commandeCarnetSouche: CommandeCarnetSoucheResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'STHGUICHET'],
      pageTitle: 'improApp.commandeCarnetSouche.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
