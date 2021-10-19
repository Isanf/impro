import { Moment } from 'moment';

export interface ILogActivity {
  id?: number;
  principal?: string;
  url?: string;
  action?: string;
  ip?: string;
  dateAction?: Moment;
}

export class LogActivity implements ILogActivity {
  constructor(
    public id?: number,
    public principal?: string,
    public url?: string,
    public action?: string,
    public ip?: string,
    public dateAction?: Moment
  ) {}
}
