import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';

type EntityResponseType = HttpResponse<ITypeActeur>;
type EntityArrayResponseType = HttpResponse<ITypeActeur[]>;

@Injectable({ providedIn: 'root' })
export class TypeActeurService {
  public resourceUrl = SERVER_API_URL + 'api/type-acteurs';

  constructor(protected http: HttpClient) {}

  create(typeActeur: ITypeActeur): Observable<EntityResponseType> {
    return this.http.post<ITypeActeur>(this.resourceUrl, typeActeur, { observe: 'response' });
  }

  update(typeActeur: ITypeActeur): Observable<EntityResponseType> {
    return this.http.put<ITypeActeur>(this.resourceUrl, typeActeur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeActeur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeActeur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
