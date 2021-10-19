export interface IStatistique {
  id?: number;
  nom?: string;
}

export class Statistique implements IStatistique {
  constructor(public id?: number, public nom?: string) {}
}
