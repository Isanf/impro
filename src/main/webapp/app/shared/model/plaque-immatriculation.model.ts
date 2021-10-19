export interface IPlaqueImmatriculation {
  id?: number;
  numeroSerie?: string;
  numeroImmatriculation?: string;
  codeQR?: string;
  certificatImmatriculationId?: number;
  vehiculeId?: number;
}

export class PlaqueImmatriculation implements IPlaqueImmatriculation {
  constructor(
    public id?: number,
    public numeroSerie?: string,
    public numeroImmatriculation?: string,
    public codeQR?: string,
    public certificatImmatriculationId?: number,
    public vehiculeId?: number
  ) {}
}
