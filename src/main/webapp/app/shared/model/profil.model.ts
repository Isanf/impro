import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';

export interface IProfil {
  id?: number;
  nom?: string;
  description?: string;
  personnePhysiques?: IPersonnePhysique[];
  organisationId?: number;
  roles?: string[];
}

export class Profil implements IProfil {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public personnePhysiques?: IPersonnePhysique[],
    public organisationId?: number,
    public roles?: string[]
  ) {}
}
