import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

@Pipe({
  name: 'lcsFilter'
})
export class LivraisonCSFilter implements PipeTransform {
  transform(values: ILivraisonCarnetSouche[], search: string): ILivraisonCarnetSouche[] {
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
