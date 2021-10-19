import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';

@Pipe({
  name: 'typeFilter'
})
export class TypeCategoFilter implements PipeTransform {
  transform(values: ITypeOrganisation[], search: string): ITypeOrganisation[] {
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
