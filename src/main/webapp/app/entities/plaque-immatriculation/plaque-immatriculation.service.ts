import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

type EntityResponseType = HttpResponse<IPlaqueImmatriculation>;
type EntityArrayResponseType = HttpResponse<IPlaqueImmatriculation[]>;

@Injectable({ providedIn: 'root' })
export class PlaqueImmatriculationService {
  public resourceUrl = SERVER_API_URL + 'api/plaque-immatriculations';
  public url = SERVER_API_URL + 'api/decodage-quitance';

  constructor(protected http: HttpClient) {}

  create(plaqueImmatriculation: IPlaqueImmatriculation): Observable<EntityResponseType> {
    return this.http.post<IPlaqueImmatriculation>(this.resourceUrl, plaqueImmatriculation, { observe: 'response' });
  }

  update(plaqueImmatriculation: IPlaqueImmatriculation): Observable<EntityResponseType> {
    return this.http.put<IPlaqueImmatriculation>(this.resourceUrl, plaqueImmatriculation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlaqueImmatriculation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlaqueImmatriculation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryQr(req?: any): Observable<EntityResponseType> {
    const options = new HttpParams().set('code', req);
    return this.http.get<IPlaqueImmatriculation>(this.resourceUrl + '/by', { params: options, observe: 'response' });
  }

  checkqr(req?: string): Observable<any> {
    const options = new HttpParams().set('data', req);
    return this.http.get(this.url, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
