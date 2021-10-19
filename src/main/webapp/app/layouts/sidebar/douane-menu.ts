import { NbMenuItem } from '@nebular/theme';

export const DOUANE_MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'ACCUEIL',
    icon: 'home-outline',
    link: '/',
    home: true
  },
  {
    title: 'QUOI?',
    icon: 'car-outline',
    children: [
      {
        title: 'Lister',
        link: '0'
      }
    ]
  },
  {
    title: 'STATISTIQUE',
    icon: 'person-outline',
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
