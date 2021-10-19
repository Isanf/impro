import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { DocIdentificationPM, IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

type EntityResponseType = HttpResponse<IDocIdentificationPM>;
type EntityArrayResponseType = HttpResponse<IDocIdentificationPM[]>;

@Injectable({ providedIn: 'root' })
export class DocIdentificationPMService {
  public resourceUrl = SERVER_API_URL + 'api/doc-identification-pms';

  constructor(protected http: HttpClient) {}

  create(docIdentificationPM: IDocIdentificationPM): Observable<EntityResponseType> {
    return this.http.post<IDocIdentificationPM>(this.resourceUrl, docIdentificationPM, { observe: 'response' });
  }

  update(docIdentificationPM: IDocIdentificationPM): Observable<EntityResponseType> {
    return this.http.put<IDocIdentificationPM>(this.resourceUrl, docIdentificationPM, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocIdentificationPM>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByIFU(ifu: string): Observable<HttpResponse<DocIdentificationPM>> {
    return this.http.get<DocIdentificationPM>(`${this.resourceUrl + '-ifu'}/${ifu}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocIdentificationPM[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
