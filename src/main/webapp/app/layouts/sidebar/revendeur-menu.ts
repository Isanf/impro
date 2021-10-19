import { NbMenuItem } from '@nebular/theme';

export const REVEN_MENU_ITEMS: NbMenuItem[] = [
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
        title: 'Commandes Véhicules',
        link: '/commande-vehicule'
      },
      {
        title: 'Livraison vehicules reçues',
        link: '/livraison-vehicule'
      },
      {
        title: 'Ventes',
        link: '/vente'
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
        title: 'Immatriculation',
        link: '/immatriculation',
        icon: 'file-text-outline'
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
    title: 'STATISTIQUES',
    icon: 'activity-outline',
    children: [
      {
        title: "Nombre d'mmatriculations par periode",
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
