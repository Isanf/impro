import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';

type EntityResponseType = HttpResponse<ICarnetASouche>;
type EntityArrayResponseType = HttpResponse<ICarnetASouche[]>;

@Injectable({ providedIn: 'root' })
export class CarnetASoucheService {
  public resourceUrl = SERVER_API_URL + 'api/carnet-a-souches';

  constructor(protected http: HttpClient) {}

  create(carnetASouche: ICarnetASouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carnetASouche);
    return this.http
      .post<ICarnetASouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(carnetASouche: ICarnetASouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carnetASouche);
    return this.http
      .put<ICarnetASouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICarnetASouche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findCarnet(id: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<ICarnetASouche[]>(`${this.resourceUrl}/carnet/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  findTotal(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombre`);
  }
  findUsed(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreUsed`);
  }
  findCarnetCertif(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreCertif`);
  }

  findCarnetCertif4Org(req: any): Observable<number> {
    const options: HttpParams = createRequestOption(req);
    options.set('fromDate', req.fromDate);
    options.set('toDate', req.toDate);
    options.set('idOrg', req.idOrg);
    return this.http.get<number>(this.resourceUrl + '/nombreCertif4Org', { params: options });
  }

  findCarnet4Org(req: any): Observable<number> {
    const options: HttpParams = createRequestOption(req);
    options.set('fromDate', req.fromDate);
    options.set('toDate', req.toDate);
    options.set('idOrg', req.idOrg);
    return this.http.get<number>(this.resourceUrl + '/nbCarnet4Org', { params: options });
  }

  findCarnetCertifUsed(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreCertifUsed`);
  }
  findTotalAdmin(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreAdmin`);
  }
  findTotalAdminUsed(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreAdminUsed`);
  }
  findTotalAdminAll(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreAdminAll`);
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarnetASouche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  queryAdmin(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarnetASouche[]>(this.resourceUrl + '/admin', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(carnetASouche: ICarnetASouche): ICarnetASouche {
    const copy: ICarnetASouche = Object.assign({}, carnetASouche, {
      dateImpression:
        carnetASouche.dateImpression && carnetASouche.dateImpression.isValid() ? carnetASouche.dateImpression.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateImpression = res.body.dateImpression ? moment(res.body.dateImpression) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((carnetASouche: ICarnetASouche) => {
        carnetASouche.dateImpression = carnetASouche.dateImpression ? moment(carnetASouche.dateImpression) : undefined;
      });
    }
    return res;
  }
}
