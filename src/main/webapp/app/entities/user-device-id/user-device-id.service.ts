import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserDeviceId } from 'app/shared/model/user-device-id.model';

type EntityResponseType = HttpResponse<IUserDeviceId>;
type EntityArrayResponseType = HttpResponse<IUserDeviceId[]>;

@Injectable({ providedIn: 'root' })
export class UserDeviceIdService {
  public resourceUrl = SERVER_API_URL + 'api/user-device-ids';

  constructor(protected http: HttpClient) {}

  create(userDeviceId: IUserDeviceId): Observable<EntityResponseType> {
    return this.http.post<IUserDeviceId>(this.resourceUrl, userDeviceId, { observe: 'response' });
  }

  update(userDeviceId: IUserDeviceId): Observable<EntityResponseType> {
    return this.http.put<IUserDeviceId>(this.resourceUrl, userDeviceId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserDeviceId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserDeviceId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
