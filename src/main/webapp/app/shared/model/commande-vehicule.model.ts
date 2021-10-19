import { Moment } from 'moment';
import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';
import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface ICommandeVehicule {
  id?: number;
  numeroCommandeVehicule?: string;
  dateCommande?: Moment;
  estLivree?: boolean;
  revendeurId?: number;
  concessionnaireId?: number;
  infoCommandeVehiculeDTO?: IInfoCommandeVehicule[];
  organisationDTO?: IOrganisation;
}

export class CommandeVehicule implements ICommandeVehicule {
  constructor(
    public id?: number,
    public numeroCommandeVehicule?: string,
    public dateCommande?: Moment,
    public estLivree?: boolean,
    public revendeurId?: number,
    public concessionnaireId?: number,
    public infoCommandeVehiculeDTO?: IInfoCommandeVehicule[],
    organisationDTO?: IOrganisation
  ) {
    this.estLivree = this.estLivree || false;
  }
}
