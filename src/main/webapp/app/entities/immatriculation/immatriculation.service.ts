import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImmatriculation } from 'app/shared/model/immatriculation.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IDatesModel } from 'app/shared/model/datesmodel';
import { Audit } from 'app/admin/audits/audit.model';

type EntityResponseType = HttpResponse<IImmatriculation>;
type EntityArrayResponseType = HttpResponse<IImmatriculation[]>;

@Injectable({ providedIn: 'root' })
export class ImmatriculationService {
  public resourceUrl = SERVER_API_URL + 'api/immatriculations';

  constructor(protected http: HttpClient) {}

  create(immatriculation: IImmatriculation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immatriculation);
    return this.http
      .post<IImmatriculation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(immatriculation: IImmatriculation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(immatriculation);
    return this.http
      .put<IImmatriculation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImmatriculation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  findAllImmAdmin(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/AllAdmin`);
  }
  findAllImmOrganisation(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/AllOrganisation`);
  }

  ImPeriode4AllOrg(req: any): Observable<number[]> {
    const options: HttpParams = createRequestOption(req);
    options.set('fromDate', req.fromDate);
    options.set('toDate', req.toDate);
    return this.http.get<number[]>(this.resourceUrl + 'Imperiode4AllOrg', { params: options });
  }

  find4Org(req: any): Observable<number> {
    const options: HttpParams = createRequestOption(req);
    options.set('fromDate', req.fromDate);
    options.set('toDate', req.toDate);
    options.set('idOrg', req.idOrg);
    return this.http.get<number>(this.resourceUrl + 'Find4Org', { params: options });
  }

  queryIm(req: any): Observable<number[]> {
    const options: HttpParams = createRequestOption(req);
    options.set('fromDate', req.fromDate);
    options.set('toDate', req.toDate);
    return this.http.get<number[]>(this.resourceUrl + '4Im', { params: options });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImmatriculation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  gettotal(): Observable<HttpResponse<any>> {
    return this.http.get(this.resourceUrl + '/total', { observe: 'response' });
  }

  queryOrganisations(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImmatriculation[]>(this.resourceUrl + '/organisations', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(immatriculation: IImmatriculation): IImmatriculation {
    const copy: IImmatriculation = Object.assign({}, immatriculation, {
      dateImmatriculation:
        immatriculation.dateImmatriculation && immatriculation.dateImmatriculation.isValid()
          ? immatriculation.dateImmatriculation.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateImmatriculation = res.body.dateImmatriculation ? moment(res.body.dateImmatriculation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((immatriculation: IImmatriculation) => {
        immatriculation.dateImmatriculation = immatriculation.dateImmatriculation ? moment(immatriculation.dateImmatriculation) : undefined;
      });
    }
    return res;
  }
}
