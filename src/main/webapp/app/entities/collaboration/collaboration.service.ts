import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICollaboration } from 'app/shared/model/collaboration.model';

type EntityResponseType = HttpResponse<ICollaboration>;
type EntityArrayResponseType = HttpResponse<ICollaboration[]>;

@Injectable({ providedIn: 'root' })
export class CollaborationService {
  public resourceUrl = SERVER_API_URL + 'api/collaborations';

  constructor(protected http: HttpClient) {}

  create(collaboration: ICollaboration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaboration);
    return this.http
      .post<ICollaboration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(collaboration: ICollaboration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collaboration);
    return this.http
      .put<ICollaboration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICollaboration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICollaboration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByme(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICollaboration[]>(this.resourceUrl + '/byme', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(collaboration: ICollaboration): ICollaboration {
    const copy: ICollaboration = Object.assign({}, collaboration, {
      dateDebut: collaboration.dateDebut && collaboration.dateDebut.isValid() ? collaboration.dateDebut.format(DATE_FORMAT) : undefined,
      dateFin: collaboration.dateFin && collaboration.dateFin.isValid() ? collaboration.dateFin.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? moment(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((collaboration: ICollaboration) => {
        collaboration.dateDebut = collaboration.dateDebut ? moment(collaboration.dateDebut) : undefined;
        collaboration.dateFin = collaboration.dateFin ? moment(collaboration.dateFin) : undefined;
      });
    }
    return res;
  }
}
