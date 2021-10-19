import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

export interface ICertificatImmatriculation {
  id?: number;
  numero?: string;
  codeQr?: string;
  carnetASoucheId?: number;
}

export class CertificatImmatriculation implements ICertificatImmatriculation {
  constructor(public id?: number, public numero?: string, public codeQr?: string, public carnetASoucheId?: number) {}
}
