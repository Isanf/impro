import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';

export interface ITypeActeur {
  id?: number;
  nom?: string;
  description?: string;
  organisations?: IOrganisation[];
  typeOrganisations?: ITypeOrganisation[];
  organisationDTO?: IOrganisation;
}

export class TypeActeur implements ITypeActeur {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public organisations?: IOrganisation[],
    public typeOrganisations?: ITypeOrganisation[],
    public organisationDTO?: IOrganisation
  ) {}
}
