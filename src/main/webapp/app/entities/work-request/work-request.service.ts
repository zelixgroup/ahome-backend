import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkRequest } from 'app/shared/model/work-request.model';

type EntityResponseType = HttpResponse<IWorkRequest>;
type EntityArrayResponseType = HttpResponse<IWorkRequest[]>;

@Injectable({ providedIn: 'root' })
export class WorkRequestService {
  public resourceUrl = SERVER_API_URL + 'api/work-requests';

  constructor(protected http: HttpClient) {}

  create(workRequest: IWorkRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workRequest);
    return this.http
      .post<IWorkRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workRequest: IWorkRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workRequest);
    return this.http
      .put<IWorkRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workRequest: IWorkRequest): IWorkRequest {
    const copy: IWorkRequest = Object.assign({}, workRequest, {
      creationDateTime:
        workRequest.creationDateTime && workRequest.creationDateTime.isValid() ? workRequest.creationDateTime.toJSON() : undefined,
      interventionDateTime:
        workRequest.interventionDateTime && workRequest.interventionDateTime.isValid()
          ? workRequest.interventionDateTime.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDateTime = res.body.creationDateTime ? moment(res.body.creationDateTime) : undefined;
      res.body.interventionDateTime = res.body.interventionDateTime ? moment(res.body.interventionDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((workRequest: IWorkRequest) => {
        workRequest.creationDateTime = workRequest.creationDateTime ? moment(workRequest.creationDateTime) : undefined;
        workRequest.interventionDateTime = workRequest.interventionDateTime ? moment(workRequest.interventionDateTime) : undefined;
      });
    }
    return res;
  }
}
