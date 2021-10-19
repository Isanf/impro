import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

@Pipe({
  name: 'plaqueFilter'
})
export class PlaqueFilter implements PipeTransform {
  transform(values: IPlaqueImmatriculation[], search: string): IPlaqueImmatriculation[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.numeroImmatriculation
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
