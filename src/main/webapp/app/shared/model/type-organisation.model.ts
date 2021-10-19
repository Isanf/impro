import { IOrganisation } from 'app/shared/model/organisation.model';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';

export interface ITypeOrganisation {
  id?: number;
  nom?: string;
  description?: string;
  niveau?: number;
  organisations?: IOrganisation[];
  categorieOrganisationId?: number;
  typeActeurs?: ITypeActeur[];
}

export class TypeOrganisation implements ITypeOrganisation {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public niveau?: number,
    public organisations?: IOrganisation[],
    public categorieOrganisationId?: number,
    public typeActeurs?: ITypeActeur[]
  ) {}
}
