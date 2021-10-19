import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPosePlaque } from 'app/shared/model/pose-plaque.model';

type EntityResponseType = HttpResponse<IPosePlaque>;
type EntityArrayResponseType = HttpResponse<IPosePlaque[]>;

@Injectable({ providedIn: 'root' })
export class PosePlaqueService {
  public resourceUrl = SERVER_API_URL + 'api/pose-plaques';

  constructor(protected http: HttpClient) {}

  create(posePlaque: IPosePlaque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(posePlaque);
    return this.http
      .post<IPosePlaque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(posePlaque: IPosePlaque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(posePlaque);
    return this.http
      .put<IPosePlaque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPosePlaque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPosePlaque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(posePlaque: IPosePlaque): IPosePlaque {
    const copy: IPosePlaque = Object.assign({}, posePlaque, {
      datePosePlaque: posePlaque.datePosePlaque != null && posePlaque.datePosePlaque.isValid() ? posePlaque.datePosePlaque.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datePosePlaque = res.body.datePosePlaque != null ? moment(res.body.datePosePlaque) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((posePlaque: IPosePlaque) => {
        posePlaque.datePosePlaque = posePlaque.datePosePlaque != null ? moment(posePlaque.datePosePlaque) : null;
      });
    }
    return res;
  }
}
