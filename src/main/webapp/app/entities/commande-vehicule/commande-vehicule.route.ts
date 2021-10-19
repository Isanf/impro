import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandeVehicule, CommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { CommandeVehiculeService } from './commande-vehicule.service';
import { CommandeVehiculeComponent } from './commande-vehicule.component';
import { CommandeVehiculeDetailComponent } from './commande-vehicule-detail.component';
import { CommandeVehiculeUpdateComponent } from './commande-vehicule-update.component';

@Injectable({ providedIn: 'root' })
export class CommandeVehiculeResolve implements Resolve<ICommandeVehicule> {
  constructor(private service: CommandeVehiculeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandeVehicule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandeVehicule: HttpResponse<CommandeVehicule>) => {
          if (commandeVehicule.body) {
            return of(commandeVehicule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommandeVehicule());
  }
}

export const commandeVehiculeRoute: Routes = [
  {
    path: '',
    component: CommandeVehiculeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      defaultSort: 'id,asc',
      pageTitle: 'improApp.commandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommandeVehiculeDetailComponent,
    resolve: {
      commandeVehicule: CommandeVehiculeResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'CONCESSIONNAIRE', 'REVENDEUR'],
      pageTitle: 'improApp.commandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommandeVehiculeUpdateComponent,
    resolve: {
      commandeVehicule: CommandeVehiculeResolve
    },
    data: {
      authorities: ['REVENDEUR'],
      pageTitle: 'improApp.commandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommandeVehiculeUpdateComponent,
    resolve: {
      commandeVehicule: CommandeVehiculeResolve
    },
    data: {
      authorities: ['REVENDEUR'],
      pageTitle: 'improApp.commandeVehicule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
