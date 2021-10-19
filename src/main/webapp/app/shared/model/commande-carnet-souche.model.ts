import { Moment } from 'moment';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';

export interface ICommandeCarnetSouche {
  id?: number;
  numeroCommandeCS?: string;
  dateCommandeCS?: Moment;
  typePaiement?: string;
  estValide?: boolean;
  estTraitee?: boolean;
  estLivree?: boolean;
  prixCommande?: string;
  livraisonCarnetSouches?: ILivraisonCarnetSouche[];
  infoCommandeCarnetASouches?: IInfoCommandeCarnetASouche[];
  typeCarnetDTOS?: ITypeCarnet[];
  concessionnaireId?: number;
  organisationDTO?: IOrganisation;
  supernetId?: number;
  delaiLivraison?: string;
}

export class CommandeCarnetSouche implements ICommandeCarnetSouche {
  constructor(
    public id?: number,
    public numeroCommandeCS?: string,
    public dateCommandeCS?: Moment,
    public typePaiement?: string,
    public estValide?: boolean,
    public estTraitee?: boolean,
    public estLivree?: boolean,
    public prixCommande?: string,
    public livraisonCarnetSouches?: ILivraisonCarnetSouche[],
    public infoCommandeCarnetASouches?: IInfoCommandeCarnetASouche[],
    public typeCarnetDTOS?: ITypeCarnet[],
    public concessionnaireId?: number,
    organisationDTO?: IOrganisation,
    public supernetId?: number,
    public delaiLivraison?: string
  ) {
    this.estValide = this.estValide || false;
    this.estTraitee = this.estTraitee || false;
    this.estLivree = this.estLivree || false;
  }
}
