import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFirstlogin } from 'app/shared/model/firstlogin.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IUser } from 'app/core/user/user.model';

type EntityResponseType = HttpResponse<IFirstlogin>;
type EntityArrayResponseType = HttpResponse<IFirstlogin[]>;

@Injectable({ providedIn: 'root' })
export class FirstloginService {
  public resourceUrl = SERVER_API_URL + 'api/firstlogins';

  constructor(protected http: HttpClient) {}

  create(firstlogin: IFirstlogin): Observable<EntityResponseType> {
    return this.http.post<IFirstlogin>(this.resourceUrl, firstlogin, { observe: 'response' });
  }

  update(firstlogin: IFirstlogin): Observable<EntityResponseType> {
    return this.http.put<IFirstlogin>(this.resourceUrl, firstlogin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFirstlogin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFirstlogin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  firstlogin(): Observable<string> {
    return this.http.get<string>(this.resourceUrl + '/myfirstlogin');
  }

  queryUser(req?: any): Observable<EntityResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUser>(this.resourceUrl + '/user', { params: options, observe: 'response' });
  }
  /*queryMyorganisation(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganisation[]>(this.resourceUrl + '/myorganisation', { params: options, observe: 'response' });
  }*/

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
