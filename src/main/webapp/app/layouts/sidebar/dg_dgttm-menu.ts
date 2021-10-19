import { NbMenuItem } from '@nebular/theme';

export const DG_DGTTM_MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'ACCUEIL',
    icon: 'home-outline',
    link: '/',
    home: true
  },
  {
    title: 'CARNETS A SOUCHE',
    icon: 'activity-outline',
    children: [
      {
        title: 'Livraisons',
        link: '/livraison-carnet-souche'
      }
    ]
  },
  {
    title: 'SIGNATURE',
    icon: 'checkmark-circle-2-outline',
    children: [
      {
        title: 'Signer',
        link: 'organisation'
      }
    ]
  }
  /* {
    title: 'STATISTIQUE',
    icon: 'person-outline',
    children: [
      {
        title: 'Achat Carnet',
        icon: 'people-outline',
        link: '1'
      } /!*,
      {
        title: 'Vehicules en circulation par periode',
        icon: 'people-outline',
        link: '2'
      },
      {
        title: 'Vehicules immatricules par periode',
        icon: 'people-outline',
        link: '3'
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
      }*!/
    ]
  }*/
];
