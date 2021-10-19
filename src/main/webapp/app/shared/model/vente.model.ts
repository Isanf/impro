import { Moment } from 'moment';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';
import { PersonnePhysique } from './personne-physique.model';
import { PersonneMorale } from './personne-morale.model';
import { DocIdentificationPP } from './doc-identification-pp.model';
import { DocIdentificationPM } from './doc-identification-pm.model';
import { Nation } from 'app/shared/model/nation.model';

export interface IVente {
  id?: number;
  dateVente?: Moment;
  numeroVente?: string;
  quantiteVendue?: number;
  personnePhysiqueId?: number;
  personneMoraleId?: number;
  personnePhysiqueDTO?: PersonnePhysique;
  personneMoraleDTO?: PersonneMorale;
  docIdentificationPPDTO?: DocIdentificationPP;
  docIdentificationPMDTO?: DocIdentificationPM;
  vehiculeDTOStock?: Vehicule;
  vehiculeDTO?: Vehicule;
  nationDTO?: Nation;
}

export class Vente implements IVente {
  constructor(
    public id?: number,
    public dateVente?: Moment,
    public numeroVente?: string,
    public quantiteVendue?: number,
    public personnePhysiqueId?: number,
    public personneMoraleId?: number,
    public personnePhysiqueDTO?: PersonnePhysique,
    public personneMoraleDTO?: PersonneMorale,
    public docIdentificationPPDTO?: DocIdentificationPP,
    public docIdentificationPMDTO?: DocIdentificationPM,
    public vehiculeDTOStock?: Vehicule,
    public vehiculeDTO?: Vehicule,
    public nationDTO?: Nation
  ) {}
}
