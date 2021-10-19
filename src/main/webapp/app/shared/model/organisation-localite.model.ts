import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IOrganisationLocalite {
  id?: number;
  code?: string;
  nom?: string;
  description?: string;
  organisations?: IOrganisation[];
}

export class OrganisationLocalite implements IOrganisationLocalite {
  constructor(
    public id?: number,
    public code?: string,
    public nom?: string,
    public description?: string,
    public organisations?: IOrganisation[]
  ) {}
}
