import { NbMenuItem } from '@nebular/theme';

export const STHGUICHET_MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'ACCUEIL',
    icon: 'home-outline',
    link: '/',
    home: true
  },
  {
    title: 'GESTION DES VEHICULES',
    icon: 'car-outline',
    children: [
      {
        title: 'Importateurs occasionnels',
        link: '/vente'
      },
      {
        title: 'Vehicules occasionnels',
        link: 'vehicule'
      },
      {
        title: 'Vehicules Revendeurs/Traversants',
        link: 'vehicule-occasion'
      }
    ]
  },
  {
    title: 'IMMATRICULATIONS',
    icon: 'file-text-outline',
    children: [
      {
        title: 'Immatriculation',
        link: '/immatriculation'
      },
      {
        title: 'Commande de Carnet',
        link: '/commande-carnet-souche'
      },
      {
        title: 'Carnets à souche',
        link: '/carnet-a-souche'
      }
    ]
  },
  {
    title: 'STATISTIQUE',
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
