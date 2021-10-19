import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';

@Pipe({
  name: 'typeVehiculeFilter'
})
export class TyVehiculeFilter implements PipeTransform {
  transform(values: ITypeVehicule[], search: string): ITypeVehicule[] {
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
