import { IIdDocument } from 'app/shared/model/id-document.model';
import { IAppUser } from 'app/shared/model/app-user.model';

export interface IWorker {
  id?: number;
  pictureContentType?: string;
  picture?: any;
  workCertificateContentType?: string;
  workCertificate?: any;
  idDocuments?: IIdDocument[];
  user?: IAppUser;
}

export class Worker implements IWorker {
  constructor(
    public id?: number,
    public pictureContentType?: string,
    public picture?: any,
    public workCertificateContentType?: string,
    public workCertificate?: any,
    public idDocuments?: IIdDocument[],
    public user?: IAppUser
  ) {}
}
