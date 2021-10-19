import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';

@Pipe({
  name: 'acteurFilter'
})
export class ActeurFilter implements PipeTransform {
  transform(values: ITypeActeur[], search: string): ITypeActeur[] {
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
