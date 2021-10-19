export interface IPrixCertificat {
  id?: number;
  prix?: number;
  activated?: boolean;
}

export class PrixCertificat implements IPrixCertificat {
  constructor(public id?: number, public prix?: number, public activated?: boolean) {
    this.activated = this.activated || false;
  }
}
