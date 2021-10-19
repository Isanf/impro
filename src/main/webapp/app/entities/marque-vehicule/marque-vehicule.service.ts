import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<IMarqueVehicule>;
type EntityArrayResponseType = HttpResponse<IMarqueVehicule[]>;

@Injectable({ providedIn: 'root' })
export class MarqueVehiculeService {
  public resourceUrl = SERVER_API_URL + 'api/marque-vehicules';

  constructor(protected http: HttpClient) {}

  create(marqueVehicule: IMarqueVehicule): Observable<EntityResponseType> {
    return this.http.post<IMarqueVehicule>(this.resourceUrl, marqueVehicule, { observe: 'response' });
  }

  update(marqueVehicule: IMarqueVehicule): Observable<EntityResponseType> {
    return this.http.put<IMarqueVehicule>(this.resourceUrl, marqueVehicule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMarqueVehicule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMarqueVehicule[]>('api/allmarques', { params: options, observe: 'response' });
  }

  createFile(file: File): Observable<EntityResponseType> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(this.resourceUrl + '/file', formData, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
