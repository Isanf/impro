import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

type EntityResponseType = HttpResponse<ICommandeCarnetSouche>;
type EntityArrayResponseType = HttpResponse<ICommandeCarnetSouche[]>;

@Injectable({ providedIn: 'root' })
export class CommandeCarnetSoucheService {
  public resourceUrl = SERVER_API_URL + 'api/commande-carnet-souches';

  constructor(protected http: HttpClient) {}

  create(commandeCarnetSouche: ICommandeCarnetSouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeCarnetSouche);
    return this.http
      .post<ICommandeCarnetSouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commandeCarnetSouche: ICommandeCarnetSouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commandeCarnetSouche);
    return this.http
      .put<ICommandeCarnetSouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeCarnetSouche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  validAdmin(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommandeCarnetSouche>(`${this.resourceUrl}/admin/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  printFacture(id: number): Observable<Blob> {
    return this.http.get(`${this.resourceUrl}/facture/${id}`, { responseType: 'blob' });
  }

  valideConcess(id: number): Observable<Blob> {
    return this.http.get(`${this.resourceUrl}/valider/${id}`, { responseType: 'blob' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeCarnetSouche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommandeCarnetSouche[]>(this.resourceUrl + '/admin', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commandeCarnetSouche: ICommandeCarnetSouche): ICommandeCarnetSouche {
    const copy: ICommandeCarnetSouche = Object.assign({}, commandeCarnetSouche, {
      dateCommandeCS:
        commandeCarnetSouche.dateCommandeCS && commandeCarnetSouche.dateCommandeCS.isValid()
          ? commandeCarnetSouche.dateCommandeCS.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCommandeCS = res.body.dateCommandeCS ? moment(res.body.dateCommandeCS) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commandeCarnetSouche: ICommandeCarnetSouche) => {
        commandeCarnetSouche.dateCommandeCS = commandeCarnetSouche.dateCommandeCS ? moment(commandeCarnetSouche.dateCommandeCS) : undefined;
      });
    }
    return res;
  }
}
