import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';

type EntityResponseType = HttpResponse<ITypeCarnet>;
type EntityArrayResponseType = HttpResponse<ITypeCarnet[]>;

@Injectable({ providedIn: 'root' })
export class TypeCarnetService {
  public resourceUrl = SERVER_API_URL + 'api/type-carnets';

  constructor(protected http: HttpClient) {}

  create(typeCarnet: ITypeCarnet): Observable<EntityResponseType> {
    return this.http.post<ITypeCarnet>(this.resourceUrl, typeCarnet, { observe: 'response' });
  }

  update(typeCarnet: ITypeCarnet): Observable<EntityResponseType> {
    return this.http.put<ITypeCarnet>(this.resourceUrl, typeCarnet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeCarnet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeCarnet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
