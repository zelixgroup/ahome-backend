import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';

type EntityResponseType = HttpResponse<IWorkRequestStatusChange>;
type EntityArrayResponseType = HttpResponse<IWorkRequestStatusChange[]>;

@Injectable({ providedIn: 'root' })
export class WorkRequestStatusChangeService {
  public resourceUrl = SERVER_API_URL + 'api/work-request-status-changes';

  constructor(protected http: HttpClient) {}

  create(workRequestStatusChange: IWorkRequestStatusChange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workRequestStatusChange);
    return this.http
      .post<IWorkRequestStatusChange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workRequestStatusChange: IWorkRequestStatusChange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workRequestStatusChange);
    return this.http
      .put<IWorkRequestStatusChange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkRequestStatusChange>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkRequestStatusChange[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workRequestStatusChange: IWorkRequestStatusChange): IWorkRequestStatusChange {
    const copy: IWorkRequestStatusChange = Object.assign({}, workRequestStatusChange, {
      dateTimeOfStatusChange:
        workRequestStatusChange.dateTimeOfStatusChange && workRequestStatusChange.dateTimeOfStatusChange.isValid()
          ? workRequestStatusChange.dateTimeOfStatusChange.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateTimeOfStatusChange = res.body.dateTimeOfStatusChange ? moment(res.body.dateTimeOfStatusChange) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((workRequestStatusChange: IWorkRequestStatusChange) => {
        workRequestStatusChange.dateTimeOfStatusChange = workRequestStatusChange.dateTimeOfStatusChange
          ? moment(workRequestStatusChange.dateTimeOfStatusChange)
          : undefined;
      });
    }
    return res;
  }
}
