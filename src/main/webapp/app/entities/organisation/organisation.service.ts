import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrganisation } from 'app/shared/model/organisation.model';

type EntityResponseType = HttpResponse<IOrganisation>;
type EntityArrayResponseType = HttpResponse<IOrganisation[]>;

@Injectable({ providedIn: 'root' })
export class OrganisationService {
  public resourceUrl = SERVER_API_URL + 'api/organisations';

  constructor(protected http: HttpClient) {}

  create(organisation: IOrganisation): Observable<EntityResponseType> {
    return this.http.post<IOrganisation>(this.resourceUrl, organisation, { observe: 'response' });
  }

  update(organisation: IOrganisation): Observable<EntityResponseType> {
    return this.http.put<IOrganisation>(this.resourceUrl, organisation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganisation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryMyorganisation(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/myorganisation', { params: options, observe: 'response' });
  }

  queryAllorganisationsfils(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/allorganisationsfils', { params: options, observe: 'response' });
  }

  queryRevendeurs(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/revendeurs', { params: options, observe: 'response' });
  }
  queryConcessionaire(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/concessionaire', { params: options, observe: 'response' });
  }

  queryConcessionnairetotal(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/concessionnairetotal', { params: options, observe: 'response' });
  }

  queryRevendeurCollaborant(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/revendeurcollaborant', { params: options, observe: 'response' });
  }

  queryRevendeurCollaborantWith(idOrg?: any): Observable<HttpResponse<IOrganisation[]>> {
    const options = new HttpParams().set('idOrg', idOrg);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/revendeurcollaborantWith', { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  createFile(file: File): Observable<EntityResponseType> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(this.resourceUrl + '/file', formData, { observe: 'response' });
  }

  findByUser(login: string) {
    const options = new HttpParams().set('login', login);
    return this.http.get<IOrganisation>(this.resourceUrl + '/findByUser', { params: options, observe: 'response' });
  }
}
