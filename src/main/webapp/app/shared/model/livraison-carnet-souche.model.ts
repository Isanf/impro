import { Moment } from 'moment';
import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

export interface ILivraisonCarnetSouche {
  id?: number;
  numeroLivraisonCS?: string;
  dateLivraison?: Moment;
  carnetASouches?: ICarnetASouche[];
  concessionnaireId?: number;
  infosId?: number;
  supernetId?: number;
  commandeCarnetSoucheId?: number;
  infoCommandeCarnetASoucheDTO?: IInfoCommandeCarnetASouche[];
  commandeCarnetSoucheDTO?: ICommandeCarnetSouche;
  carnetASoucheDTO?: ICarnetASouche[];
}

export class LivraisonCarnetSouche implements ILivraisonCarnetSouche {
  constructor(
    public id?: number,
    public numeroLivraisonCS?: string,
    public dateLivraison?: Moment,
    public carnetASouches?: ICarnetASouche[],
    public concessionnaireId?: number,
    public infosId?: number,
    public supernetId?: number,
    public commandeCarnetSoucheId?: number,
    public infoCommandeCarnetASoucheDTO?: IInfoCommandeCarnetASouche[],
    public commandeCarnetSoucheDTO?: ICommandeCarnetSouche,
    public carnetASoucheDTO?: ICarnetASouche[]
  ) {}
}
