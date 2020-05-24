import { ICity } from 'app/shared/model/city.model';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AddressType } from 'app/shared/model/enumerations/address-type.model';

export interface IAddress {
  id?: number;
  type?: AddressType;
  location?: string;
  geolocation?: string;
  primaryPhoneNumber?: string;
  secondaryPhoneNumber?: string;
  emailAddress?: string;
  city?: ICity;
  appUser?: IAppUser;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public type?: AddressType,
    public location?: string,
    public geolocation?: string,
    public primaryPhoneNumber?: string,
    public secondaryPhoneNumber?: string,
    public emailAddress?: string,
    public city?: ICity,
    public appUser?: IAppUser
  ) {}
}
