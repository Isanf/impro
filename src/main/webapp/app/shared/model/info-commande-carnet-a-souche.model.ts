import { Moment } from 'moment';

export interface IInfoCommandeCarnetASouche {
  id?: number;
  numeroCommande?: string;
  dateCommande?: Moment;
  quantiteCommande?: number;
  estDeliver?: boolean;
  estTransiter?: boolean;
  commandeCarnetSoucheId?: number;
  typeCarnetId?: number;
  libelle?: string;
}

export class InfoCommandeCarnetASouche implements IInfoCommandeCarnetASouche {
  constructor(
    public id?: number,
    public numeroCommande?: string,
    public dateCommande?: Moment,
    public quantiteCommande?: number,
    public estDeliver?: boolean,
    public estTransiter?: boolean,
    public commandeCarnetSoucheId?: number,
    public typeCarnetId?: number,
    public libelle?: string
  ) {
    this.estDeliver = this.estDeliver || false;
    this.estTransiter = this.estTransiter || false;
  }
}
