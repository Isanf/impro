import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

type EntityResponseType = HttpResponse<IInfoCommandeCarnetASouche>;
type EntityArrayResponseType = HttpResponse<IInfoCommandeCarnetASouche[]>;

@Injectable({ providedIn: 'root' })
export class InfoCommandeCarnetASoucheService {
  public resourceUrl = SERVER_API_URL + 'api/info-commande-carnet-a-souches';

  constructor(protected http: HttpClient) {}

  create(infoCommandeCarnetASouche: IInfoCommandeCarnetASouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infoCommandeCarnetASouche);
    return this.http
      .post<IInfoCommandeCarnetASouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(infoCommandeCarnetASouche: IInfoCommandeCarnetASouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infoCommandeCarnetASouche);
    return this.http
      .put<IInfoCommandeCarnetASouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInfoCommandeCarnetASouche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInfoCommandeCarnetASouche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(infoCommandeCarnetASouche: IInfoCommandeCarnetASouche): IInfoCommandeCarnetASouche {
    const copy: IInfoCommandeCarnetASouche = Object.assign({}, infoCommandeCarnetASouche, {
      dateCommande:
        infoCommandeCarnetASouche.dateCommande && infoCommandeCarnetASouche.dateCommande.isValid()
          ? infoCommandeCarnetASouche.dateCommande.toJSON()
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
      res.body.forEach((infoCommandeCarnetASouche: IInfoCommandeCarnetASouche) => {
        infoCommandeCarnetASouche.dateCommande = infoCommandeCarnetASouche.dateCommande
          ? moment(infoCommandeCarnetASouche.dateCommande)
          : undefined;
      });
    }
    return res;
  }
}
