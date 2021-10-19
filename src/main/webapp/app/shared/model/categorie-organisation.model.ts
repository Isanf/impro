import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeCategorieOrganisation } from 'app/shared/model/enumerations/type-categorie-organisation.model';

export interface ICategorieOrganisation {
  id?: number;
  libelle?: string;
  description?: string;
  typeCategorieOrganisation?: TypeCategorieOrganisation;
  types?: ITypeOrganisation[];
}

export class CategorieOrganisation implements ICategorieOrganisation {
  constructor(
    public id?: number,
    public libelle?: string,
    public description?: string,
    public typeCategorieOrganisation?: TypeCategorieOrganisation,
    public types?: ITypeOrganisation[]
  ) {}
}
