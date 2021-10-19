import { Moment } from 'moment';
import { IVehicule } from 'app/shared/model/vehicule.model';

export interface IStock {
  id?: number;
  numeroStock?: string;
  fichierStockContentType?: string;
  fichierStock?: any;
  dateStock?: Moment;
  vehicules?: IVehicule[];
  concessionnaireId?: number;
}

export class Stock implements IStock {
  constructor(
    public id?: number,
    public numeroStock?: string,
    public fichierStockContentType?: string,
    public fichierStock?: any,
    public dateStock?: Moment,
    public vehicules?: IVehicule[],
    public concessionnaireId?: number
  ) {}
}
