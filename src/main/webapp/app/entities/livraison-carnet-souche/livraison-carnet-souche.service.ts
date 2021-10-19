import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

type EntityResponseType = HttpResponse<ILivraisonCarnetSouche>;
type EntityArrayResponseType = HttpResponse<ILivraisonCarnetSouche[]>;

@Injectable({ providedIn: 'root' })
export class LivraisonCarnetSoucheService {
  public resourceUrl = SERVER_API_URL + 'api/livraison-carnet-souches';

  constructor(protected http: HttpClient, private localStorage: LocalStorageService, private sessionStorage: SessionStorageService) {}

  create(livraisonCarnetSouche: ILivraisonCarnetSouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonCarnetSouche);
    return this.http
      .post<ILivraisonCarnetSouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(livraisonCarnetSouche: ILivraisonCarnetSouche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(livraisonCarnetSouche);
    return this.http
      .put<ILivraisonCarnetSouche>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILivraisonCarnetSouche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findToLivrer(id: number): Observable<HttpResponse<ILivraisonCarnetSouche>> {
    return this.http
      .get(`${this.resourceUrl}/${id}/livrer`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILivraisonCarnetSouche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(livraisonCarnetSouche: ILivraisonCarnetSouche): ILivraisonCarnetSouche {
    const copy: ILivraisonCarnetSouche = Object.assign({}, livraisonCarnetSouche, {
      dateLivraison:
        livraisonCarnetSouche.dateLivraison && livraisonCarnetSouche.dateLivraison.isValid()
          ? livraisonCarnetSouche.dateLivraison.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateLivraison = res.body.dateLivraison ? moment(res.body.dateLivraison) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((livraisonCarnetSouche: ILivraisonCarnetSouche) => {
        livraisonCarnetSouche.dateLivraison = livraisonCarnetSouche.dateLivraison ? moment(livraisonCarnetSouche.dateLivraison) : undefined;
      });
    }
    return res;
  }

  find4Org(req: any): Observable<number> {
    const options: HttpParams = createRequestOption(req);
    options.set('fromDate', req.fromDate);
    options.set('toDate', req.toDate);
    options.set('idOrg', req.idOrg);
    return this.http.get<number>(this.resourceUrl + 'Find4Org', { params: options });
  }
}
