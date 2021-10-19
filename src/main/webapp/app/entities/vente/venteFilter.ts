import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { IVente } from 'app/shared/model/vente.model';

@Pipe({
  name: 'venteFilter'
})
export class VenteFilter implements PipeTransform {
  transform(values: IVente[], search: string): IVente[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.dateVente
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
