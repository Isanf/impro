import { Moment } from 'moment';

export interface IPlaqueGarage {
  id?: number;
  numeroOrdre?: string;
  numeroPlaque?: string;
  codeQrPlaque?: string;
  createdAt?: Moment;
  carteWId?: number;
}

export class PlaqueGarage implements IPlaqueGarage {
  constructor(
    public id?: number,
    public numeroOrdre?: string,
    public numeroPlaque?: string,
    public codeQrPlaque?: string,
    public createdAt?: Moment,
    public carteWId?: number
  ) {}
}
