export interface IDocIdentificationPM {
  id?: number;
  numero?: string;
  numeroIFU?: string;
  numeroRCCM?: string;
  telephone?: string;
  siegeSocial?: string;
  codePostal?: string;
  email?: string;
  organisationId?: number;
  personneMoraleId?: number;
}

export class DocIdentificationPM implements IDocIdentificationPM {
  constructor(
    public id?: number,
    public numero?: string,
    public numeroIFU?: string,
    public numeroRCCM?: string,
    public telephone?: string,
    public siegeSocial?: string,
    public codePostal?: string,
    public email?: string,
    public organisationId?: number,
    public personneMoraleId?: number
  ) {}
}
