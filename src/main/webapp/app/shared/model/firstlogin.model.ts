export interface IFirstlogin {
  id?: number;
  passe?: boolean;
  userLogin?: string;
  userId?: number;
}

export class Firstlogin implements IFirstlogin {
  constructor(public id?: number, public passe?: boolean, public userLogin?: string, public userId?: number) {
    this.passe = this.passe || false;
  }
}
