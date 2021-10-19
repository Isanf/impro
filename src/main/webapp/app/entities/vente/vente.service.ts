import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVente } from 'app/shared/model/vente.model';

type EntityResponseType = HttpResponse<IVente>;
type EntityArrayResponseType = HttpResponse<IVente[]>;

@Injectable({ providedIn: 'root' })
export class VenteService {
  public resourceUrl = SERVER_API_URL + 'api/ventes';

  constructor(protected http: HttpClient) {}

  create(vente: IVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .post<IVente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  createStock(vente: IVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .post<IVente>(this.resourceUrl + 'Stock', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vente: IVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .put<IVente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateStock(vente: IVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .put<IVente>(this.resourceUrl + 'Stock', copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  findCertificatVente(id: number): Observable<any> {
    return this.http.get(`${this.resourceUrl}/vente/${id}`, { responseType: 'blob' });
  }
  findCertificatConformite(id: number): Observable<any> {
    return this.http.get(`${this.resourceUrl}/conformite/${id}`, { responseType: 'blob' });
  }
  findFactureVente(id: number): Observable<any> {
    return this.http.get(`${this.resourceUrl}/facture/${id}`, { responseType: 'blob' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vente: IVente): IVente {
    const copy: IVente = Object.assign({}, vente, {
      dateVente: vente.dateVente && vente.dateVente.isValid() ? vente.dateVente.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateVente = res.body.dateVente ? moment(res.body.dateVente) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vente: IVente) => {
        vente.dateVente = vente.dateVente ? moment(vente.dateVente) : undefined;
      });
    }
    return res;
  }
}
