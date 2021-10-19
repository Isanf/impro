import { Moment } from 'moment';
import { IImmatriculation } from 'app/shared/model/immatriculation.model';
import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { Stock } from 'app/shared/model/stock.model';
import { TypeVehicule } from 'app/shared/model/type-vehicule.model';
import { MarqueVehicule } from 'app/shared/model/marque-vehicule.model';

export interface IVehicule {
  id?: number;
  numeroChassis?: string;
  types?: string;
  model?: string;
  energie?: string;
  puissanceReel?: string;
  puissanceAdmin?: string;
  couleur?: string;
  poidsVide?: number;
  chargeUtile?: number;
  ptac?: number;
  ptra?: number;
  nbrPlace?: number;
  capacite?: number;
  dateMiseCirculation?: Moment;
  regime?: string;
  noDedouanement?: string;
  dateDedouanement?: Moment;
  immatriculations?: IImmatriculation[];
  plaqueImmatriculations?: IPlaqueImmatriculation[];
  livraisonVehiculeId?: number;
  typeVehiculeId?: number;
  marqueVehiculeId?: number;
  venteId?: number;
  stockId?: number;
  stockDTO?: Stock;
  typeVehiculeDTO?: TypeVehicule;
  marqueVehiculeDTO?: MarqueVehicule;
}

export class Vehicule implements IVehicule {
  constructor(
    public id?: number,
    public numeroChassis?: string,
    public types?: string,
    public model?: string,
    public energie?: string,
    public puissanceReel?: string,
    public puissanceAdmin?: string,
    public couleur?: string,
    public poidsVide?: number,
    public chargeUtile?: number,
    public ptac?: number,
    public ptra?: number,
    public nbrPlace?: number,
    public capacite?: number,
    public dateMiseCirculation?: Moment,
    public regime?: string,
    public noDedouanement?: string,
    public dateDedouanement?: Moment,
    public immatriculations?: IImmatriculation[],
    public plaqueImmatriculations?: IPlaqueImmatriculation[],
    public livraisonVehiculeId?: number,
    public typeVehiculeId?: number,
    public marqueVehiculeId?: number,
    public venteId?: number,
    public stockId?: number,
    public typeVehiculeDTO?: TypeVehicule,
    public marqueVehiculeDTO?: MarqueVehicule
  ) {}
}
