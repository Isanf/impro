import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

@Pipe({
  name: 'organisationFilter'
})
export class OrganisationFilter implements PipeTransform {
  transform(values: IOrganisation[], search: string): IOrganisation[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.nom
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
