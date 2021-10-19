import { ITypeCarnet } from 'app/shared/model/type-carnet.model';
import { IVehicule } from 'app/shared/model/vehicule.model';

export interface ITypeVehicule {
  id?: number;
  code?: string;
  libelle?: string;
  nombrePlaque?: number;
  estCycleMoteur?: boolean;
  typeCarnets?: ITypeCarnet[];
  vehicules?: IVehicule[];
  fichierType?: any;
}

export class TypeVehicule implements ITypeVehicule {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public nombrePlaque?: number,
    public estCycleMoteur?: boolean,
    public typeCarnets?: ITypeCarnet[],
    public vehicules?: IVehicule[],
    public fichierType?: any
  ) {
    this.estCycleMoteur = this.estCycleMoteur || false;
  }
}
