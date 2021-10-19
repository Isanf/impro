import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

type EntityResponseType = HttpResponse<IVehicule>;
type EntityArrayResponseType = HttpResponse<IVehicule[]>;

@Injectable({ providedIn: 'root' })
export class VehiculeService {
  public resourceUrl = SERVER_API_URL + 'api/vehicules';

  constructor(protected http: HttpClient) {}

  create(vehicule: IVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicule);
    return this.http
      .post<IVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehicule: IVehicule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehicule);
    return this.http
      .put<IVehicule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByChassis(chassis: string): Observable<HttpResponse<Vehicule>> {
    return this.http.get<Vehicule>(`${this.resourceUrl + '-chassis'}/${chassis}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + 'ForConcessionnaire', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryh(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + 'horsstock', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryVehiculesForRevendeur(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + 'ForRevendeur', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  queryNDelivNSell(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + '/allnotdelivnotsell', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryDelivNSell(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + '/allDeliveryAndNotSell', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  allV(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + '/all', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  queryVehiculeConcessionnaire(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + 'Concessionnaire', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  queryVehiculeRevendeur(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + 'VehiculeRevendeur', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  /*queryVehiculeRevendeur(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehicule[]>(this.resourceUrl + 'Revendeurs', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }*/

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vehicule: IVehicule): IVehicule {
    const copy: IVehicule = Object.assign({}, vehicule, {
      dateMiseCirculation:
        vehicule.dateMiseCirculation != null && vehicule.dateMiseCirculation.isValid()
          ? vehicule.dateMiseCirculation.format(DATE_FORMAT)
          : null,
      dateDedouanement:
        vehicule.dateDedouanement != null && vehicule.dateDedouanement.isValid() ? vehicule.dateDedouanement.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateMiseCirculation = res.body.dateMiseCirculation != null ? moment(res.body.dateMiseCirculation) : null;
      res.body.dateDedouanement = res.body.dateDedouanement != null ? moment(res.body.dateDedouanement) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehicule: IVehicule) => {
        vehicule.dateMiseCirculation = vehicule.dateMiseCirculation != null ? moment(vehicule.dateMiseCirculation) : null;
        vehicule.dateDedouanement = vehicule.dateDedouanement != null ? moment(vehicule.dateDedouanement) : null;
      });
    }
    return res;
  }
}
