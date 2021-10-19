import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';

type EntityResponseType = HttpResponse<IPersonnePhysique>;
type EntityArrayResponseType = HttpResponse<IPersonnePhysique[]>;

@Injectable({ providedIn: 'root' })
export class PersonnePhysiqueService {
  public resourceUrl = SERVER_API_URL + 'api/personne-physiques';

  constructor(protected http: HttpClient) {}

  create(personnePhysique: IPersonnePhysique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personnePhysique);
    return this.http
      .post<IPersonnePhysique>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personnePhysique: IPersonnePhysique): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personnePhysique);
    return this.http
      .put<IPersonnePhysique>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonnePhysique>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonnePhysique[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personnePhysique: IPersonnePhysique): IPersonnePhysique {
    const copy: IPersonnePhysique = Object.assign({}, personnePhysique, {
      dateNaissance:
        personnePhysique.dateNaissance != null && personnePhysique.dateNaissance.isValid()
          ? personnePhysique.dateNaissance.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance != null ? moment(res.body.dateNaissance) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personnePhysique: IPersonnePhysique) => {
        personnePhysique.dateNaissance = personnePhysique.dateNaissance != null ? moment(personnePhysique.dateNaissance) : null;
      });
    }
    return res;
  }
}
