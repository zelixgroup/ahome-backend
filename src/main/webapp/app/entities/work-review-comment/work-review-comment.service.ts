import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkReviewComment } from 'app/shared/model/work-review-comment.model';

type EntityResponseType = HttpResponse<IWorkReviewComment>;
type EntityArrayResponseType = HttpResponse<IWorkReviewComment[]>;

@Injectable({ providedIn: 'root' })
export class WorkReviewCommentService {
  public resourceUrl = SERVER_API_URL + 'api/work-review-comments';

  constructor(protected http: HttpClient) {}

  create(workReviewComment: IWorkReviewComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workReviewComment);
    return this.http
      .post<IWorkReviewComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(workReviewComment: IWorkReviewComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workReviewComment);
    return this.http
      .put<IWorkReviewComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWorkReviewComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWorkReviewComment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(workReviewComment: IWorkReviewComment): IWorkReviewComment {
    const copy: IWorkReviewComment = Object.assign({}, workReviewComment, {
      commentDateTime:
        workReviewComment.commentDateTime && workReviewComment.commentDateTime.isValid()
          ? workReviewComment.commentDateTime.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.commentDateTime = res.body.commentDateTime ? moment(res.body.commentDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((workReviewComment: IWorkReviewComment) => {
        workReviewComment.commentDateTime = workReviewComment.commentDateTime ? moment(workReviewComment.commentDateTime) : undefined;
      });
    }
    return res;
  }
}
