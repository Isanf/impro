export interface ICountry {
  id?: number;
  iso?: string;
  name?: string;
  nicename?: string;
  iso3?: string;
  numcode?: number;
  phonecode?: number;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public iso?: string,
    public name?: string,
    public nicename?: string,
    public iso3?: string,
    public numcode?: number,
    public phonecode?: number
  ) {}
}
