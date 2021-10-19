import { Moment } from 'moment';
import { CommandeVehicule } from 'app/shared/model/commande-vehicule.model';

export interface IInfoCommandeVehicule {
  id?: number;
  numeroCommande?: string;
  dateCommande?: Moment;
  quantiteCommande?: number;
  commandeVehiculeId?: number;
  marqueVehiculeId?: number;
  /* commandeVehiculeDTO?: CommandeVehicule;*/
}

export class InfoCommandeVehicule implements IInfoCommandeVehicule {
  constructor(
    public id?: number,
    public numeroCommande?: string,
    public dateCommande?: Moment,
    public quantiteCommande?: number,
    public commandeVehiculeId?: number,
    public marqueVehiculeId?: any /*public commandeVehiculeDTO?: CommandeVehicule*/
  ) {}
}
