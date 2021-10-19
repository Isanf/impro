import { Pipe, PipeTransform } from '@angular/core';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICollaboration } from 'app/shared/model/collaboration.model';
import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';

@Pipe({
  name: 'carnetFilter'
})
export class CarnetFilter implements PipeTransform {
  transform(values: ICarnetASouche[], search: string): ICarnetASouche[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.numero
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
