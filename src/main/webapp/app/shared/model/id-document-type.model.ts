export interface IIdDocumentType {
  id?: number;
  label?: string;
  description?: string;
  needVerso?: boolean;
}

export class IdDocumentType implements IIdDocumentType {
  constructor(public id?: number, public label?: string, public description?: string, public needVerso?: boolean) {
    this.needVerso = this.needVerso || false;
  }
}
