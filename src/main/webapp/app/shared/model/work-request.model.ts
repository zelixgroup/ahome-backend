import { Moment } from 'moment';
import { IWork } from 'app/shared/model/work.model';
import { IAppUser } from 'app/shared/model/app-user.model';
import { IAddress } from 'app/shared/model/address.model';
import { WorkRequestMagnitude } from 'app/shared/model/enumerations/work-request-magnitude.model';

export interface IWorkRequest {
  id?: number;
  creationDateTime?: Moment;
  forMysef?: boolean;
  constructionSite?: boolean;
  mediatorPercentage?: number;
  interventionDateTime?: Moment;
  detailedDescription?: string;
  magnitude?: WorkRequestMagnitude;
  estimatedWorkFees?: number;
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
    public interventionDateTime?: Moment,
    public detailedDescription?: string,
    public magnitude?: WorkRequestMagnitude,
    public estimatedWorkFees?: number,
    public work?: IWork,
    public user?: IAppUser,
    public address?: IAddress
  ) {
    this.forMysef = this.forMysef || false;
    this.constructionSite = this.constructionSite || false;
  }
}
