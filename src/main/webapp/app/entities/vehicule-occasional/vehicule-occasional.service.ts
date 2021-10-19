import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';

type EntityResponseType = HttpResponse<IVehiculeOccasional>;
type EntityArrayResponseType = HttpResponse<IVehiculeOccasional[]>;

@Injectable({ providedIn: 'root' })
export class VehiculeOccasionalService {
  public resourceUrl = SERVER_API_URL + 'api/vehicule-occasionals';

  constructor(protected http: HttpClient) {}

  create(vehiculeOccasional: IVehiculeOccasional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehiculeOccasional);
    return this.http
      .post<IVehiculeOccasional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vehiculeOccasional: IVehiculeOccasional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vehiculeOccasional);
    return this.http
      .put<IVehiculeOccasional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVehiculeOccasional>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVehiculeOccasional[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vehiculeOccasional: IVehiculeOccasional): IVehiculeOccasional {
    const copy: IVehiculeOccasional = Object.assign({}, vehiculeOccasional, {
      createdAt: vehiculeOccasional.createdAt && vehiculeOccasional.createdAt.isValid() ? vehiculeOccasional.createdAt.toJSON() : undefined
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
      res.body.forEach((vehiculeOccasional: IVehiculeOccasional) => {
        vehiculeOccasional.createdAt = vehiculeOccasional.createdAt ? moment(vehiculeOccasional.createdAt) : undefined;
      });
    }
    return res;
  }
}
