import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfil } from 'app/shared/model/profil.model';
import { IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { map } from 'rxjs/operators';
import { IOrganisation } from 'app/shared/model/organisation.model';

type EntityResponseType = HttpResponse<IProfil>;
type EntityArrayResponseType = HttpResponse<IProfil[]>;

@Injectable({ providedIn: 'root' })
export class ProfilService {
  public resourceUrl = SERVER_API_URL + 'api/profils';

  constructor(protected http: HttpClient) {}

  create(profil: IProfil): Observable<EntityResponseType> {
    return this.http.post<IProfil>(this.resourceUrl, profil, { observe: 'response' });
  }

  update(profil: IProfil): Observable<EntityResponseType> {
    return this.http.put<IProfil>(this.resourceUrl, profil, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryCreatedBy(): Observable<HttpResponse<IProfil[]>> {
    return this.http.get<IProfil[]>(this.resourceUrl + '/AllCreatedBy', { observe: 'response' });
  }

  /*queryList(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfil[]>(this.resourceUrl + '-List', { params: options, observe: 'response' });
  }*/

  queryList(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfil[]>(this.resourceUrl + '-list', { params: options, observe: 'response' });
  }

  /*querylist(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocIdentificationPP[]>(this.resourceUrl + '-list', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((docIdentificationPP: IDocIdentificationPP) => {
        docIdentificationPP.dateEtablissement = docIdentificationPP.dateEtablissement
          ? moment(docIdentificationPP.dateEtablissement)
          : undefined;
      });
    }
    return res;
  }*/

  queryByMyOrganisation(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfil[]>(this.resourceUrl + '/ByMyOrganisation', { params: options, observe: 'response' });
  }

  queryForMyRevendeurs(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfil[]>(this.resourceUrl + '/ForMyRevendeurs', { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
