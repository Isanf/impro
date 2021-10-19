import { Injectable, Pipe, PipeTransform } from '@angular/core';
import { IImmatriculation } from 'app/shared/model/immatriculation.model';

@Pipe({
  name: 'immatriculationFilter'
})
@Injectable({ providedIn: 'root' })
export class ImmatriculationFilter implements PipeTransform {
  transform(values: IImmatriculation[], search: string): IImmatriculation[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.dateImmatriculation
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
