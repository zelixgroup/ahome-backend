import { IAddress } from 'app/shared/model/address.model';
import { IUser } from 'app/core/user/user.model';

export interface IAppUser {
  id?: number;
  addresses?: IAddress[];
  user?: IUser;
}

export class AppUser implements IAppUser {
  constructor(public id?: number, public addresses?: IAddress[], public user?: IUser) {}
}
