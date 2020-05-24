import { IWorkCategory } from 'app/shared/model/work-category.model';

export interface IWork {
  id?: number;
  name?: string;
  description?: string;
  picture?: string;
  icone?: string;
  pricePerHour?: number;
  category?: IWorkCategory;
}

export class Work implements IWork {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public picture?: string,
    public icone?: string,
    public pricePerHour?: number,
    public category?: IWorkCategory
  ) {}
}
