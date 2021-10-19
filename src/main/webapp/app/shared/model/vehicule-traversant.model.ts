import { Moment } from 'moment';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { Nation } from 'app/shared/model/nation.model';

export interface IVehiculeTraversant {
  id?: number;
  chassis?: string;
  marque?: string;
  model?: string;
  dateEntre?: Moment;
  dateSortie?: Moment;
  provenance?: string;
  destination?: string;
  createdAt?: Moment;
  personnePhysiqueId?: number;
  personneMoraleId?: number;
  personnePhysiqueDTO?: PersonnePhysique;
  personneMoraleDTO?: PersonneMorale;
  docIdentificationPPDTO?: DocIdentificationPP;
  docIdentificationPMDTO?: DocIdentificationPM;
  nationDTO?: Nation;
}

export class VehiculeTraversant implements IVehiculeTraversant {
  constructor(
    public id?: number,
    public chassis?: string,
    public marque?: string,
    public model?: string,
    public dateEntre?: Moment,
    public dateSortie?: Moment,
    public provenance?: string,
    public destination?: string,
    public createdAt?: Moment,
    public personnePhysiqueId?: number,
    public personneMoraleId?: number,
    public personnePhysiqueDTO?: PersonnePhysique,
    public personneMoraleDTO?: PersonneMorale,
    public docIdentificationPPDTO?: DocIdentificationPP,
    public docIdentificationPMDTO?: DocIdentificationPM,
    public nationDTO?: Nation
  ) {}
}
