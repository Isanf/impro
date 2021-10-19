import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

@Pipe({
  name: 'categorieFilter'
})
export class CategorieFilter implements PipeTransform {
  transform(values: ICategorieOrganisation[], search: string): ICategorieOrganisation[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.libelle
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
