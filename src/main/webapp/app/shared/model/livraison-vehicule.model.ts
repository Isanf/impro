import { Moment } from 'moment';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';

export interface ILivraisonVehicule {
  id?: number;
  numeroLivraison?: string;
  dateLivraison?: Moment;
  vehiculeDTOS?: Vehicule[];
  revendeurId?: number;
  concessionnaireId?: number;
  commandeVehiculeId?: number;
}

export class LivraisonVehicule implements ILivraisonVehicule {
  constructor(
    public id?: number,
    public numeroLivraison?: string,
    public dateLivraison?: Moment,
    public vehiculeDTOS?: Vehicule[],
    public revendeurId?: number,
    public concessionnaireId?: number,
    public commandeVehiculeId?: number
  ) {}
}
