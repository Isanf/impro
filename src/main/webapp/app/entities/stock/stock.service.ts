import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStock } from 'app/shared/model/stock.model';
import { IVehicule } from 'app/shared/model/vehicule.model';

type EntityResponseType = HttpResponse<IStock>;
type EntityArrayResponseType = HttpResponse<IStock[]>;

@Injectable({ providedIn: 'root' })
export class StockService {
  public resourceUrl = SERVER_API_URL + 'api/stocks';

  constructor(protected http: HttpClient) {}

  create(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .post<IStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  createFile(file: File): Observable<EntityResponseType> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http
      .post(this.resourceUrl + '/file', formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stock: IStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stock);
    return this.http
      .put<IStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  queryForConcessionnaire(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStock[]>(this.resourceUrl + 'ForConcessionnaire', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stock: IStock): IStock {
    const copy: IStock = Object.assign({}, stock, {
      dateStock: stock.dateStock != null && stock.dateStock.isValid() ? stock.dateStock.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateStock = res.body.dateStock != null ? moment(res.body.dateStock) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stock: IStock) => {
        stock.dateStock = stock.dateStock != null ? moment(stock.dateStock) : null;
      });
    }
    return res;
  }
}
