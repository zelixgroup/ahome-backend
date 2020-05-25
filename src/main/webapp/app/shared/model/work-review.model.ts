import { Moment } from 'moment';
import { IWorkRequest } from 'app/shared/model/work-request.model';
import { IWorkReviewComment } from 'app/shared/model/work-review-comment.model';

export interface IWorkReview {
  id?: number;
  reviewDateTime?: Moment;
  starsNumber?: number;
  notes?: string;
  workRequest?: IWorkRequest;
  workReviewComments?: IWorkReviewComment[];
}

export class WorkReview implements IWorkReview {
  constructor(
    public id?: number,
    public reviewDateTime?: Moment,
    public starsNumber?: number,
    public notes?: string,
    public workRequest?: IWorkRequest,
    public workReviewComments?: IWorkReviewComment[]
  ) {}
}
