import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';

@Pipe({
  name: 'commandeFilter'
})
export class CommandeVehiculeFilter implements PipeTransform {
  transform(values: ICommandeVehicule[], search: string): ICommandeVehicule[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.dateCommande
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
