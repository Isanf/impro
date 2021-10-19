import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

@Pipe({
  name: 'commandeFilter'
})
export class CommandeCarnetSoucheFilter implements PipeTransform {
  transform(values: ICommandeCarnetSouche[], search: string): ICommandeCarnetSouche[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.dateCommandeCS
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
