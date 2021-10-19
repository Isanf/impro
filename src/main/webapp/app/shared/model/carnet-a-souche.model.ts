import { Moment } from 'moment';
import { ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

export interface ICarnetASouche {
  id?: number;
  numero?: string;
  dateImpression?: Moment;
  certificatImmatriculations?: ICertificatImmatriculation[];
  concessionnaireId?: number;
  livraisonCarnetSoucheId?: number;
  typeCarnetId?: number;
  typeCarnetDTO?: ITypeCarnet;
  organisationDTO?: IOrganisation;
  livraisonCarnetSoucheDTO?: ILivraisonCarnetSouche;
}

export class CarnetASouche implements ICarnetASouche {
  constructor(
    public id?: number,
    public numero?: string,
    public dateImpression?: Moment,
    public certificatImmatriculations?: ICertificatImmatriculation[],
    public concessionnaireId?: number,
    public livraisonCarnetSoucheId?: number,
    public typeCarnetId?: number,
    public typeCarnetDTO?: ITypeCarnet,
    public organisationDTO?: IOrganisation,
    public livraisonCarnetSoucheDTO?: ILivraisonCarnetSouche
  ) {}
}
