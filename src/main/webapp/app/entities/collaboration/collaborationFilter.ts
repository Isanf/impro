import { Pipe, PipeTransform } from '@angular/core';
import { ICollaboration } from 'app/shared/model/collaboration.model';

@Pipe({
  name: 'collaborationFilter'
})
export class CollaborationFilter implements PipeTransform {
  transform(values: ICollaboration[], search: string): ICollaboration[] {
    if (!values || !search) {
      return values;
    }
    return values.filter(val =>
      val.revendeurs.nom
        .toString()
        .toLocaleLowerCase()
        .includes(search.toLocaleLowerCase())
    );
  }
}
