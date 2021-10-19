import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlaqueGarage, PlaqueGarage } from 'app/shared/model/plaque-garage.model';

type EntityResponseType = HttpResponse<IPlaqueGarage>;
type EntityArrayResponseType = HttpResponse<IPlaqueGarage[]>;

@Injectable({ providedIn: 'root' })
export class PlaqueGarageService {
  public resourceUrl = SERVER_API_URL + 'api/plaque-garages';

  constructor(protected http: HttpClient) {}

  create(plaqueGarage: PlaqueGarage[]): Observable<any> {
    return this.http.post<IPlaqueGarage[]>(this.resourceUrl, plaqueGarage, { observe: 'response' });
  }

  update(plaqueGarage: IPlaqueGarage): Observable<EntityResponseType> {
    return this.http.put<IPlaqueGarage>(this.resourceUrl, plaqueGarage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlaqueGarage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlaqueGarage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByGarage(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlaqueGarage[]>(`${this.resourceUrl}/${id}/garage`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
