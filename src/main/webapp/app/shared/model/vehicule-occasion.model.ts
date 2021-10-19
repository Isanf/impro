import { Moment } from 'moment';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';

export interface IVehiculeOccasion {
  id?: number;
  chassis?: string;
  marque?: string;
  model?: string;
  nomPrenom?: string;
  telephone?: string;
  carteW?: string;
  createdAt?: Moment;
  numeroCNIB?: string;
  organisation?: IOrganisation;
  personnePhysiqueDTO?: PersonnePhysique;
  personneMoraleDTO?: PersonneMorale;
  docIdentificationPPDTO?: DocIdentificationPP;
  docIdentificationPMDTO?: DocIdentificationPM;
}

export class VehiculeOccasion implements IVehiculeOccasion {
  constructor(
    public id?: number,
    public chassis?: string,
    public marque?: string,
    public model?: string,
    public nomPrenom?: string,
    public telephone?: string,
    public carteW?: string,
    public createdAt?: Moment,
    public numeroCNIB?: string,
    public organisation?: IOrganisation,
    public personnePhysiqueDTO?: PersonnePhysique,
    public personneMoraleDTO?: PersonneMorale,
    public docIdentificationPPDTO?: DocIdentificationPP,
    public docIdentificationPMDTO?: DocIdentificationPM
  ) {}
}
