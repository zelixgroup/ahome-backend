import { Moment } from 'moment';
import { IWorkRequest } from 'app/shared/model/work-request.model';

export interface IWorkReview {
  id?: number;
  reviewDateTime?: Moment;
  starsNumber?: number;
  workRequest?: IWorkRequest;
}

export class WorkReview implements IWorkReview {
  constructor(public id?: number, public reviewDateTime?: Moment, public starsNumber?: number, public workRequest?: IWorkRequest) {}
}
