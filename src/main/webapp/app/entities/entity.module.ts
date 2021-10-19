import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'certificat-immatriculation',
        loadChildren: () =>
          import('./certificat-immatriculation/certificat-immatriculation.module').then(m => m.ImproCertificatImmatriculationModule)
      },
      {
        path: 'immatriculation',
        loadChildren: () => import('./immatriculation/immatriculation.module').then(m => m.ImproImmatriculationModule)
      },
      {
        path: 'carnet-a-souche',
        loadChildren: () => import('./carnet-a-souche/carnet-a-souche.module').then(m => m.ImproCarnetASoucheModule)
      },
      {
        path: 'carte-w',
        loadChildren: () => import('./carte-w/carte-w.module').then(m => m.ImproCarteWModule)
      },
      {
        path: 'personne-physique',
        loadChildren: () => import('./personne-physique/personne-physique.module').then(m => m.ImproPersonnePhysiqueModule)
      },
      {
        path: 'doc-identification-pp',
        loadChildren: () => import('./doc-identification-pp/doc-identification-pp.module').then(m => m.ImproDocIdentificationPPModule)
      },
      {
        path: 'personne-morale',
        loadChildren: () => import('./personne-morale/personne-morale.module').then(m => m.ImproPersonneMoraleModule)
      },
      {
        path: 'doc-identification-pm',
        loadChildren: () => import('./doc-identification-pm/doc-identification-pm.module').then(m => m.ImproDocIdentificationPMModule)
      },
      {
        path: 'vente',
        loadChildren: () => import('./vente/vente.module').then(m => m.ImproVenteModule)
      },
      {
        path: 'pose-plaque',
        loadChildren: () => import('./pose-plaque/pose-plaque.module').then(m => m.ImproPosePlaqueModule)
      },
      {
        path: 'vehicule',
        loadChildren: () => import('./vehicule/vehicule.module').then(m => m.ImproVehiculeModule)
      },
      {
        path: 'collaboration',
        loadChildren: () => import('./collaboration/collaboration.module').then(m => m.ImproCollaborationModule)
      },
      {
        path: 'commande-vehicule',
        loadChildren: () => import('./commande-vehicule/commande-vehicule.module').then(m => m.ImproCommandeVehiculeModule)
      },
      {
        path: 'stock',
        loadChildren: () => import('./stock/stock.module').then(m => m.ImproStockModule)
      },
      {
        path: 'livraison-vehicule',
        loadChildren: () => import('./livraison-vehicule/livraison-vehicule.module').then(m => m.ImproLivraisonVehiculeModule)
      },
      {
        path: 'livraison-carnet-souche',
        loadChildren: () => import('./livraison-carnet-souche/livraison-carnet-souche.module').then(m => m.ImproLivraisonCarnetSoucheModule)
      },
      {
        path: 'commande-carnet-souche',
        loadChildren: () => import('./commande-carnet-souche/commande-carnet-souche.module').then(m => m.ImproCommandeCarnetSoucheModule)
      },
      {
        path: 'categorie-organisation',
        loadChildren: () => import('./categorie-organisation/categorie-organisation.module').then(m => m.ImproCategorieOrganisationModule)
      },
      {
        path: 'type-organisation',
        loadChildren: () => import('./type-organisation/type-organisation.module').then(m => m.ImproTypeOrganisationModule)
      },
      {
        path: 'organisation',
        loadChildren: () => import('./organisation/organisation.module').then(m => m.ImproOrganisationModule)
      },
      {
        path: 'type-acteur',
        loadChildren: () => import('./type-acteur/type-acteur.module').then(m => m.ImproTypeActeurModule)
      },
      {
        path: 'profil',
        loadChildren: () => import('./profil/profil.module').then(m => m.ImproProfilModule)
      },
      {
        path: 'type-vehicule',
        loadChildren: () => import('./type-vehicule/type-vehicule.module').then(m => m.ImproTypeVehiculeModule)
      },
      {
        path: 'marque-vehicule',
        loadChildren: () => import('./marque-vehicule/marque-vehicule.module').then(m => m.ImproMarqueVehiculeModule)
      },
      {
        path: 'plaque-immatriculation',
        loadChildren: () => import('./plaque-immatriculation/plaque-immatriculation.module').then(m => m.ImproPlaqueImmatriculationModule)
      },
      {
        path: 'type-carnet',
        loadChildren: () => import('./type-carnet/type-carnet.module').then(m => m.ImproTypeCarnetModule)
      },
      {
        path: 'info-commande-vehicule',
        loadChildren: () => import('./info-commande-vehicule/info-commande-vehicule.module').then(m => m.ImproInfoCommandeVehiculeModule)
      },
      {
        path: 'info-commande-carnet-a-souche',
        loadChildren: () =>
          import('./info-commande-carnet-a-souche/info-commande-carnet-a-souche.module').then(m => m.ImproInfoCommandeCarnetASoucheModule)
      },
      {
        path: 'organisation-localite',
        loadChildren: () => import('./organisation-localite/organisation-localite.module').then(m => m.ImproOrganisationLocaliteModule)
      },
      {
        path: 'firstlogin',
        loadChildren: () => import('./firstlogin/firstlogin.module').then(m => m.ImproFirstloginModule)
      },
      {
        path: 'log-activity',
        loadChildren: () => import('./log-activity/log-activity.module').then(m => m.ImproLogActivityModule)
      },
      {
        path: 'vehicule-occasion',
        loadChildren: () => import('./vehicule-occasion/vehicule-occasion.module').then(m => m.ImproVehiculeOccasionModule)
      },
      {
        path: 'statistique',
        loadChildren: () => import('./statistique/statistique.module').then(m => m.ImproStatistiqueModule)
      },
      {
        path: 'plaque-garage',
        loadChildren: () => import('./plaque-garage/plaque-garage.module').then(m => m.ImproPlaqueGarageModule)
      },
      {
        path: 'prix-certificat',
        loadChildren: () => import('./prix-certificat/prix-certificat.module').then(m => m.ImproPrixCertificatModule)
      },
      {
        path: 'user-otp',
        loadChildren: () => import('./user-otp/user-otp.module').then(m => m.ImproUserOtpModule)
      },
      {
        path: 'user-device-id',
        loadChildren: () => import('./user-device-id/user-device-id.module').then(m => m.ImproUserDeviceIdModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ImproEntityModule {}
