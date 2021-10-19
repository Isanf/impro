import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserOtp } from 'app/shared/model/user-otp.model';

type EntityResponseType = HttpResponse<IUserOtp>;
type EntityArrayResponseType = HttpResponse<IUserOtp[]>;

@Injectable({ providedIn: 'root' })
export class UserOtpService {
  public resourceUrl = SERVER_API_URL + 'api/user-otps';

  constructor(protected http: HttpClient) {}

  create(userOtp: IUserOtp): Observable<EntityResponseType> {
    return this.http.post<IUserOtp>(this.resourceUrl, userOtp, { observe: 'response' });
  }

  update(userOtp: IUserOtp): Observable<EntityResponseType> {
    return this.http.put<IUserOtp>(this.resourceUrl, userOtp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserOtp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserOtp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
