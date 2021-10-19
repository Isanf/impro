import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

@Pipe({
  name: 'livraisonVehiculeFilter'
})
export class LivraisonVehiculeFilter implements PipeTransform {
  transform(values: ILivraisonVehicule[], search: string): ILivraisonVehicule[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.dateLivraison
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
