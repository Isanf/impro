import { Moment } from 'moment';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { Nation } from 'app/shared/model/nation.model';
import { CarteW } from 'app/shared/model/carte-w.model';

export interface IVehiculeOccasional {
  id?: number;
  chassis?: string;
  marque?: string;
  model?: string;
  createdAt?: Moment;
  personnePhysiqueId?: number;
  personneMoraleId?: number;
  carteWId?: number;
  personnePhysiqueDTO?: PersonnePhysique;
  personneMoraleDTO?: PersonneMorale;
  docIdentificationPPDTO?: DocIdentificationPP;
  docIdentificationPMDTO?: DocIdentificationPM;
  nationDTO?: Nation;
  carteWDTO?: CarteW;
}

export class VehiculeOccasional implements IVehiculeOccasional {
  constructor(
    public id?: number,
    public chassis?: string,
    public marque?: string,
    public model?: string,
    public createdAt?: Moment,
    public personnePhysiqueId?: number,
    public personneMoraleId?: number,
    public carteWId?: number,
    public personnePhysiqueDTO?: PersonnePhysique,
    public personneMoraleDTO?: PersonneMorale,
    public docIdentificationPPDTO?: DocIdentificationPP,
    public docIdentificationPMDTO?: DocIdentificationPM,
    public nationDTO?: Nation,
    public carteWDTO?: CarteW
  ) {}
}
