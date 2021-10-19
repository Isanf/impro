import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { DocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { Organisation } from 'app/shared/model/organisation.model';

export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  passwordConfirm?: string;
  profilId?: number;
  personnePhysiqueDTO?: PersonnePhysique;
  docIdentificationPPDTO?: DocIdentificationPP;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string,
    public passwordConfirm?: string,
    public profilId?: number,
    public personnePhysiqueDTO?: PersonnePhysique,
    public docIdentificationPPDTO?: DocIdentificationPP,
    public organisationDTO?: Organisation
  ) {
    this.id = id ? id : null;
    this.login = login ? login : null;
    this.firstName = firstName ? firstName : null;
    this.lastName = lastName ? lastName : null;
    this.email = email ? email : null;
    this.activated = activated ? activated : false;
    this.langKey = langKey ? langKey : null;
    this.authorities = authorities ? authorities : null;
    this.createdBy = createdBy ? createdBy : null;
    this.createdDate = createdDate ? createdDate : null;
    this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
    this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
    this.password = password ? password : null;
    this.passwordConfirm = passwordConfirm ? passwordConfirm : null;
    this.profilId = profilId ? profilId : null;
    this.personnePhysiqueDTO = personnePhysiqueDTO ? personnePhysiqueDTO : null;
    this.docIdentificationPPDTO = docIdentificationPPDTO ? docIdentificationPPDTO : null;
    this.organisationDTO = organisationDTO ? organisationDTO : null;
  }
}
