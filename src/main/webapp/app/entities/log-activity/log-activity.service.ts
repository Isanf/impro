import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILogActivity } from 'app/shared/model/log-activity.model';

type EntityResponseType = HttpResponse<ILogActivity>;
type EntityArrayResponseType = HttpResponse<ILogActivity[]>;

@Injectable({ providedIn: 'root' })
export class LogActivityService {
  public resourceUrl = SERVER_API_URL + 'api/log-activities';

  constructor(protected http: HttpClient) {}

  create(logActivity: ILogActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logActivity);
    return this.http
      .post<ILogActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logActivity: ILogActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logActivity);
    return this.http
      .put<ILogActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(logActivity: ILogActivity): ILogActivity {
    const copy: ILogActivity = Object.assign({}, logActivity, {
      dateAction: logActivity.dateAction && logActivity.dateAction.isValid() ? logActivity.dateAction.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAction = res.body.dateAction ? moment(res.body.dateAction) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logActivity: ILogActivity) => {
        logActivity.dateAction = logActivity.dateAction ? moment(logActivity.dateAction) : undefined;
      });
    }
    return res;
  }
}
