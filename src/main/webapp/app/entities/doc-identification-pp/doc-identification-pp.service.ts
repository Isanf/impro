import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { DocIdentificationPP, IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

type EntityResponseType = HttpResponse<IDocIdentificationPP>;
type EntityArrayResponseType = HttpResponse<IDocIdentificationPP[]>;

@Injectable({ providedIn: 'root' })
export class DocIdentificationPPService {
  public resourceUrl = SERVER_API_URL + 'api/doc-identification-pps';

  constructor(protected http: HttpClient) {}

  create(docIdentificationPP: IDocIdentificationPP): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docIdentificationPP);
    return this.http
      .post<IDocIdentificationPP>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(docIdentificationPP: IDocIdentificationPP): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docIdentificationPP);
    return this.http
      .put<IDocIdentificationPP>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocIdentificationPP>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByNip(nip: string): Observable<HttpResponse<DocIdentificationPP>> {
    return this.http.get<DocIdentificationPP>(`${this.resourceUrl + '-nip'}/${nip}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocIdentificationPP[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  querylist(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocIdentificationPP[]>(this.resourceUrl + '-list', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(docIdentificationPP: IDocIdentificationPP): IDocIdentificationPP {
    const copy: IDocIdentificationPP = Object.assign({}, docIdentificationPP, {
      dateEtablissement:
        docIdentificationPP.dateEtablissement && docIdentificationPP.dateEtablissement.isValid()
          ? docIdentificationPP.dateEtablissement.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateEtablissement = res.body.dateEtablissement ? moment(res.body.dateEtablissement) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((docIdentificationPP: IDocIdentificationPP) => {
        docIdentificationPP.dateEtablissement = docIdentificationPP.dateEtablissement
          ? moment(docIdentificationPP.dateEtablissement)
          : undefined;
      });
    }
    return res;
  }
}
