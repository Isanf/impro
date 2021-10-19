import { Moment } from 'moment';
import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';
import { IPersonneMorale } from 'app/shared/model/personne-morale.model';
import { IVehicule } from 'app/shared/model/vehicule.model';

export interface IImmatriculation {
  id?: number;
  numero?: string;
  dateImmatriculation?: Moment;
  certificatImmatriculationId?: number;
  organisationId?: number;
  personnePhysiqueId?: number;
  personneMoraleId?: number;
  vehiculeId?: number;
  personnePhysiqueDTO?: IPersonnePhysique;
  personneMoraleDTO?: IPersonneMorale;
  vehiculeDTO?: IVehicule;
}

export class Immatriculation implements IImmatriculation {
  constructor(
    public id?: number,
    public numero?: string,
    public dateImmatriculation?: Moment,
    public certificatImmatriculationId?: number,
    public organisationId?: number,
    public personnePhysiqueId?: number,
    public personneMoraleId?: number,
    public vehiculeId?: number,
    public personnePhysiqueDTO?: IPersonnePhysique,
    public personneMoraleDTO?: IPersonneMorale,
    public vehiculeDTO?: IVehicule
  ) {}
}
