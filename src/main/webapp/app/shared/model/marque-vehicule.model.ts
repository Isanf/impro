import { IVehicule } from 'app/shared/model/vehicule.model';
import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

export interface IMarqueVehicule {
  id?: number;
  code?: string;
  libelle?: string;
  vehicules?: IVehicule[];
  infoCommandeVehicules?: IInfoCommandeVehicule[];
  fichierMarque?: any;
}

export class MarqueVehicule implements IMarqueVehicule {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public vehicules?: IVehicule[],
    public infoCommandeVehicules?: IInfoCommandeVehicule[],
    fichierMarque?: any
  ) {}
}
