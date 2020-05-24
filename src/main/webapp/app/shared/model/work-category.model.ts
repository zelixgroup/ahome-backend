export interface IWorkCategory {
  id?: number;
  name?: string;
  description?: string;
  picture?: string;
  icone?: string;
  pricePerHour?: number;
}

export class WorkCategory implements IWorkCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public picture?: string,
    public icone?: string,
    public pricePerHour?: number
  ) {}
}
