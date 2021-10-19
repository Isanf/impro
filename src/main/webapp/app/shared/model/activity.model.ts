export interface IActivity {
  id?: number;
}

export class Activity implements IActivity {
  constructor(public id?: number) {}
}
