import { Moment } from 'moment';
import { IOrganisation, Organisation } from './organisation.model';

export interface ICollaboration {
  id?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
  numeroCollaboration?: string;
  revendeurId?: number;
  concessionnaireId?: number;
  concessionnaires?: IOrganisation;
  revendeurs?: IOrganisation;
}

export class Collaboration implements ICollaboration {
  constructor(
    public id?: number,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public numeroCollaboration?: string,
    public revendeurId?: number,
    public concessionnaireId?: number,
    public concessionnaires?: IOrganisation,
    public revendeurs?: IOrganisation
  ) {}
}
