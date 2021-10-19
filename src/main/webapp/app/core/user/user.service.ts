import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUser } from './user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  public resourceUrl = SERVER_API_URL + 'api/users';

  constructor(private http: HttpClient) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user);
  }

  find(login: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrl}/${login}`);
  }

  query(req?: any): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findByCreater(req?: any): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl + '/bycreater', { params: options, observe: 'response' });
  }

  findByMyOrg(req?: any): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl + '/byMyOrg', { params: options, observe: 'response' });
  }

  delete(login: string): Observable<any> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
  }
  authorities4STH(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4STH');
  }

  authorities4STHGuichet(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4STHGuichet');
  }

  authorities4Concessionnaire(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4Concessionnaire');
  }

  authorities4Revendeur(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4Revendeur');
  }

  authorities4DGTTM(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4DGTTM');
  }

  authorities4DOUANE(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4DOUANE');
  }

  authorities4DGDGTTM(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4DG_DGTTM');
  }

  authorities4DGDOUANE(): Observable<string[]> {
    return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities4DG_DOUANE');
  }

  report(login: string): Observable<string> {
    return this.http.get<string>(`${this.resourceUrl + '/report'}/${login}`);
  }
}
