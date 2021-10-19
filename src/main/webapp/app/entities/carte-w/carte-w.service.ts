import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICarteW } from 'app/shared/model/carte-w.model';

type EntityResponseType = HttpResponse<ICarteW>;
type EntityArrayResponseType = HttpResponse<ICarteW[]>;

@Injectable({ providedIn: 'root' })
export class CarteWService {
  public resourceUrl = SERVER_API_URL + 'api/carte-ws';

  constructor(protected http: HttpClient) {}

  create(carteW: ICarteW): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carteW);
    return this.http
      .post<ICarteW>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(carteW: ICarteW): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carteW);
    return this.http
      .put<ICarteW>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICarteW>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  findPrint(id: number): Observable<any> {
    return this.http.get(`${this.resourceUrl}/print/${id}`, { responseType: 'blob' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarteW[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(carteW: ICarteW): ICarteW {
    const copy: ICarteW = Object.assign({}, carteW, {
      dateEtablissementCarteW:
        carteW.dateEtablissementCarteW && carteW.dateEtablissementCarteW.isValid()
          ? carteW.dateEtablissementCarteW.format(DATE_FORMAT)
          : undefined,
      dateExpirationCarteW:
        carteW.dateExpirationCarteW && carteW.dateExpirationCarteW.isValid() ? carteW.dateExpirationCarteW.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateEtablissementCarteW = res.body.dateEtablissementCarteW ? moment(res.body.dateEtablissementCarteW) : undefined;
      res.body.dateExpirationCarteW = res.body.dateExpirationCarteW ? moment(res.body.dateExpirationCarteW) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((carteW: ICarteW) => {
        carteW.dateEtablissementCarteW = carteW.dateEtablissementCarteW ? moment(carteW.dateEtablissementCarteW) : undefined;
        carteW.dateExpirationCarteW = carteW.dateExpirationCarteW ? moment(carteW.dateExpirationCarteW) : undefined;
      });
    }
    return res;
  }
}
