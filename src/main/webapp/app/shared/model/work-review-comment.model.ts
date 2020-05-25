import { Moment } from 'moment';
import { IWorkReview } from 'app/shared/model/work-review.model';
import { IUser } from 'app/core/user/user.model';

export interface IWorkReviewComment {
  id?: number;
  commentDateTime?: Moment;
  comment?: string;
  workReview?: IWorkReview;
  user?: IUser;
  workReview?: IWorkReview;
}

export class WorkReviewComment implements IWorkReviewComment {
  constructor(
    public id?: number,
    public commentDateTime?: Moment,
    public comment?: string,
    public workReview?: IWorkReview,
    public user?: IUser,
    public workReview?: IWorkReview
  ) {}
}
