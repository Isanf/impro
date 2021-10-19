import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrganisationLocalite } from 'app/shared/model/organisation-localite.model';

type EntityResponseType = HttpResponse<IOrganisationLocalite>;
type EntityArrayResponseType = HttpResponse<IOrganisationLocalite[]>;

@Injectable({ providedIn: 'root' })
export class OrganisationLocaliteService {
  public resourceUrl = SERVER_API_URL + 'api/organisation-localites';

  constructor(protected http: HttpClient) {}

  create(organisationLocalite: IOrganisationLocalite): Observable<EntityResponseType> {
    return this.http.post<IOrganisationLocalite>(this.resourceUrl, organisationLocalite, { observe: 'response' });
  }

  update(organisationLocalite: IOrganisationLocalite): Observable<EntityResponseType> {
    return this.http.put<IOrganisationLocalite>(this.resourceUrl, organisationLocalite, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganisationLocalite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisationLocalite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
