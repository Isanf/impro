import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';

type EntityResponseType = HttpResponse<ICertificatImmatriculation>;
type EntityArrayResponseType = HttpResponse<ICertificatImmatriculation[]>;

@Injectable({ providedIn: 'root' })
export class CertificatImmatriculationService {
  public resourceUrl = SERVER_API_URL + 'api/certificat-immatriculations';

  constructor(protected http: HttpClient) {}

  create(certificatImmatriculation: ICertificatImmatriculation): Observable<EntityResponseType> {
    return this.http.post<ICertificatImmatriculation>(this.resourceUrl, certificatImmatriculation, { observe: 'response' });
  }

  update(certificatImmatriculation: ICertificatImmatriculation): Observable<EntityResponseType> {
    return this.http.put<ICertificatImmatriculation>(this.resourceUrl, certificatImmatriculation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICertificatImmatriculation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  findqr(qr: string): Observable<EntityResponseType> {
    const options = new HttpParams().set('code', qr);
    return this.http.get<ICertificatImmatriculation>(`${this.resourceUrl}/by`, { params: options, observe: 'response' });
  }
  findTotalAdmin(): Observable<any> {
    return this.http.get(`${this.resourceUrl}/nombreAdmin`);
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICertificatImmatriculation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
