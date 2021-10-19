import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';

type EntityResponseType = HttpResponse<ITypeOrganisation>;
type EntityArrayResponseType = HttpResponse<ITypeOrganisation[]>;

@Injectable({ providedIn: 'root' })
export class TypeOrganisationService {
  public resourceUrl = SERVER_API_URL + 'api/type-organisations';

  constructor(protected http: HttpClient) {}

  create(typeOrganisation: ITypeOrganisation): Observable<EntityResponseType> {
    return this.http.post<ITypeOrganisation>(this.resourceUrl, typeOrganisation, { observe: 'response' });
  }

  update(typeOrganisation: ITypeOrganisation): Observable<EntityResponseType> {
    return this.http.put<ITypeOrganisation>(this.resourceUrl, typeOrganisation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeOrganisation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeOrganisation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
