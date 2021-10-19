import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';

@Pipe({
  name: 'typeCarnetFilter'
})
export class TypeCarnetFilter implements PipeTransform {
  transform(values: ITypeCarnet[], search: string): ITypeCarnet[] {
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
