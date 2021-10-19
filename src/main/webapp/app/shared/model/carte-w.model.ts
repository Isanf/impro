import { Moment } from 'moment';
import { Organisation } from 'app/shared/model/organisation.model';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { Profil } from 'app/shared/model/profil.model';
import { User } from 'app/core/user/user.model';

export interface ICarteW {
  id?: number;
  numeroCarteW?: string;
  dateEtablissementCarteW?: Moment;
  dateExpirationCarteW?: Moment;
  lieuEtablissement?: string;
  organisationId?: number;
  organisationDTO?: Organisation;
  docIdentificationPMDTO?: DocIdentificationPM;
  personnePhysiqueDTO?: PersonnePhysique;
  docIdentificationPPDTO?: DocIdentificationPP;
  profilDTO?: Profil;
  userDTO?: User;
}

export class CarteW implements ICarteW {
  constructor(
    public id?: number,
    public numeroCarteW?: string,
    public dateEtablissementCarteW?: Moment,
    public dateExpirationCarteW?: Moment,
    public lieuEtablissement?: string,
    public organisationId?: number,
    public organisationDTO?: Organisation,
    public docIdentificationPMDTO?: DocIdentificationPM,
    public personnePhysiqueDTO?: PersonnePhysique,
    public docIdentificationPPDTO?: DocIdentificationPP,
    public profilDTO?: Profil,
    public userDTO?: User
  ) {}
}
