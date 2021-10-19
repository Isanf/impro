import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';

type EntityResponseType = HttpResponse<IVehiculeTraversant>;
type EntityArrayResponseType = HttpResponse<IVehiculeTraversant[]>;

@Injectable({ providedIn: 'root' })
export class VehiculeTraversantService {
  public resourceUrl = SERVER_API_URL + 'api/vehicule-traversants';

  constructor(protected http: HttpClient) {}

  create(vehiculeTraversant: IVehiculeTraversant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehiculeTraversant);
    return this.http
      .post<IVehiculeTraversant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehiculeTraversant: IVehiculeTraversant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehiculeTraversant);
    return this.http
      .put<IVehiculeTraversant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehiculeTraversant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehiculeTraversant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vehiculeTraversant: IVehiculeTraversant): IVehiculeTraversant {
    const copy: IVehiculeTraversant = Object.assign({}, vehiculeTraversant, {
      createdAt: vehiculeTraversant.createdAt && vehiculeTraversant.createdAt.isValid() ? vehiculeTraversant.createdAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vehiculeTraversant: IVehiculeTraversant) => {
        vehiculeTraversant.createdAt = vehiculeTraversant.createdAt ? moment(vehiculeTraversant.createdAt) : undefined;
      });
    }
    return res;
  }
}
