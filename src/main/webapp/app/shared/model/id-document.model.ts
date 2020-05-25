import { IIdDocumentType } from 'app/shared/model/id-document-type.model';
import { IWorker } from 'app/shared/model/worker.model';

export interface IIdDocument {
  id?: number;
  idDocumentRectoContentType?: string;
  idDocumentRecto?: any;
  idDocumentVersoContentType?: string;
  idDocumentVerso?: any;
  type?: IIdDocumentType;
  worker?: IWorker;
  worker?: IWorker;
}

export class IdDocument implements IIdDocument {
  constructor(
    public id?: number,
    public idDocumentRectoContentType?: string,
    public idDocumentRecto?: any,
    public idDocumentVersoContentType?: string,
    public idDocumentVerso?: any,
    public type?: IIdDocumentType,
    public worker?: IWorker,
    public worker?: IWorker
  ) {}
}
