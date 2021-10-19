import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

type EntityResponseType = HttpResponse<ILivraisonVehicule>;
type EntityArrayResponseType = HttpResponse<ILivraisonVehicule[]>;

@Injectable({ providedIn: 'root' })
export class LivraisonVehiculeService {
  public resourceUrl = SERVER_API_URL + 'api/livraison-vehicules';

  constructor(protected http: HttpClient) {}

  create(livraisonVehicule: ILivraisonVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonVehicule);
    return this.http
      .post<ILivraisonVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(livraisonVehicule: ILivraisonVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonVehicule);
    return this.http
      .put<ILivraisonVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILivraisonVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraisonVehicule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query1(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraisonVehicule[]>(this.resourceUrl + '1', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(livraisonVehicule: ILivraisonVehicule): ILivraisonVehicule {
    const copy: ILivraisonVehicule = Object.assign({}, livraisonVehicule, {
      dateLivraison:
        livraisonVehicule.dateLivraison && livraisonVehicule.dateLivraison.isValid() ? livraisonVehicule.dateLivraison.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((livraisonVehicule: ILivraisonVehicule) => {
        livraisonVehicule.dateLivraison = livraisonVehicule.dateLivraison ? moment(livraisonVehicule.dateLivraison) : undefined;
      });
    }
    return res;
  }
}
