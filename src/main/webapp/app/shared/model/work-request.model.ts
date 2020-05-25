import { Moment } from 'moment';
import { IWorkRequestStatusChange } from 'app/shared/model/work-request-status-change.model';
import { IWork } from 'app/shared/model/work.model';
import { IAppUser } from 'app/shared/model/app-user.model';
import { IAddress } from 'app/shared/model/address.model';
import { WorkRequestMagnitude } from 'app/shared/model/enumerations/work-request-magnitude.model';
import { WorkRequestStatus } from 'app/shared/model/enumerations/work-request-status.model';

export interface IWorkRequest {
  id?: number;
  creationDateTime?: Moment;
  forMysef?: boolean;
  constructionSite?: boolean;
  mediatorPercentage?: number;
  detailedDescription?: string;
  magnitude?: WorkRequestMagnitude;
  estimatedWorkFees?: number;
  plannedStartDateTime?: Moment;
  plannedEndDateTime?: Moment;
  status?: WorkRequestStatus;
  statusChanges?: IWorkRequestStatusChange[];
  work?: IWork;
  user?: IAppUser;
  address?: IAddress;
}

export class WorkRequest implements IWorkRequest {
  constructor(
    public id?: number,
    public creationDateTime?: Moment,
    public forMysef?: boolean,
    public constructionSite?: boolean,
    public mediatorPercentage?: number,
    public detailedDescription?: string,
    public magnitude?: WorkRequestMagnitude,
    public estimatedWorkFees?: number,
    public plannedStartDateTime?: Moment,
    public plannedEndDateTime?: Moment,
    public status?: WorkRequestStatus,
    public statusChanges?: IWorkRequestStatusChange[],
    public work?: IWork,
    public user?: IAppUser,
    public address?: IAddress
  ) {
    this.forMysef = this.forMysef || false;
    this.constructionSite = this.constructionSite || false;
  }
}
