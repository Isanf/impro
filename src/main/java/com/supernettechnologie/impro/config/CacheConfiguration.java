package com.supernettechnologie.impro.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.supernettechnologie.impro.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.supernettechnologie.impro.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.supernettechnologie.impro.domain.User.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Authority.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.User.class.getName() + ".authorities");
            createCache(cm, com.supernettechnologie.impro.domain.CertificatImmatriculation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Immatriculation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.CarnetASouche.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.CarteW.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.CarnetASouche.class.getName() + ".certificatImmatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.PersonnePhysique.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.PersonnePhysique.class.getName() + ".ventes");
            createCache(cm, com.supernettechnologie.impro.domain.PersonnePhysique.class.getName() + ".immatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.DocIdentificationPP.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.PersonneMorale.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.PersonneMorale.class.getName() + ".ventes");
            createCache(cm, com.supernettechnologie.impro.domain.PersonneMorale.class.getName() + ".immatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.DocIdentificationPM.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Vente.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.PosePlaque.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Vehicule.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Vehicule.class.getName() + ".plaqueImmatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.Vehicule.class.getName() + ".immatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.Collaboration.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.CommandeVehicule.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Stock.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.LivraisonVehicule.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.LivraisonVehicule.class.getName() + ".vehicules");
            createCache(cm, com.supernettechnologie.impro.domain.LivraisonCarnetSouche.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.LivraisonCarnetSouche.class.getName() + ".carnetASouches");
            createCache(cm, com.supernettechnologie.impro.domain.CommandeCarnetSouche.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.CategorieOrganisation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.CategorieOrganisation.class.getName() + ".types");
            createCache(cm, com.supernettechnologie.impro.domain.TypeOrganisation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.TypeOrganisation.class.getName() + ".organisations");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".fils");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".concessionnaires");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".revendeurs");
            createCache(cm, com.supernettechnologie.impro.domain.TypeActeur.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Profil.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Profil.class.getName() + ".personnePhysiques");
            createCache(cm, com.supernettechnologie.impro.domain.TypeVehicule.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.TypeVehicule.class.getName() + ".vehicules");
            createCache(cm, com.supernettechnologie.impro.domain.MarqueVehicule.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.MarqueVehicule.class.getName() + ".vehicules");
            createCache(cm, com.supernettechnologie.impro.domain.PlaqueImmatriculation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".profils");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".carnetASouches");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".collaborations");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".personnePhysiques");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".commandeCarnetSouches");
            createCache(cm, com.supernettechnologie.impro.domain.CommandeCarnetSouche.class.getName() + ".livraisonCarnetSouches");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".commandeVehicules");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".immatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".livraisonCarnetSouches");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".livraisonVehicules");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".posePlaques");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".stocks");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".ventes");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".collaborationsRevendeurs");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".collaborationsConcessionnaires");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".commandeCSConcessionnaires");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".commandeCSRevendeurs");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".commandeVRevendeurs");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".commandeVConcessionnaires");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".livraisonCSConcessionnaires");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".livraisonCSSupernets");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".livraisonVRevendeurs");
            createCache(cm, com.supernettechnologie.impro.domain.Organisation.class.getName() + ".livraisonVConcessionnaires");
            createCache(cm, com.supernettechnologie.impro.domain.CertificatImmatriculation.class.getName() + ".plaqueImmatriculations");
            createCache(cm, com.supernettechnologie.impro.domain.TypeVehicule.class.getName() + ".typeCarnets");
            createCache(cm, com.supernettechnologie.impro.domain.TypeCarnet.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.TypeCarnet.class.getName() + ".carnetSouches");
            createCache(cm, com.supernettechnologie.impro.domain.CommandeCarnetSouche.class.getName() + ".infoCommandeCarnetASouches");
            createCache(cm, com.supernettechnologie.impro.domain.CommandeVehicule.class.getName() + ".infoCommandeVehicules");
            createCache(cm, com.supernettechnologie.impro.domain.CommandeVehicule.class.getName() + ".livraisonVehicules");
            createCache(cm, com.supernettechnologie.impro.domain.MarqueVehicule.class.getName() + ".infoCommandeVehicules");
            createCache(cm, com.supernettechnologie.impro.domain.TypeCarnet.class.getName() + ".infoCommandeCarnetASouches");
            createCache(cm, com.supernettechnologie.impro.domain.InfoCommandeVehicule.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.TypeActeur.class.getName() + ".organisations");
            createCache(cm, com.supernettechnologie.impro.domain.Vente.class.getName() + ".vehicules");
            createCache(cm, com.supernettechnologie.impro.domain.Stock.class.getName() + ".vehicules");
            createCache(cm, com.supernettechnologie.impro.domain.PersonnePhysique.class.getName() + ".organisations");
            createCache(cm, com.supernettechnologie.impro.domain.Firstlogin.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.TypeActeur.class.getName() + ".typeOrganisations");
            createCache(cm, com.supernettechnologie.impro.domain.TypeOrganisation.class.getName() + ".typeActeurs");
            createCache(cm, com.supernettechnologie.impro.domain.OrganisationLocalite.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.OrganisationLocalite.class.getName() + ".organisations");
            createCache(cm, com.supernettechnologie.impro.domain.LogActivity.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.VehiculeOccasion.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.VehiculeOccasion.class.getName() + ".organisations");
            createCache(cm, com.supernettechnologie.impro.domain.Statistique.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.PrixCertificat.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.UserOtp.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.UserDeviceId.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.Nation.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.VehiculeTraversant.class.getName());
            createCache(cm, com.supernettechnologie.impro.domain.VehiculeOccasional.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
