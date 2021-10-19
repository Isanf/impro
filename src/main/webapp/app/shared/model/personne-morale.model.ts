import { Moment } from 'moment';
import { IImmatriculation } from 'app/shared/model/immatriculation.model';
import { IVente } from 'app/shared/model/vente.model';

export interface IPersonneMorale {
  id?: number;
  numeroIFU?: string;
  denomination?: string;
  dateCreate?: Moment;
  telephone?: string;
  immatriculations?: IImmatriculation[];
  ventes?: IVente[];
}

export class PersonneMorale implements IPersonneMorale {
  constructor(
    public id?: number,
    public numeroIFU?: string,
    public denomination?: string,
    public dateCreate?: Moment,
    public telephone?: string,
    public immatriculations?: IImmatriculation[],
    public ventes?: IVente[]
  ) {}
}
