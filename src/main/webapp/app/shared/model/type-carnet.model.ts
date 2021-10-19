import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

export interface ITypeCarnet {
  id?: number;
  code?: string;
  libelle?: string;
  quantiteCertificat?: number;
  carnetSouches?: ICarnetASouche[];
  infoCommandeCarnetASouches?: IInfoCommandeCarnetASouche[];
  typeVehiculeId?: number;
}

export class TypeCarnet implements ITypeCarnet {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public quantiteCertificat?: number,
    public carnetSouches?: ICarnetASouche[],
    public infoCommandeCarnetASouches?: IInfoCommandeCarnetASouche[],
    public typeVehiculeId?: number
  ) {}
}
