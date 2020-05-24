import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkReview } from 'app/shared/model/work-review.model';

type EntityResponseType = HttpResponse<IWorkReview>;
type EntityArrayResponseType = HttpResponse<IWorkReview[]>;

@Injectable({ providedIn: 'root' })
export class WorkReviewService {
  public resourceUrl = SERVER_API_URL + 'api/work-reviews';

  constructor(protected http: HttpClient) {}

  create(workReview: IWorkReview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workReview);
    return this.http
      .post<IWorkReview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workReview: IWorkReview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workReview);
    return this.http
      .put<IWorkReview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkReview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkReview[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workReview: IWorkReview): IWorkReview {
    const copy: IWorkReview = Object.assign({}, workReview, {
      reviewDateTime: workReview.reviewDateTime && workReview.reviewDateTime.isValid() ? workReview.reviewDateTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.reviewDateTime = res.body.reviewDateTime ? moment(res.body.reviewDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((workReview: IWorkReview) => {
        workReview.reviewDateTime = workReview.reviewDateTime ? moment(workReview.reviewDateTime) : undefined;
      });
    }
    return res;
  }
}
