import { NbMenuItem } from '@nebular/theme';

export const CONS_MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'ACCUEIL',
    icon: 'home-outline',
    link: '/'
  },
  {
    title: 'GESTION DES VEHICULES',
    icon: 'car-outline',
    children: [
      {
        title: 'Commandes Véhicules reçues',
        link: '/commande-vehicule'
      },
      {
        title: 'Livraison vehicules',
        link: '/livraison-vehicule'
      },
      {
        title: 'Ventes',
        link: '/vente'
      },
      {
        title: 'Stock véhicules',
        link: '/stock'
      },
      {
        title: 'Véhicule',
        link: '/vehicule'
      }
    ]
  },
  {
    title: 'IMMATRICULATIONS',
    icon: 'file-text-outline',
    children: [
      {
        title: 'Carnet à souche',
        link: '/carnet-a-souche'
      },
      {
        title: 'Commande de Carnet',
        link: '/commande-carnet-souche'
      },
      {
        title: 'Immatriculation',
        link: '/immatriculation'
      }
    ]
  },
  {
    title: 'REVENDEURS',
    icon: 'grid-outline',
    children: [
      {
        title: 'Collaborations',
        link: 'collaboration'
      },
      {
        title: 'Revendeurs',
        link: 'organisation'
      }
    ]
  },
  {
    title: 'GESTION DES UTILISATEURS',
    icon: 'people-outline',
    children: [
      {
        title: 'Profil',
        link: 'profil'
      },
      {
        title: 'Utilisateurs',
        link: 'admin/user-management'
      }
    ]
  },
  {
    title: 'STATISTIQUE',
    icon: 'activity-outline',
    children: [
      {
        title: "Nombre d'mmatriculations par periode",
        icon: 'people-outline',
        link: 'statistique'
      } /*,
      {
        title: 'Vehicules entres par periode',
        icon: 'people-outline',
        link: '1'
      },
      {
        title: 'Vehicules en circulation par periode',
        icon: 'people-outline',
        link: '2'
      },
      {
        title: 'Immatriculation expiré par periode',
        icon: 'people-outline',
        link: '4'
      },
      {
        title: 'Vehicules en transit par periode',
        link: '5',
        icon: 'checkmark-circle-2-outline'
      },
      {
        title: 'Vehicules en stock par periode',
        link: '6',
        icon: 'checkmark-circle-2-outline'
      },
      {
        title: 'Nombre de concessionnaires par periode',
        link: '7',
        icon: 'checkmark-circle-2-outline'
      },
      {
        title: 'Nombre de revendeurs par periode',
        link: '8',
        icon: 'checkmark-circle-2-outline'
      },
      {
        title: "Nombre d'utilisateurs par periode",
        link: '9',
        icon: 'checkmark-circle-2-outline'
      }*/
    ]
  }
];
