import { Moment } from 'moment';

export interface IPosePlaque {
  id?: number;
  numeroPose?: string;
  datePosePlaque?: Moment;
  revendeurId?: number;
}

export class PosePlaque implements IPosePlaque {
  constructor(public id?: number, public numeroPose?: string, public datePosePlaque?: Moment, public revendeurId?: number) {}
}
