import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatistique } from 'app/shared/model/statistique.model';

type EntityResponseType = HttpResponse<IStatistique>;
type EntityArrayResponseType = HttpResponse<IStatistique[]>;

@Injectable({ providedIn: 'root' })
export class StatistiqueService {
  public resourceUrl = SERVER_API_URL + 'api/statistiques';

  constructor(protected http: HttpClient) {}

  create(statistique: IStatistique): Observable<EntityResponseType> {
    return this.http.post<IStatistique>(this.resourceUrl, statistique, { observe: 'response' });
  }

  update(statistique: IStatistique): Observable<EntityResponseType> {
    return this.http.put<IStatistique>(this.resourceUrl, statistique, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatistique>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatistique[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
