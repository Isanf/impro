import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonneMorale } from 'app/shared/model/personne-morale.model';

type EntityResponseType = HttpResponse<IPersonneMorale>;
type EntityArrayResponseType = HttpResponse<IPersonneMorale[]>;

@Injectable({ providedIn: 'root' })
export class PersonneMoraleService {
  public resourceUrl = SERVER_API_URL + 'api/personne-morales';

  constructor(protected http: HttpClient) {}

  create(personneMorale: IPersonneMorale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personneMorale);
    return this.http
      .post<IPersonneMorale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personneMorale: IPersonneMorale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personneMorale);
    return this.http
      .put<IPersonneMorale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonneMorale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonneMorale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personneMorale: IPersonneMorale): IPersonneMorale {
    const copy: IPersonneMorale = Object.assign({}, personneMorale, {
      dateCreate:
        personneMorale.dateCreate != null && personneMorale.dateCreate.isValid() ? personneMorale.dateCreate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreate = res.body.dateCreate != null ? moment(res.body.dateCreate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personneMorale: IPersonneMorale) => {
        personneMorale.dateCreate = personneMorale.dateCreate != null ? moment(personneMorale.dateCreate) : null;
      });
    }
    return res;
  }
}
