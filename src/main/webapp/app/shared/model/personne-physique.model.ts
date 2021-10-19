import { Moment } from 'moment';
import { IImmatriculation } from 'app/shared/model/immatriculation.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IVente } from 'app/shared/model/vente.model';

export interface IPersonnePhysique {
  id?: number;
  nom?: string;
  prenom?: string;
  dateNaissance?: Moment;
  lieuNaissance?: string;
  telephone?: string;
  residence?: string;
  gerant?: boolean;
  docIdentificationId?: number;
  userId?: number;
  immatriculations?: IImmatriculation[];
  organisations?: IOrganisation[];
  ventes?: IVente[];
  organisationId?: number;
  profilId?: number;
}

export class PersonnePhysique implements IPersonnePhysique {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public dateNaissance?: Moment,
    public lieuNaissance?: string,
    public telephone?: string,
    public residence?: string,
    public gerant?: boolean,
    public docIdentificationId?: number,
    public userId?: number,
    public immatriculations?: IImmatriculation[],
    public organisations?: IOrganisation[],
    public ventes?: IVente[],
    public organisationId?: number,
    public profilId?: number
  ) {}
}
