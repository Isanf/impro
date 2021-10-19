import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';

type EntityResponseType = HttpResponse<ICommandeVehicule>;
type EntityArrayResponseType = HttpResponse<ICommandeVehicule[]>;

@Injectable({ providedIn: 'root' })
export class CommandeVehiculeService {
  public resourceUrl = SERVER_API_URL + 'api/commande-vehicules';

  constructor(protected http: HttpClient) {}

  create(commandeVehicule: ICommandeVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeVehicule);
    return this.http
      .post<ICommandeVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeVehicule: ICommandeVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeVehicule);
    return this.http
      .put<ICommandeVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeVehicule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryNotDelivery(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeVehicule[]>(this.resourceUrl + '/notdelivery', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query1(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeVehicule[]>(this.resourceUrl + '1', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandeVehicule: ICommandeVehicule): ICommandeVehicule {
    const copy: ICommandeVehicule = Object.assign({}, commandeVehicule, {
      dateCommande:
        commandeVehicule.dateCommande && commandeVehicule.dateCommande.isValid() ? commandeVehicule.dateCommande.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCommande = res.body.dateCommande ? moment(res.body.dateCommande) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandeVehicule: ICommandeVehicule) => {
        commandeVehicule.dateCommande = commandeVehicule.dateCommande ? moment(commandeVehicule.dateCommande) : undefined;
      });
    }
    return res;
  }
}
