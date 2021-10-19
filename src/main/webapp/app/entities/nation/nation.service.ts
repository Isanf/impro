import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INation } from 'app/shared/model/nation.model';

type EntityResponseType = HttpResponse<INation>;
type EntityArrayResponseType = HttpResponse<INation[]>;

@Injectable({ providedIn: 'root' })
export class NationService {
  public resourceUrl = SERVER_API_URL + 'api/nations';

  constructor(protected http: HttpClient) {}

  create(nation: INation): Observable<EntityResponseType> {
    return this.http.post<INation>(this.resourceUrl, nation, { observe: 'response' });
  }

  update(nation: INation): Observable<EntityResponseType> {
    return this.http.put<INation>(this.resourceUrl, nation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  querylist(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INation[]>(this.resourceUrl + 'list', { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
