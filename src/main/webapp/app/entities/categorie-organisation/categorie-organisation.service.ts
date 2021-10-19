import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

type EntityResponseType = HttpResponse<ICategorieOrganisation>;
type EntityArrayResponseType = HttpResponse<ICategorieOrganisation[]>;

@Injectable({ providedIn: 'root' })
export class CategorieOrganisationService {
  public resourceUrl = SERVER_API_URL + 'api/categorie-organisations';

  constructor(protected http: HttpClient) {}

  create(categorieOrganisation: ICategorieOrganisation): Observable<EntityResponseType> {
    return this.http.post<ICategorieOrganisation>(this.resourceUrl, categorieOrganisation, { observe: 'response' });
  }

  update(categorieOrganisation: ICategorieOrganisation): Observable<EntityResponseType> {
    return this.http.put<ICategorieOrganisation>(this.resourceUrl, categorieOrganisation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieOrganisation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieOrganisation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
