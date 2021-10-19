import { NbMenuItem } from '@nebular/theme';

export const ADMIN_MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'ACCUEIL',
    icon: 'home-outline',
    link: '/',
    home: true
  },
  {
    title: 'PARAMETRES',
    icon: 'settings-outline',
    children: [
      {
        title: 'Categories Organisations',
        link: 'categorie-organisation'
      },
      {
        title: "Types d'organisations",
        link: 'type-organisation'
      },
      {
        title: 'Types Acteur',
        link: 'type-acteur'
      },
      {
        title: 'Localités',
        link: 'organisation-localite'
      },
      {
        title: 'Types de vehicules',
        link: 'type-vehicule'
      },
      {
        title: 'Types Carnets a souche',
        link: 'type-carnet'
      },
      {
        title: 'Marques de vehicules',
        link: 'marque-vehicule'
      }
    ]
  },
  {
    title: 'CARTE-W',
    icon: 'checkmark-square-2-outline',
    link: '/carte-w'
  },
  {
    title: 'ORGANISATIONS',
    icon: 'grid-outline',
    link: '/organisation'
  },
  {
    title: 'CARNET A SOUCHE',
    icon: 'file-text-outline',
    children: [
      {
        title: 'Carnet à souche',
        link: '/carnet-a-souche'
      },
      {
        title: 'Les Commandes',
        link: '/commande-carnet-souche'
      },
      {
        title: 'Livraison carnet',
        link: '/livraison-carnet-souche'
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
  },
  {
    title: 'SUIVI ADMIN',
    icon: 'options-outline',
    children: [
      {
        title: 'HISTORIQUE',
        link: 'log-activity',
        icon: 'loader-outline'
      },
      {
        title: 'Diagnostics',
        link: 'admin/health',
        icon: 'heart-outline'
      },
      {
        title: 'Configuration',
        link: 'admin/configuration',
        icon: 'options-2-outline'
      },
      {
        title: 'Audits',
        link: 'admin/audits',
        icon: 'flip-outline'
      },
      {
        title: 'Logs',
        link: 'admin/logs',
        icon: 'list-outline'
      },
      {
        title: 'API',
        link: 'admin/docs',
        icon: 'loader-outline'
      }
    ]
  }
];
