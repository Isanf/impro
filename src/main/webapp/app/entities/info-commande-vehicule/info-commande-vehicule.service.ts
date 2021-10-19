import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

type EntityResponseType = HttpResponse<IInfoCommandeVehicule>;
type EntityArrayResponseType = HttpResponse<IInfoCommandeVehicule[]>;

@Injectable({ providedIn: 'root' })
export class InfoCommandeVehiculeService {
  public resourceUrl = SERVER_API_URL + 'api/info-commande-vehicules';

  constructor(protected http: HttpClient) {}

  create(infoCommandeVehicule: IInfoCommandeVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infoCommandeVehicule);
    return this.http
      .post<IInfoCommandeVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(infoCommandeVehicule: IInfoCommandeVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infoCommandeVehicule);
    return this.http
      .put<IInfoCommandeVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInfoCommandeVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInfoCommandeVehicule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(infoCommandeVehicule: IInfoCommandeVehicule): IInfoCommandeVehicule {
    const copy: IInfoCommandeVehicule = Object.assign({}, infoCommandeVehicule, {
      dateCommande:
        infoCommandeVehicule.dateCommande && infoCommandeVehicule.dateCommande.isValid()
          ? infoCommandeVehicule.dateCommande.toJSON()
          : undefined
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
      res.body.forEach((infoCommandeVehicule: IInfoCommandeVehicule) => {
        infoCommandeVehicule.dateCommande = infoCommandeVehicule.dateCommande ? moment(infoCommandeVehicule.dateCommande) : undefined;
      });
    }
    return res;
  }
}
