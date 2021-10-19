import { Moment } from 'moment';

export interface IDatesModel {
  dateDebut?: Date;
  dateFin?: Date;
}

export class DatesModel implements IDatesModel {
  constructor(public dateDebut?: Date, public dateFin?: Date) {}
}
