import { Moment } from 'moment';
import { IWorkRequest } from 'app/shared/model/work-request.model';
import { WorkRequestStatus } from 'app/shared/model/enumerations/work-request-status.model';

export interface IWorkRequestStatusChange {
  id?: number;
  oldStatus?: WorkRequestStatus;
  newStatus?: WorkRequestStatus;
  dateTimeOfStatusChange?: Moment;
  workRequest?: IWorkRequest;
  workRequest?: IWorkRequest;
}

export class WorkRequestStatusChange implements IWorkRequestStatusChange {
  constructor(
    public id?: number,
    public oldStatus?: WorkRequestStatus,
    public newStatus?: WorkRequestStatus,
    public dateTimeOfStatusChange?: Moment,
    public workRequest?: IWorkRequest,
    public workRequest?: IWorkRequest
  ) {}
}
