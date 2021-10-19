import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrixCertificat } from 'app/shared/model/prix-certificat.model';

type EntityResponseType = HttpResponse<IPrixCertificat>;
type EntityArrayResponseType = HttpResponse<IPrixCertificat[]>;

@Injectable({ providedIn: 'root' })
export class PrixCertificatService {
  public resourceUrl = SERVER_API_URL + 'api/prix-certificats';

  constructor(protected http: HttpClient) {}

  create(prixCertificat: IPrixCertificat): Observable<EntityResponseType> {
    return this.http.post<IPrixCertificat>(this.resourceUrl, prixCertificat, { observe: 'response' });
  }

  update(prixCertificat: IPrixCertificat): Observable<EntityResponseType> {
    return this.http.put<IPrixCertificat>(this.resourceUrl, prixCertificat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrixCertificat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrixCertificat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
