import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { ICollaboration } from 'app/shared/model/collaboration.model';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { IImmatriculation } from 'app/shared/model/immatriculation.model';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';
import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';
import { IPosePlaque } from 'app/shared/model/pose-plaque.model';
import { IProfil } from 'app/shared/model/profil.model';
import { IStock } from 'app/shared/model/stock.model';
import { IVente } from 'app/shared/model/vente.model';
import { DocIdentificationPM, IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { IOrganisationLocalite, OrganisationLocalite } from 'app/shared/model/organisation-localite.model';

export interface IOrganisation {
  id?: number;
  nom?: string;
  description?: string;
  numeroOrdre?: number;
  carnetASouches?: ICarnetASouche[];
  collaborationsRevendeurs?: ICollaboration[];
  collaborationsConcessionnaires?: ICollaboration[];
  commandeCSConcessionnaires?: ICommandeCarnetSouche[];
  commandeCSRevendeurs?: ICommandeCarnetSouche[];
  commandeVRevendeurs?: ICommandeVehicule[];
  commandeVConcessionnaires?: ICommandeVehicule[];
  immatriculations?: IImmatriculation[];
  livraisonCSConcessionnaires?: ILivraisonCarnetSouche[];
  livraisonCSSupernets?: ILivraisonCarnetSouche[];
  livraisonVRevendeurs?: ILivraisonVehicule[];
  livraisonVConcessionnaires?: ILivraisonVehicule[];
  fils?: IOrganisation[];
  personnePhysiques?: IPersonnePhysique[];
  posePlaques?: IPosePlaque[];
  profils?: IProfil[];
  stocks?: IStock[];
  ventes?: IVente[];
  docIdPM?: IDocIdentificationPM;
  organisationLocaliteId?: number;
  pereId?: number;
  typeOrganisationId?: number;
  gerantId?: number;
  typeActeurId?: number;
  docIdentificationPMDTO?: DocIdentificationPM;
  numeroPhone?: string;
  fichierSign?: any;
  fichierLogo?: any;
  fichierSignContentType?: string;
  fichierLogoContentType?: string;
  organisationlocaliteDTO?: OrganisationLocalite;
  acteur?: string;
}

export class Organisation implements IOrganisation {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public numeroOrdre?: number,
    public carnetASouches?: ICarnetASouche[],
    public collaborationsRevendeurs?: ICollaboration[],
    public collaborationsConcessionnaires?: ICollaboration[],
    public commandeCSConcessionnaires?: ICommandeCarnetSouche[],
    public commandeCSRevendeurs?: ICommandeCarnetSouche[],
    public commandeVRevendeurs?: ICommandeVehicule[],
    public commandeVConcessionnaires?: ICommandeVehicule[],
    public immatriculations?: IImmatriculation[],
    public livraisonCSConcessionnaires?: ILivraisonCarnetSouche[],
    public livraisonCSSupernets?: ILivraisonCarnetSouche[],
    public livraisonVRevendeurs?: ILivraisonVehicule[],
    public livraisonVConcessionnaires?: ILivraisonVehicule[],
    public fils?: IOrganisation[],
    public personnePhysiques?: IPersonnePhysique[],
    public posePlaques?: IPosePlaque[],
    public profils?: IProfil[],
    public stocks?: IStock[],
    public ventes?: IVente[],
    public docIdPM?: IDocIdentificationPM,
    public organisationLocaliteId?: number,
    public pereId?: number,
    public typeOrganisationId?: number,
    public gerantId?: number,
    public typeActeurId?: number,
    public docIdentificationPMDTO?: DocIdentificationPM,
    public numeroPhone?: string,
    public fichierSign?: any,
    public fichierLogo?: any,
    public fichierSignContentType?: string,
    public fichierLogoContentType?: string,
    public organisationlocaliteDTO?: OrganisationLocalite,
    public acteur?: string
  ) {}
}
