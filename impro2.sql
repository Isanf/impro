PGDMP                 
        x            impro2    13.0    13.0    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    27360    impro2    DATABASE     h   CREATE DATABASE impro2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'French_Burkina Faso.1252';
    DROP DATABASE impro2;
                postgres    false            ?            1259    27406    carnet_a_souche    TABLE     ?   CREATE TABLE public.carnet_a_souche (
    id bigint NOT NULL,
    date_impression timestamp without time zone,
    numero character varying(255),
    concessionnaire_id bigint,
    livraison_carnet_souche_id bigint,
    type_carnet_id bigint
);
 #   DROP TABLE public.carnet_a_souche;
       public         heap    postgres    false            ?            1259    27430    carte_w    TABLE       CREATE TABLE public.carte_w (
    id bigint NOT NULL,
    code_qr character varying(255),
    date_etablissement_carte_w date,
    date_expiration_carte_w date,
    lieu_etablissement character varying(255),
    numero_carte_w character varying(255),
    organisation_id bigint
);
    DROP TABLE public.carte_w;
       public         heap    postgres    false            ?            1259    27441    categorie_organisation    TABLE     ?   CREATE TABLE public.categorie_organisation (
    id bigint NOT NULL,
    description character varying(255),
    libelle character varying(255) NOT NULL,
    type_categorie_organisation character varying(255)
);
 *   DROP TABLE public.categorie_organisation;
       public         heap    postgres    false            ?            1259    27454    certificat_immatriculation    TABLE     ?   CREATE TABLE public.certificat_immatriculation (
    id bigint NOT NULL,
    code_qr character varying(255),
    numero character varying(255),
    carnetasouche_id bigint
);
 .   DROP TABLE public.certificat_immatriculation;
       public         heap    postgres    false            ?            1259    27462    collaboration    TABLE     ?   CREATE TABLE public.collaboration (
    id bigint NOT NULL,
    date_debut date,
    date_fin date,
    numero_collaboration character varying(255),
    concessionnaire_id bigint,
    revendeur_id bigint
);
 !   DROP TABLE public.collaboration;
       public         heap    postgres    false            ?            1259    27467    commande_carnet_souche    TABLE     |  CREATE TABLE public.commande_carnet_souche (
    id bigint NOT NULL,
    date_commande_cs timestamp without time zone,
    est_livree boolean,
    est_traitee boolean,
    est_valide boolean,
    numero_commande_cs character varying(255),
    prix_commande character varying(255),
    type_paiement character varying(255),
    concessionnaire_id bigint,
    supernet_id bigint
);
 *   DROP TABLE public.commande_carnet_souche;
       public         heap    postgres    false            ?            1259    27482    commande_vehicule    TABLE     ?   CREATE TABLE public.commande_vehicule (
    id bigint NOT NULL,
    date_commande timestamp without time zone,
    est_livree boolean,
    numero_commande_vehicule character varying(255),
    concessionnaire_id bigint,
    revendeur_id bigint
);
 %   DROP TABLE public.commande_vehicule;
       public         heap    postgres    false            ?            1259    27366    databasechangelog    TABLE     Y  CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);
 %   DROP TABLE public.databasechangelog;
       public         heap    postgres    false            ?            1259    27361    databasechangeloglock    TABLE     ?   CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);
 )   DROP TABLE public.databasechangeloglock;
       public         heap    postgres    false            ?            1259    27487    doc_identification_pm    TABLE     ?  CREATE TABLE public.doc_identification_pm (
    id bigint NOT NULL,
    code_postal character varying(255),
    email character varying(255),
    numero character varying(255),
    numero_ifu character varying(255),
    numero_rccm character varying(255),
    siege_social character varying(255),
    telephone character varying(255),
    nation_id bigint,
    organisation_id bigint,
    personne_morale_id bigint
);
 )   DROP TABLE public.doc_identification_pm;
       public         heap    postgres    false            ?            1259    27503    doc_identification_pp    TABLE     R  CREATE TABLE public.doc_identification_pp (
    id bigint NOT NULL,
    autorite_emettrice character varying(255),
    date_etablissement date,
    lieu_etablissement character varying(255),
    nip character varying(255),
    numero_doc character varying(255),
    type_doc_identification character varying(255),
    nation_id bigint
);
 )   DROP TABLE public.doc_identification_pp;
       public         heap    postgres    false            ?            1259    27511 
   firstlogin    TABLE     b   CREATE TABLE public.firstlogin (
    id bigint NOT NULL,
    passe boolean,
    user_id bigint
);
    DROP TABLE public.firstlogin;
       public         heap    postgres    false            ?            1259    27518    image_table    TABLE     ?   CREATE TABLE public.image_table (
    id bigint NOT NULL,
    name character varying(255),
    orgid bigint,
    pic_byte bytea,
    type character varying(255)
);
    DROP TABLE public.image_table;
       public         heap    postgres    false            ?            1259    27516    image_table_id_seq    SEQUENCE     {   CREATE SEQUENCE public.image_table_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.image_table_id_seq;
       public          postgres    false    222            ?           0    0    image_table_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.image_table_id_seq OWNED BY public.image_table.id;
          public          postgres    false    221            ?            1259    27473    immatriculation    TABLE     7  CREATE TABLE public.immatriculation (
    id bigint NOT NULL,
    numero character varying(255),
    date_immatriculation timestamp without time zone,
    certificat_immatriculation_id bigint,
    organisation_id bigint,
    personne_physique_id bigint,
    personne_morale_id bigint,
    vehicule_id bigint
);
 #   DROP TABLE public.immatriculation;
       public         heap    postgres    false            ?            1259    27535    info_commande_carnet_a_souche    TABLE     <  CREATE TABLE public.info_commande_carnet_a_souche (
    id bigint NOT NULL,
    date_commande timestamp without time zone,
    est_deliver boolean,
    est_transiter boolean,
    numero_commande character varying(255),
    quantite_commande bigint,
    commande_carnet_souche_id bigint,
    type_carnet_id bigint
);
 1   DROP TABLE public.info_commande_carnet_a_souche;
       public         heap    postgres    false            ?            1259    27540    info_commande_vehicule    TABLE        CREATE TABLE public.info_commande_vehicule (
    id bigint NOT NULL,
    date_commande timestamp without time zone,
    numero_commande character varying(255),
    quantite_commande bigint,
    commande_vehicule_id bigint,
    marque_vehicule_id bigint
);
 *   DROP TABLE public.info_commande_vehicule;
       public         heap    postgres    false            ?            1259    27386    jhi_authority    TABLE     O   CREATE TABLE public.jhi_authority (
    name character varying(50) NOT NULL
);
 !   DROP TABLE public.jhi_authority;
       public         heap    postgres    false            ?            1259    27421    jhi_persistent_audit_event    TABLE     ?   CREATE TABLE public.jhi_persistent_audit_event (
    event_id bigint NOT NULL,
    principal character varying(50) NOT NULL,
    event_date timestamp without time zone,
    event_type character varying(255)
);
 .   DROP TABLE public.jhi_persistent_audit_event;
       public         heap    postgres    false            ?            1259    27426    jhi_persistent_audit_evt_data    TABLE     ?   CREATE TABLE public.jhi_persistent_audit_evt_data (
    event_id bigint NOT NULL,
    name character varying(150) NOT NULL,
    value character varying(255)
);
 1   DROP TABLE public.jhi_persistent_audit_evt_data;
       public         heap    postgres    false            ?            1259    27396    jhi_persistent_token    TABLE     ?   CREATE TABLE public.jhi_persistent_token (
    series character varying(20) NOT NULL,
    user_id bigint,
    token_value character varying(20) NOT NULL,
    token_date date,
    ip_address character varying(39),
    user_agent character varying(255)
);
 (   DROP TABLE public.jhi_persistent_token;
       public         heap    postgres    false            ?            1259    27374    jhi_user    TABLE     ?  CREATE TABLE public.jhi_user (
    id bigint NOT NULL,
    login character varying(50) NOT NULL,
    password_hash character varying(60) NOT NULL,
    first_name character varying(50),
    last_name character varying(50),
    email character varying(191),
    image_url character varying(256),
    activated boolean NOT NULL,
    lang_key character varying(10),
    activation_key character varying(20),
    reset_key character varying(20),
    created_by character varying(50) NOT NULL,
    created_date timestamp without time zone,
    reset_date timestamp without time zone,
    last_modified_by character varying(50),
    last_modified_date timestamp without time zone
);
    DROP TABLE public.jhi_user;
       public         heap    postgres    false            ?            1259    27391    jhi_user_authority    TABLE     {   CREATE TABLE public.jhi_user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);
 &   DROP TABLE public.jhi_user_authority;
       public         heap    postgres    false            ?            1259    27551    livraison_carnet_souche    TABLE       CREATE TABLE public.livraison_carnet_souche (
    id bigint NOT NULL,
    date_livraison timestamp without time zone,
    numero_livraison_cs character varying(255),
    commande_carnet_souche_id bigint,
    concessionnaire_id bigint,
    supernet_id bigint
);
 +   DROP TABLE public.livraison_carnet_souche;
       public         heap    postgres    false            ?            1259    27558    livraison_vehicule    TABLE     ?   CREATE TABLE public.livraison_vehicule (
    id bigint NOT NULL,
    date_livraison timestamp without time zone,
    numero_livraison character varying(255),
    commande_vehicule_id bigint,
    concessionnaire_id bigint,
    revendeur_id bigint
);
 &   DROP TABLE public.livraison_vehicule;
       public         heap    postgres    false            ?            1259    27563    log_activity    TABLE     ?   CREATE TABLE public.log_activity (
    id bigint NOT NULL,
    action character varying(255),
    date_action timestamp without time zone,
    ip character varying(255),
    principal character varying(255),
    url character varying(255)
);
     DROP TABLE public.log_activity;
       public         heap    postgres    false            ?            1259    27577    marque_vehicule    TABLE     ?   CREATE TABLE public.marque_vehicule (
    id bigint NOT NULL,
    code character varying(255),
    libelle character varying(255)
);
 #   DROP TABLE public.marque_vehicule;
       public         heap    postgres    false            ?            1259    27587    nation    TABLE     ?   CREATE TABLE public.nation (
    id bigint NOT NULL,
    iso character varying(255),
    iso_3 character varying(255),
    name character varying(255),
    nicename character varying(255),
    numcode integer,
    phonecode integer
);
    DROP TABLE public.nation;
       public         heap    postgres    false            ?            1259    27595    organisation    TABLE     ?  CREATE TABLE public.organisation (
    id bigint NOT NULL,
    description character varying(1000),
    nom character varying(255) NOT NULL,
    numero_ordre integer NOT NULL,
    numero_phone character varying(255),
    signnom character varying(255),
    gerant_id bigint,
    organisation_localite_id bigint,
    pere_id bigint,
    type_acteur_id bigint,
    type_organisation_id bigint
);
     DROP TABLE public.organisation;
       public         heap    postgres    false            ?            1259    27493    organisation_localite    TABLE     ?   CREATE TABLE public.organisation_localite (
    id bigint NOT NULL,
    code character varying(255),
    nom character varying(255),
    description character varying(255)
);
 )   DROP TABLE public.organisation_localite;
       public         heap    postgres    false            ?            1259    27545    personne_morale    TABLE     ?   CREATE TABLE public.personne_morale (
    id bigint NOT NULL,
    numero_ifu character varying(255),
    denomination character varying(255),
    date_create date
);
 #   DROP TABLE public.personne_morale;
       public         heap    postgres    false            ?           0    0    TABLE personne_morale    COMMENT     a   COMMENT ON TABLE public.personne_morale IS 'The PersonneMorale entity.\n@author A true hipster';
          public          postgres    false    226            ?           0    0 !   COLUMN personne_morale.numero_ifu    COMMENT     D   COMMENT ON COLUMN public.personne_morale.numero_ifu IS 'numeroIFU';
          public          postgres    false    226            ?            1259    27603    personne_physique    TABLE     ?  CREATE TABLE public.personne_physique (
    id bigint NOT NULL,
    date_naissance date,
    flogin character varying(255),
    fotp bigint,
    fpassword character varying(255),
    lieu_naissance character varying(255),
    nom character varying(255),
    prenom character varying(255),
    residence character varying(255),
    telephone character varying(255),
    doc_identification_id bigint,
    organisation_id bigint,
    profil_id bigint,
    user_id bigint
);
 %   DROP TABLE public.personne_physique;
       public         heap    postgres    false            ?            1259    27619    plaque_garage    TABLE     ?   CREATE TABLE public.plaque_garage (
    id bigint NOT NULL,
    code_qr_plaque character varying(255),
    created_at timestamp without time zone,
    numero_ordre character varying(255),
    numero_plaque character varying(255),
    cartew_id bigint
);
 !   DROP TABLE public.plaque_garage;
       public         heap    postgres    false            ?            1259    27627    plaque_immatriculation    TABLE     	  CREATE TABLE public.plaque_immatriculation (
    id bigint NOT NULL,
    code_qr character varying(255),
    numero_immatriculation character varying(255),
    numero_serie character varying(255),
    certificat_immatriculation_id bigint,
    vehicule_id bigint
);
 *   DROP TABLE public.plaque_immatriculation;
       public         heap    postgres    false            ?            1259    27635    pose_plaque    TABLE     ?   CREATE TABLE public.pose_plaque (
    id bigint NOT NULL,
    date_pose_plaque timestamp without time zone,
    numero_pose character varying(255),
    revendeur_id bigint
);
    DROP TABLE public.pose_plaque;
       public         heap    postgres    false            ?            1259    27640    prix_certificat    TABLE     q   CREATE TABLE public.prix_certificat (
    id bigint NOT NULL,
    activated boolean,
    prix bigint NOT NULL
);
 #   DROP TABLE public.prix_certificat;
       public         heap    postgres    false            ?            1259    27645    profil    TABLE     ?   CREATE TABLE public.profil (
    id bigint NOT NULL,
    description character varying(1000),
    nom character varying(255) NOT NULL,
    organisation_id bigint
);
    DROP TABLE public.profil;
       public         heap    postgres    false            ?            1259    27653    profil_authority    TABLE     |   CREATE TABLE public.profil_authority (
    profils_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);
 $   DROP TABLE public.profil_authority;
       public         heap    postgres    false            ?            1259    27372    sequence_generator    SEQUENCE        CREATE SEQUENCE public.sequence_generator
    START WITH 20000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.sequence_generator;
       public          postgres    false            ?            1259    27658    statistique    TABLE     \   CREATE TABLE public.statistique (
    id bigint NOT NULL,
    nom character varying(255)
);
    DROP TABLE public.statistique;
       public         heap    postgres    false            ?            1259    27571    stock    TABLE     ?   CREATE TABLE public.stock (
    id bigint NOT NULL,
    numero_stock character varying(255),
    fichier_stock bytea,
    fichier_stock_content_type character varying(255),
    date_stock timestamp without time zone,
    concessionnaire_id bigint
);
    DROP TABLE public.stock;
       public         heap    postgres    false            ?           0    0    TABLE stock    COMMENT     N   COMMENT ON TABLE public.stock IS 'The Stock entity.\n@author A true hipster';
          public          postgres    false    230            ?           0    0    COLUMN stock.numero_stock    COMMENT     >   COMMENT ON COLUMN public.stock.numero_stock IS 'numeroStock';
          public          postgres    false    230            ?            1259    27525    type_acteur    TABLE     ?   CREATE TABLE public.type_acteur (
    id bigint NOT NULL,
    nom character varying(255) NOT NULL,
    description character varying(1000)
);
    DROP TABLE public.type_acteur;
       public         heap    postgres    false            ?            1259    27663    type_acteur_type_organisations    TABLE     ?   CREATE TABLE public.type_acteur_type_organisations (
    type_acteur_id bigint NOT NULL,
    type_organisations_id bigint NOT NULL
);
 2   DROP TABLE public.type_acteur_type_organisations;
       public         heap    postgres    false            ?            1259    27668    type_carnet    TABLE     ?   CREATE TABLE public.type_carnet (
    id bigint NOT NULL,
    code character varying(255),
    libelle character varying(255),
    quantite_certificat bigint,
    type_vehicule_id bigint
);
    DROP TABLE public.type_carnet;
       public         heap    postgres    false            ?            1259    27609    type_organisation    TABLE     ?   CREATE TABLE public.type_organisation (
    id bigint NOT NULL,
    nom character varying(255) NOT NULL,
    description character varying(1000),
    niveau integer NOT NULL,
    categorie_organisation_id bigint
);
 %   DROP TABLE public.type_organisation;
       public         heap    postgres    false            ?            1259    27676    type_vehicule    TABLE     ?   CREATE TABLE public.type_vehicule (
    id bigint NOT NULL,
    code character varying(255),
    est_cycle_moteur boolean,
    libelle character varying(255),
    nombre_plaque bigint
);
 !   DROP TABLE public.type_vehicule;
       public         heap    postgres    false            ?            1259    27684    user_device_id    TABLE     ?   CREATE TABLE public.user_device_id (
    id bigint NOT NULL,
    adress_mac character varying(255),
    device_id character varying(255),
    user_id bigint
);
 "   DROP TABLE public.user_device_id;
       public         heap    postgres    false            ?            1259    27692    user_otp    TABLE     z   CREATE TABLE public.user_otp (
    id bigint NOT NULL,
    otp_number bigint,
    otp_used boolean,
    user_id bigint
);
    DROP TABLE public.user_otp;
       public         heap    postgres    false            ?            1259    27697    vehicule    TABLE     ?  CREATE TABLE public.vehicule (
    id bigint NOT NULL,
    capacite integer,
    charge_utile integer,
    couleur character varying(255),
    date_dedouanement date,
    date_mise_circulation date,
    energie character varying(255),
    model character varying(255),
    nbr_place integer,
    no_dedouanement character varying(255),
    numero_chassis character varying(255),
    poids_vide integer,
    ptac integer,
    ptra integer,
    puissance_admin character varying(255),
    puissance_reel character varying(255),
    regime character varying(255),
    types character varying(255),
    livraison_vehicule_id bigint,
    marque_vehicule_id bigint,
    stock_id bigint,
    type_vehicule_id bigint,
    vente_id bigint
);
    DROP TABLE public.vehicule;
       public         heap    postgres    false            ?            1259    27705    vehicule_occasion    TABLE     ?  CREATE TABLE public.vehicule_occasion (
    id bigint NOT NULL,
    carte_w character varying(255),
    chassis character varying(255),
    created_at timestamp without time zone,
    marque character varying(255),
    model character varying(255),
    nom_prenom character varying(255),
    numero_cnib character varying(255),
    telephone character varying(255),
    organisation_id bigint NOT NULL,
    personne_morale_id bigint,
    personne_physique_id bigint
);
 %   DROP TABLE public.vehicule_occasion;
       public         heap    postgres    false            ?            1259    27713    vehicule_occasional    TABLE     K  CREATE TABLE public.vehicule_occasional (
    id bigint NOT NULL,
    chassis character varying(255),
    created_at timestamp without time zone,
    marque character varying(255),
    model character varying(255),
    cartew_id bigint,
    organisation_id bigint,
    personne_morale_id bigint,
    personne_physique_id bigint
);
 '   DROP TABLE public.vehicule_occasional;
       public         heap    postgres    false            ?            1259    27721    vehicule_traversant    TABLE     ?  CREATE TABLE public.vehicule_traversant (
    id bigint NOT NULL,
    chassis character varying(255),
    created_at timestamp without time zone,
    date_entre timestamp without time zone,
    date_sortie timestamp without time zone,
    destination character varying(255),
    marque character varying(255),
    model character varying(255),
    provenance character varying(255),
    organisation_id bigint,
    personne_morale_id bigint,
    personne_physique_id bigint
);
 '   DROP TABLE public.vehicule_traversant;
       public         heap    postgres    false            ?            1259    27729    vente    TABLE       CREATE TABLE public.vente (
    id bigint NOT NULL,
    date_vente timestamp without time zone,
    numero_vente character varying(255),
    quantite_vendue integer,
    personne_morale_id bigint,
    personne_physique_id bigint,
    revendeur_id bigint
);
    DROP TABLE public.vente;
       public         heap    postgres    false            	           2604    27521    image_table id    DEFAULT     p   ALTER TABLE ONLY public.image_table ALTER COLUMN id SET DEFAULT nextval('public.image_table_id_seq'::regclass);
 =   ALTER TABLE public.image_table ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222            j          0    27406    carnet_a_souche 
   TABLE DATA           ?   COPY public.carnet_a_souche (id, date_impression, numero, concessionnaire_id, livraison_carnet_souche_id, type_carnet_id) FROM stdin;
    public          postgres    false    207   ??      m          0    27430    carte_w 
   TABLE DATA           ?   COPY public.carte_w (id, code_qr, date_etablissement_carte_w, date_expiration_carte_w, lieu_etablissement, numero_carte_w, organisation_id) FROM stdin;
    public          postgres    false    210   Ɂ      n          0    27441    categorie_organisation 
   TABLE DATA           g   COPY public.categorie_organisation (id, description, libelle, type_categorie_organisation) FROM stdin;
    public          postgres    false    211   ??      o          0    27454    certificat_immatriculation 
   TABLE DATA           [   COPY public.certificat_immatriculation (id, code_qr, numero, carnetasouche_id) FROM stdin;
    public          postgres    false    212   ??      p          0    27462    collaboration 
   TABLE DATA           y   COPY public.collaboration (id, date_debut, date_fin, numero_collaboration, concessionnaire_id, revendeur_id) FROM stdin;
    public          postgres    false    213   ??      q          0    27467    commande_carnet_souche 
   TABLE DATA           ?   COPY public.commande_carnet_souche (id, date_commande_cs, est_livree, est_traitee, est_valide, numero_commande_cs, prix_commande, type_paiement, concessionnaire_id, supernet_id) FROM stdin;
    public          postgres    false    214   ??      s          0    27482    commande_vehicule 
   TABLE DATA           ?   COPY public.commande_vehicule (id, date_commande, est_livree, numero_commande_vehicule, concessionnaire_id, revendeur_id) FROM stdin;
    public          postgres    false    216   ڂ      d          0    27366    databasechangelog 
   TABLE DATA           ?   COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
    public          postgres    false    201   ??      c          0    27361    databasechangeloglock 
   TABLE DATA           R   COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
    public          postgres    false    200   Ŋ      t          0    27487    doc_identification_pm 
   TABLE DATA           ?   COPY public.doc_identification_pm (id, code_postal, email, numero, numero_ifu, numero_rccm, siege_social, telephone, nation_id, organisation_id, personne_morale_id) FROM stdin;
    public          postgres    false    217   ??      v          0    27503    doc_identification_pp 
   TABLE DATA           ?   COPY public.doc_identification_pp (id, autorite_emettrice, date_etablissement, lieu_etablissement, nip, numero_doc, type_doc_identification, nation_id) FROM stdin;
    public          postgres    false    219   ??      w          0    27511 
   firstlogin 
   TABLE DATA           8   COPY public.firstlogin (id, passe, user_id) FROM stdin;
    public          postgres    false    220   ??      y          0    27518    image_table 
   TABLE DATA           F   COPY public.image_table (id, name, orgid, pic_byte, type) FROM stdin;
    public          postgres    false    222   ??      r          0    27473    immatriculation 
   TABLE DATA           ?   COPY public.immatriculation (id, numero, date_immatriculation, certificat_immatriculation_id, organisation_id, personne_physique_id, personne_morale_id, vehicule_id) FROM stdin;
    public          postgres    false    215   Ќ      {          0    27535    info_commande_carnet_a_souche 
   TABLE DATA           ?   COPY public.info_commande_carnet_a_souche (id, date_commande, est_deliver, est_transiter, numero_commande, quantite_commande, commande_carnet_souche_id, type_carnet_id) FROM stdin;
    public          postgres    false    224   ??      |          0    27540    info_commande_vehicule 
   TABLE DATA           ?   COPY public.info_commande_vehicule (id, date_commande, numero_commande, quantite_commande, commande_vehicule_id, marque_vehicule_id) FROM stdin;
    public          postgres    false    225   
?      g          0    27386    jhi_authority 
   TABLE DATA           -   COPY public.jhi_authority (name) FROM stdin;
    public          postgres    false    204   '?      k          0    27421    jhi_persistent_audit_event 
   TABLE DATA           a   COPY public.jhi_persistent_audit_event (event_id, principal, event_date, event_type) FROM stdin;
    public          postgres    false    208   ??      l          0    27426    jhi_persistent_audit_evt_data 
   TABLE DATA           N   COPY public.jhi_persistent_audit_evt_data (event_id, name, value) FROM stdin;
    public          postgres    false    209   ??      i          0    27396    jhi_persistent_token 
   TABLE DATA           p   COPY public.jhi_persistent_token (series, user_id, token_value, token_date, ip_address, user_agent) FROM stdin;
    public          postgres    false    206   +?      f          0    27374    jhi_user 
   TABLE DATA           ?   COPY public.jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) FROM stdin;
    public          postgres    false    203   H?      h          0    27391    jhi_user_authority 
   TABLE DATA           E   COPY public.jhi_user_authority (user_id, authority_name) FROM stdin;
    public          postgres    false    205   D?      ~          0    27551    livraison_carnet_souche 
   TABLE DATA           ?   COPY public.livraison_carnet_souche (id, date_livraison, numero_livraison_cs, commande_carnet_souche_id, concessionnaire_id, supernet_id) FROM stdin;
    public          postgres    false    227   ֔                0    27558    livraison_vehicule 
   TABLE DATA           ?   COPY public.livraison_vehicule (id, date_livraison, numero_livraison, commande_vehicule_id, concessionnaire_id, revendeur_id) FROM stdin;
    public          postgres    false    228   ??      ?          0    27563    log_activity 
   TABLE DATA           S   COPY public.log_activity (id, action, date_action, ip, principal, url) FROM stdin;
    public          postgres    false    229   ?      ?          0    27577    marque_vehicule 
   TABLE DATA           <   COPY public.marque_vehicule (id, code, libelle) FROM stdin;
    public          postgres    false    231   -?      ?          0    27587    nation 
   TABLE DATA           T   COPY public.nation (id, iso, iso_3, name, nicename, numcode, phonecode) FROM stdin;
    public          postgres    false    232   J?      ?          0    27595    organisation 
   TABLE DATA           ?   COPY public.organisation (id, description, nom, numero_ordre, numero_phone, signnom, gerant_id, organisation_localite_id, pere_id, type_acteur_id, type_organisation_id) FROM stdin;
    public          postgres    false    233   g?      u          0    27493    organisation_localite 
   TABLE DATA           K   COPY public.organisation_localite (id, code, nom, description) FROM stdin;
    public          postgres    false    218   Ζ      }          0    27545    personne_morale 
   TABLE DATA           T   COPY public.personne_morale (id, numero_ifu, denomination, date_create) FROM stdin;
    public          postgres    false    226   ??      ?          0    27603    personne_physique 
   TABLE DATA           ?   COPY public.personne_physique (id, date_naissance, flogin, fotp, fpassword, lieu_naissance, nom, prenom, residence, telephone, doc_identification_id, organisation_id, profil_id, user_id) FROM stdin;
    public          postgres    false    234   ?      ?          0    27619    plaque_garage 
   TABLE DATA           o   COPY public.plaque_garage (id, code_qr_plaque, created_at, numero_ordre, numero_plaque, cartew_id) FROM stdin;
    public          postgres    false    236   ??      ?          0    27627    plaque_immatriculation 
   TABLE DATA           ?   COPY public.plaque_immatriculation (id, code_qr, numero_immatriculation, numero_serie, certificat_immatriculation_id, vehicule_id) FROM stdin;
    public          postgres    false    237   ę      ?          0    27635    pose_plaque 
   TABLE DATA           V   COPY public.pose_plaque (id, date_pose_plaque, numero_pose, revendeur_id) FROM stdin;
    public          postgres    false    238   ??      ?          0    27640    prix_certificat 
   TABLE DATA           >   COPY public.prix_certificat (id, activated, prix) FROM stdin;
    public          postgres    false    239   ??      ?          0    27645    profil 
   TABLE DATA           G   COPY public.profil (id, description, nom, organisation_id) FROM stdin;
    public          postgres    false    240   ?      ?          0    27653    profil_authority 
   TABLE DATA           F   COPY public.profil_authority (profils_id, authority_name) FROM stdin;
    public          postgres    false    241   ?      ?          0    27658    statistique 
   TABLE DATA           .   COPY public.statistique (id, nom) FROM stdin;
    public          postgres    false    242   O?      ?          0    27571    stock 
   TABLE DATA           |   COPY public.stock (id, numero_stock, fichier_stock, fichier_stock_content_type, date_stock, concessionnaire_id) FROM stdin;
    public          postgres    false    230   l?      z          0    27525    type_acteur 
   TABLE DATA           ;   COPY public.type_acteur (id, nom, description) FROM stdin;
    public          postgres    false    223   ??      ?          0    27663    type_acteur_type_organisations 
   TABLE DATA           _   COPY public.type_acteur_type_organisations (type_acteur_id, type_organisations_id) FROM stdin;
    public          postgres    false    243   U?      ?          0    27668    type_carnet 
   TABLE DATA           _   COPY public.type_carnet (id, code, libelle, quantite_certificat, type_vehicule_id) FROM stdin;
    public          postgres    false    244   r?      ?          0    27609    type_organisation 
   TABLE DATA           d   COPY public.type_organisation (id, nom, description, niveau, categorie_organisation_id) FROM stdin;
    public          postgres    false    235   ??      ?          0    27676    type_vehicule 
   TABLE DATA           [   COPY public.type_vehicule (id, code, est_cycle_moteur, libelle, nombre_plaque) FROM stdin;
    public          postgres    false    245   ??      ?          0    27684    user_device_id 
   TABLE DATA           L   COPY public.user_device_id (id, adress_mac, device_id, user_id) FROM stdin;
    public          postgres    false    246   ?      ?          0    27692    user_otp 
   TABLE DATA           E   COPY public.user_otp (id, otp_number, otp_used, user_id) FROM stdin;
    public          postgres    false    247   %?      ?          0    27697    vehicule 
   TABLE DATA           F  COPY public.vehicule (id, capacite, charge_utile, couleur, date_dedouanement, date_mise_circulation, energie, model, nbr_place, no_dedouanement, numero_chassis, poids_vide, ptac, ptra, puissance_admin, puissance_reel, regime, types, livraison_vehicule_id, marque_vehicule_id, stock_id, type_vehicule_id, vente_id) FROM stdin;
    public          postgres    false    248   ??      ?          0    27705    vehicule_occasion 
   TABLE DATA           ?   COPY public.vehicule_occasion (id, carte_w, chassis, created_at, marque, model, nom_prenom, numero_cnib, telephone, organisation_id, personne_morale_id, personne_physique_id) FROM stdin;
    public          postgres    false    249   ??      ?          0    27713    vehicule_occasional 
   TABLE DATA           ?   COPY public.vehicule_occasional (id, chassis, created_at, marque, model, cartew_id, organisation_id, personne_morale_id, personne_physique_id) FROM stdin;
    public          postgres    false    250   ՝      ?          0    27721    vehicule_traversant 
   TABLE DATA           ?   COPY public.vehicule_traversant (id, chassis, created_at, date_entre, date_sortie, destination, marque, model, provenance, organisation_id, personne_morale_id, personne_physique_id) FROM stdin;
    public          postgres    false    251   ??      ?          0    27729    vente 
   TABLE DATA           ?   COPY public.vente (id, date_vente, numero_vente, quantite_vendue, personne_morale_id, personne_physique_id, revendeur_id) FROM stdin;
    public          postgres    false    252   ?      ?           0    0    image_table_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.image_table_id_seq', 1, false);
          public          postgres    false    221            ?           0    0    sequence_generator    SEQUENCE SET     D   SELECT pg_catalog.setval('public.sequence_generator', 20008, true);
          public          postgres    false    202                       2606    27415 $   carnet_a_souche carnet_a_souche_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.carnet_a_souche
    ADD CONSTRAINT carnet_a_souche_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.carnet_a_souche DROP CONSTRAINT carnet_a_souche_pkey;
       public            postgres    false    207            %           2606    27439    carte_w carte_w_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.carte_w
    ADD CONSTRAINT carte_w_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.carte_w DROP CONSTRAINT carte_w_pkey;
       public            postgres    false    210            )           2606    27453 2   categorie_organisation categorie_organisation_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.categorie_organisation
    ADD CONSTRAINT categorie_organisation_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.categorie_organisation DROP CONSTRAINT categorie_organisation_pkey;
       public            postgres    false    211            +           2606    27461 :   certificat_immatriculation certificat_immatriculation_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.certificat_immatriculation
    ADD CONSTRAINT certificat_immatriculation_pkey PRIMARY KEY (id);
 d   ALTER TABLE ONLY public.certificat_immatriculation DROP CONSTRAINT certificat_immatriculation_pkey;
       public            postgres    false    212            -           2606    27466     collaboration collaboration_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.collaboration
    ADD CONSTRAINT collaboration_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.collaboration DROP CONSTRAINT collaboration_pkey;
       public            postgres    false    213            /           2606    27479 2   commande_carnet_souche commande_carnet_souche_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.commande_carnet_souche
    ADD CONSTRAINT commande_carnet_souche_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.commande_carnet_souche DROP CONSTRAINT commande_carnet_souche_pkey;
       public            postgres    false    214            7           2606    27486 (   commande_vehicule commande_vehicule_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.commande_vehicule
    ADD CONSTRAINT commande_vehicule_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.commande_vehicule DROP CONSTRAINT commande_vehicule_pkey;
       public            postgres    false    216                       2606    27365 0   databasechangeloglock databasechangeloglock_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.databasechangeloglock DROP CONSTRAINT databasechangeloglock_pkey;
       public            postgres    false    200            9           2606    27500 0   doc_identification_pm doc_identification_pm_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT doc_identification_pm_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT doc_identification_pm_pkey;
       public            postgres    false    217            C           2606    27510 0   doc_identification_pp doc_identification_pp_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.doc_identification_pp
    ADD CONSTRAINT doc_identification_pp_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.doc_identification_pp DROP CONSTRAINT doc_identification_pp_pkey;
       public            postgres    false    219            G           2606    27515    firstlogin firstlogin_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.firstlogin
    ADD CONSTRAINT firstlogin_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.firstlogin DROP CONSTRAINT firstlogin_pkey;
       public            postgres    false    220            K           2606    27532    image_table image_table_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.image_table
    ADD CONSTRAINT image_table_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.image_table DROP CONSTRAINT image_table_pkey;
       public            postgres    false    222            1           2606    27477 $   immatriculation immatriculation_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT immatriculation_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT immatriculation_pkey;
       public            postgres    false    215            Q           2606    27539 @   info_commande_carnet_a_souche info_commande_carnet_a_souche_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.info_commande_carnet_a_souche
    ADD CONSTRAINT info_commande_carnet_a_souche_pkey PRIMARY KEY (id);
 j   ALTER TABLE ONLY public.info_commande_carnet_a_souche DROP CONSTRAINT info_commande_carnet_a_souche_pkey;
       public            postgres    false    224            S           2606    27544 2   info_commande_vehicule info_commande_vehicule_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.info_commande_vehicule
    ADD CONSTRAINT info_commande_vehicule_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.info_commande_vehicule DROP CONSTRAINT info_commande_vehicule_pkey;
       public            postgres    false    225                       2606    27390     jhi_authority jhi_authority_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.jhi_authority
    ADD CONSTRAINT jhi_authority_pkey PRIMARY KEY (name);
 J   ALTER TABLE ONLY public.jhi_authority DROP CONSTRAINT jhi_authority_pkey;
       public            postgres    false    204                        2606    27425 :   jhi_persistent_audit_event jhi_persistent_audit_event_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.jhi_persistent_audit_event
    ADD CONSTRAINT jhi_persistent_audit_event_pkey PRIMARY KEY (event_id);
 d   ALTER TABLE ONLY public.jhi_persistent_audit_event DROP CONSTRAINT jhi_persistent_audit_event_pkey;
       public            postgres    false    208            #           2606    27431 @   jhi_persistent_audit_evt_data jhi_persistent_audit_evt_data_pkey 
   CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data
    ADD CONSTRAINT jhi_persistent_audit_evt_data_pkey PRIMARY KEY (event_id, name);
 j   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data DROP CONSTRAINT jhi_persistent_audit_evt_data_pkey;
       public            postgres    false    209    209                       2606    27400 .   jhi_persistent_token jhi_persistent_token_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.jhi_persistent_token
    ADD CONSTRAINT jhi_persistent_token_pkey PRIMARY KEY (series);
 X   ALTER TABLE ONLY public.jhi_persistent_token DROP CONSTRAINT jhi_persistent_token_pkey;
       public            postgres    false    206                       2606    27395 *   jhi_user_authority jhi_user_authority_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);
 T   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT jhi_user_authority_pkey;
       public            postgres    false    205    205                       2606    27381    jhi_user jhi_user_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT jhi_user_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT jhi_user_pkey;
       public            postgres    false    203            W           2606    27555 4   livraison_carnet_souche livraison_carnet_souche_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.livraison_carnet_souche
    ADD CONSTRAINT livraison_carnet_souche_pkey PRIMARY KEY (id);
 ^   ALTER TABLE ONLY public.livraison_carnet_souche DROP CONSTRAINT livraison_carnet_souche_pkey;
       public            postgres    false    227            Y           2606    27562 *   livraison_vehicule livraison_vehicule_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.livraison_vehicule
    ADD CONSTRAINT livraison_vehicule_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.livraison_vehicule DROP CONSTRAINT livraison_vehicule_pkey;
       public            postgres    false    228            [           2606    27570    log_activity log_activity_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.log_activity
    ADD CONSTRAINT log_activity_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.log_activity DROP CONSTRAINT log_activity_pkey;
       public            postgres    false    229            _           2606    27586 $   marque_vehicule marque_vehicule_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.marque_vehicule
    ADD CONSTRAINT marque_vehicule_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.marque_vehicule DROP CONSTRAINT marque_vehicule_pkey;
       public            postgres    false    231            a           2606    27594    nation nation_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.nation
    ADD CONSTRAINT nation_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.nation DROP CONSTRAINT nation_pkey;
       public            postgres    false    232            A           2606    27502 0   organisation_localite organisation_localite_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.organisation_localite
    ADD CONSTRAINT organisation_localite_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.organisation_localite DROP CONSTRAINT organisation_localite_pkey;
       public            postgres    false    218            c           2606    27602    organisation organisation_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT organisation_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.organisation DROP CONSTRAINT organisation_pkey;
       public            postgres    false    233            U           2606    27557 $   personne_morale personne_morale_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.personne_morale
    ADD CONSTRAINT personne_morale_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.personne_morale DROP CONSTRAINT personne_morale_pkey;
       public            postgres    false    226            e           2606    27616 (   personne_physique personne_physique_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT personne_physique_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT personne_physique_pkey;
       public            postgres    false    234            m           2606    27626     plaque_garage plaque_garage_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.plaque_garage
    ADD CONSTRAINT plaque_garage_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.plaque_garage DROP CONSTRAINT plaque_garage_pkey;
       public            postgres    false    236            o           2606    27634 2   plaque_immatriculation plaque_immatriculation_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.plaque_immatriculation
    ADD CONSTRAINT plaque_immatriculation_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.plaque_immatriculation DROP CONSTRAINT plaque_immatriculation_pkey;
       public            postgres    false    237            q           2606    27639    pose_plaque pose_plaque_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.pose_plaque
    ADD CONSTRAINT pose_plaque_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.pose_plaque DROP CONSTRAINT pose_plaque_pkey;
       public            postgres    false    238            s           2606    27644 $   prix_certificat prix_certificat_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.prix_certificat
    ADD CONSTRAINT prix_certificat_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.prix_certificat DROP CONSTRAINT prix_certificat_pkey;
       public            postgres    false    239            w           2606    27657 &   profil_authority profil_authority_pkey 
   CONSTRAINT     |   ALTER TABLE ONLY public.profil_authority
    ADD CONSTRAINT profil_authority_pkey PRIMARY KEY (profils_id, authority_name);
 P   ALTER TABLE ONLY public.profil_authority DROP CONSTRAINT profil_authority_pkey;
       public            postgres    false    241    241            u           2606    27652    profil profil_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.profil
    ADD CONSTRAINT profil_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.profil DROP CONSTRAINT profil_pkey;
       public            postgres    false    240            y           2606    27662    statistique statistique_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.statistique
    ADD CONSTRAINT statistique_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.statistique DROP CONSTRAINT statistique_pkey;
       public            postgres    false    242            ]           2606    27584    stock stock_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.stock DROP CONSTRAINT stock_pkey;
       public            postgres    false    230            O           2606    27534    type_acteur type_acteur_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.type_acteur
    ADD CONSTRAINT type_acteur_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.type_acteur DROP CONSTRAINT type_acteur_pkey;
       public            postgres    false    223            {           2606    27667 B   type_acteur_type_organisations type_acteur_type_organisations_pkey 
   CONSTRAINT     ?   ALTER TABLE ONLY public.type_acteur_type_organisations
    ADD CONSTRAINT type_acteur_type_organisations_pkey PRIMARY KEY (type_acteur_id, type_organisations_id);
 l   ALTER TABLE ONLY public.type_acteur_type_organisations DROP CONSTRAINT type_acteur_type_organisations_pkey;
       public            postgres    false    243    243            }           2606    27675    type_carnet type_carnet_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.type_carnet
    ADD CONSTRAINT type_carnet_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.type_carnet DROP CONSTRAINT type_carnet_pkey;
       public            postgres    false    244            k           2606    27618 (   type_organisation type_organisation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.type_organisation
    ADD CONSTRAINT type_organisation_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.type_organisation DROP CONSTRAINT type_organisation_pkey;
       public            postgres    false    235                       2606    27683     type_vehicule type_vehicule_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.type_vehicule
    ADD CONSTRAINT type_vehicule_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.type_vehicule DROP CONSTRAINT type_vehicule_pkey;
       public            postgres    false    245            E           2606    27743 2   doc_identification_pp uk_2rx408m3ausajsegv516g1j2t 
   CONSTRAINT     l   ALTER TABLE ONLY public.doc_identification_pp
    ADD CONSTRAINT uk_2rx408m3ausajsegv516g1j2t UNIQUE (nip);
 \   ALTER TABLE ONLY public.doc_identification_pp DROP CONSTRAINT uk_2rx408m3ausajsegv516g1j2t;
       public            postgres    false    219            I           2606    27745 '   firstlogin uk_3vgvn3t621yinjr9q51057hag 
   CONSTRAINT     e   ALTER TABLE ONLY public.firstlogin
    ADD CONSTRAINT uk_3vgvn3t621yinjr9q51057hag UNIQUE (user_id);
 Q   ALTER TABLE ONLY public.firstlogin DROP CONSTRAINT uk_3vgvn3t621yinjr9q51057hag;
       public            postgres    false    220            g           2606    27757 -   personne_physique uk_4iya1258w3ltum5xa3d07b65 
   CONSTRAINT     k   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT uk_4iya1258w3ltum5xa3d07b65 UNIQUE (user_id);
 W   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT uk_4iya1258w3ltum5xa3d07b65;
       public            postgres    false    234                       2606    27753 %   jhi_user uk_9y0frpqnmqe7y6mk109vw3246 
   CONSTRAINT     a   ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT uk_9y0frpqnmqe7y6mk109vw3246 UNIQUE (login);
 O   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT uk_9y0frpqnmqe7y6mk109vw3246;
       public            postgres    false    203                       2606    27751 %   jhi_user uk_bycanyquvi09q7fh5pgxrqnku 
   CONSTRAINT     a   ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT uk_bycanyquvi09q7fh5pgxrqnku UNIQUE (email);
 O   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT uk_bycanyquvi09q7fh5pgxrqnku;
       public            postgres    false    203            ?           2606    27761 %   user_otp uk_d5uugwrclfg2tf4imweyb854w 
   CONSTRAINT     c   ALTER TABLE ONLY public.user_otp
    ADD CONSTRAINT uk_d5uugwrclfg2tf4imweyb854w UNIQUE (user_id);
 O   ALTER TABLE ONLY public.user_otp DROP CONSTRAINT uk_d5uugwrclfg2tf4imweyb854w;
       public            postgres    false    247            3           2606    27749 ,   immatriculation uk_dufyexxdl722an4wp4tm346bh 
   CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT uk_dufyexxdl722an4wp4tm346bh UNIQUE (certificat_immatriculation_id);
 V   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT uk_dufyexxdl722an4wp4tm346bh;
       public            postgres    false    215            i           2606    27755 .   personne_physique uk_i2nymqbfh7yc38925cmb9od7j 
   CONSTRAINT     z   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT uk_i2nymqbfh7yc38925cmb9od7j UNIQUE (doc_identification_id);
 X   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT uk_i2nymqbfh7yc38925cmb9od7j;
       public            postgres    false    234            ;           2606    27739 2   doc_identification_pm uk_mch2eopuv9b2u5kxt430nc8e6 
   CONSTRAINT     x   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT uk_mch2eopuv9b2u5kxt430nc8e6 UNIQUE (organisation_id);
 \   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT uk_mch2eopuv9b2u5kxt430nc8e6;
       public            postgres    false    217            M           2606    27747 (   image_table uk_ohi3b5py4m1qi7r2ynxkwfln8 
   CONSTRAINT     d   ALTER TABLE ONLY public.image_table
    ADD CONSTRAINT uk_ohi3b5py4m1qi7r2ynxkwfln8 UNIQUE (orgid);
 R   ALTER TABLE ONLY public.image_table DROP CONSTRAINT uk_ohi3b5py4m1qi7r2ynxkwfln8;
       public            postgres    false    222            =           2606    27741 2   doc_identification_pm uk_paqaobq0d9qlyyl3iuj53hjv3 
   CONSTRAINT     {   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT uk_paqaobq0d9qlyyl3iuj53hjv3 UNIQUE (personne_morale_id);
 \   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT uk_paqaobq0d9qlyyl3iuj53hjv3;
       public            postgres    false    217            ?           2606    27737 2   doc_identification_pm uk_ql7fck1ccsuew1ncrwjeufvgt 
   CONSTRAINT     s   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT uk_ql7fck1ccsuew1ncrwjeufvgt UNIQUE (numero_ifu);
 \   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT uk_ql7fck1ccsuew1ncrwjeufvgt;
       public            postgres    false    217            ?           2606    27759 +   user_device_id uk_qr35awp8k4leyflo67otd5170 
   CONSTRAINT     i   ALTER TABLE ONLY public.user_device_id
    ADD CONSTRAINT uk_qr35awp8k4leyflo67otd5170 UNIQUE (user_id);
 U   ALTER TABLE ONLY public.user_device_id DROP CONSTRAINT uk_qr35awp8k4leyflo67otd5170;
       public            postgres    false    246            '           2606    27735 $   carte_w uk_qu780dewvahcfh32xbwwtok17 
   CONSTRAINT     j   ALTER TABLE ONLY public.carte_w
    ADD CONSTRAINT uk_qu780dewvahcfh32xbwwtok17 UNIQUE (organisation_id);
 N   ALTER TABLE ONLY public.carte_w DROP CONSTRAINT uk_qu780dewvahcfh32xbwwtok17;
       public            postgres    false    210            ?           2606    27691 "   user_device_id user_device_id_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.user_device_id
    ADD CONSTRAINT user_device_id_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.user_device_id DROP CONSTRAINT user_device_id_pkey;
       public            postgres    false    246            ?           2606    27696    user_otp user_otp_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.user_otp
    ADD CONSTRAINT user_otp_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.user_otp DROP CONSTRAINT user_otp_pkey;
       public            postgres    false    247            5           2606    27481 @   immatriculation ux_immatriculation_certificat_immatriculation_id 
   CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT ux_immatriculation_certificat_immatriculation_id UNIQUE (certificat_immatriculation_id);
 j   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT ux_immatriculation_certificat_immatriculation_id;
       public            postgres    false    215                       2606    27385    jhi_user ux_user_email 
   CONSTRAINT     R   ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_email UNIQUE (email);
 @   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT ux_user_email;
       public            postgres    false    203                       2606    27383    jhi_user ux_user_login 
   CONSTRAINT     R   ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_login UNIQUE (login);
 @   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT ux_user_login;
       public            postgres    false    203            ?           2606    27712 (   vehicule_occasion vehicule_occasion_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.vehicule_occasion
    ADD CONSTRAINT vehicule_occasion_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.vehicule_occasion DROP CONSTRAINT vehicule_occasion_pkey;
       public            postgres    false    249            ?           2606    27720 ,   vehicule_occasional vehicule_occasional_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.vehicule_occasional
    ADD CONSTRAINT vehicule_occasional_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.vehicule_occasional DROP CONSTRAINT vehicule_occasional_pkey;
       public            postgres    false    250            ?           2606    27704    vehicule vehicule_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT vehicule_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.vehicule DROP CONSTRAINT vehicule_pkey;
       public            postgres    false    248            ?           2606    27728 ,   vehicule_traversant vehicule_traversant_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.vehicule_traversant
    ADD CONSTRAINT vehicule_traversant_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.vehicule_traversant DROP CONSTRAINT vehicule_traversant_pkey;
       public            postgres    false    251            ?           2606    27733    vente vente_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.vente
    ADD CONSTRAINT vente_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.vente DROP CONSTRAINT vente_pkey;
       public            postgres    false    252                       1259    27437    idx_persistent_audit_event    INDEX     r   CREATE INDEX idx_persistent_audit_event ON public.jhi_persistent_audit_event USING btree (principal, event_date);
 .   DROP INDEX public.idx_persistent_audit_event;
       public            postgres    false    208    208            !           1259    27440    idx_persistent_audit_evt_data    INDEX     k   CREATE INDEX idx_persistent_audit_evt_data ON public.jhi_persistent_audit_evt_data USING btree (event_id);
 1   DROP INDEX public.idx_persistent_audit_evt_data;
       public            postgres    false    209            ?           2606    27882 2   info_commande_vehicule fk171jiv2w2xt37s8gq02ku26id    FK CONSTRAINT     ?   ALTER TABLE ONLY public.info_commande_vehicule
    ADD CONSTRAINT fk171jiv2w2xt37s8gq02ku26id FOREIGN KEY (marque_vehicule_id) REFERENCES public.marque_vehicule(id);
 \   ALTER TABLE ONLY public.info_commande_vehicule DROP CONSTRAINT fk171jiv2w2xt37s8gq02ku26id;
       public          postgres    false    225    231    3167            ?           2606    27907 3   livraison_carnet_souche fk1vsb88l8vco7vi1fvv2f5vokg    FK CONSTRAINT     ?   ALTER TABLE ONLY public.livraison_carnet_souche
    ADD CONSTRAINT fk1vsb88l8vco7vi1fvv2f5vokg FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 ]   ALTER TABLE ONLY public.livraison_carnet_souche DROP CONSTRAINT fk1vsb88l8vco7vi1fvv2f5vokg;
       public          postgres    false    233    227    3171            ?           2606    27897 .   jhi_user_authority fk290okww5jujghp4el5i7mgwu0    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk290okww5jujghp4el5i7mgwu0 FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 X   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT fk290okww5jujghp4el5i7mgwu0;
       public          postgres    false    203    3085    205            ?           2606    27887 9   jhi_persistent_audit_evt_data fk2ehnyx2si4tjd2nt4q7y40v8m    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data
    ADD CONSTRAINT fk2ehnyx2si4tjd2nt4q7y40v8m FOREIGN KEY (event_id) REFERENCES public.jhi_persistent_audit_event(event_id);
 c   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data DROP CONSTRAINT fk2ehnyx2si4tjd2nt4q7y40v8m;
       public          postgres    false    209    208    3104            ?           2606    28082 -   vehicule_occasion fk2p93kjjughxwwt4nu21mj69m2    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasion
    ADD CONSTRAINT fk2p93kjjughxwwt4nu21mj69m2 FOREIGN KEY (personne_physique_id) REFERENCES public.personne_physique(id);
 W   ALTER TABLE ONLY public.vehicule_occasion DROP CONSTRAINT fk2p93kjjughxwwt4nu21mj69m2;
       public          postgres    false    234    3173    249            ?           2606    28057 $   vehicule fk32fsfubfxv6yh0avnk556cyjm    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT fk32fsfubfxv6yh0avnk556cyjm FOREIGN KEY (stock_id) REFERENCES public.stock(id);
 N   ALTER TABLE ONLY public.vehicule DROP CONSTRAINT fk32fsfubfxv6yh0avnk556cyjm;
       public          postgres    false    3165    248    230            ?           2606    27772 +   carnet_a_souche fk38dugvcgpjn4d0ibd8b9wnhyq    FK CONSTRAINT     ?   ALTER TABLE ONLY public.carnet_a_souche
    ADD CONSTRAINT fk38dugvcgpjn4d0ibd8b9wnhyq FOREIGN KEY (type_carnet_id) REFERENCES public.type_carnet(id);
 U   ALTER TABLE ONLY public.carnet_a_souche DROP CONSTRAINT fk38dugvcgpjn4d0ibd8b9wnhyq;
       public          postgres    false    3197    244    207            ?           2606    27797 2   commande_carnet_souche fk39htk7lp8ew1e6k8p0e1se2v7    FK CONSTRAINT     ?   ALTER TABLE ONLY public.commande_carnet_souche
    ADD CONSTRAINT fk39htk7lp8ew1e6k8p0e1se2v7 FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 \   ALTER TABLE ONLY public.commande_carnet_souche DROP CONSTRAINT fk39htk7lp8ew1e6k8p0e1se2v7;
       public          postgres    false    214    3171    233            ?           2606    28097 /   vehicule_occasional fk3r6j5xxq4vf39m19nucv38gmo    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasional
    ADD CONSTRAINT fk3r6j5xxq4vf39m19nucv38gmo FOREIGN KEY (personne_morale_id) REFERENCES public.personne_morale(id);
 Y   ALTER TABLE ONLY public.vehicule_occasional DROP CONSTRAINT fk3r6j5xxq4vf39m19nucv38gmo;
       public          postgres    false    250    3157    226            ?           2606    27892 .   jhi_user_authority fk4psxl0jtx6nr7rhqbynr6itoc    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk4psxl0jtx6nr7rhqbynr6itoc FOREIGN KEY (authority_name) REFERENCES public.jhi_authority(name);
 X   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT fk4psxl0jtx6nr7rhqbynr6itoc;
       public          postgres    false    205    3095    204            ?           2606    27832 1   doc_identification_pp fk4uuf7hfded5f7js6osw2fjdsn    FK CONSTRAINT     ?   ALTER TABLE ONLY public.doc_identification_pp
    ADD CONSTRAINT fk4uuf7hfded5f7js6osw2fjdsn FOREIGN KEY (nation_id) REFERENCES public.nation(id);
 [   ALTER TABLE ONLY public.doc_identification_pp DROP CONSTRAINT fk4uuf7hfded5f7js6osw2fjdsn;
       public          postgres    false    232    3169    219            ?           2606    28012     stock fk5ldim7fvbdbbr3fs01jkm1ic    FK CONSTRAINT     ?   ALTER TABLE ONLY public.stock
    ADD CONSTRAINT fk5ldim7fvbdbbr3fs01jkm1ic FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 J   ALTER TABLE ONLY public.stock DROP CONSTRAINT fk5ldim7fvbdbbr3fs01jkm1ic;
       public          postgres    false    230    233    3171            ?           2606    27942 (   organisation fk6xvavv6ni4nqhnys32u6esf6u    FK CONSTRAINT     ?   ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT fk6xvavv6ni4nqhnys32u6esf6u FOREIGN KEY (pere_id) REFERENCES public.organisation(id);
 R   ALTER TABLE ONLY public.organisation DROP CONSTRAINT fk6xvavv6ni4nqhnys32u6esf6u;
       public          postgres    false    233    233    3171            ?           2606    27987 2   plaque_immatriculation fk764m9l8eujv3egr439xngnkb6    FK CONSTRAINT     ?   ALTER TABLE ONLY public.plaque_immatriculation
    ADD CONSTRAINT fk764m9l8eujv3egr439xngnkb6 FOREIGN KEY (vehicule_id) REFERENCES public.vehicule(id);
 \   ALTER TABLE ONLY public.plaque_immatriculation DROP CONSTRAINT fk764m9l8eujv3egr439xngnkb6;
       public          postgres    false    237    248    3209            ?           2606    28027 '   type_carnet fk78gobawlnb88m814gb3balk84    FK CONSTRAINT     ?   ALTER TABLE ONLY public.type_carnet
    ADD CONSTRAINT fk78gobawlnb88m814gb3balk84 FOREIGN KEY (type_vehicule_id) REFERENCES public.type_vehicule(id);
 Q   ALTER TABLE ONLY public.type_carnet DROP CONSTRAINT fk78gobawlnb88m814gb3balk84;
       public          postgres    false    244    3199    245            ?           2606    27802 2   commande_carnet_souche fk7syk53k3vkd4kxla0mag8aidm    FK CONSTRAINT     ?   ALTER TABLE ONLY public.commande_carnet_souche
    ADD CONSTRAINT fk7syk53k3vkd4kxla0mag8aidm FOREIGN KEY (supernet_id) REFERENCES public.organisation(id);
 \   ALTER TABLE ONLY public.commande_carnet_souche DROP CONSTRAINT fk7syk53k3vkd4kxla0mag8aidm;
       public          postgres    false    214    233    3171            ?           2606    27952 (   organisation fk7u9s12rurc9bp85hmg2rlkom1    FK CONSTRAINT     ?   ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT fk7u9s12rurc9bp85hmg2rlkom1 FOREIGN KEY (type_organisation_id) REFERENCES public.type_organisation(id);
 R   ALTER TABLE ONLY public.organisation DROP CONSTRAINT fk7u9s12rurc9bp85hmg2rlkom1;
       public          postgres    false    233    235    3179            ?           2606    28022 :   type_acteur_type_organisations fk7vcxjgfrpvkawvi1ayal1qakw    FK CONSTRAINT     ?   ALTER TABLE ONLY public.type_acteur_type_organisations
    ADD CONSTRAINT fk7vcxjgfrpvkawvi1ayal1qakw FOREIGN KEY (type_acteur_id) REFERENCES public.type_acteur(id);
 d   ALTER TABLE ONLY public.type_acteur_type_organisations DROP CONSTRAINT fk7vcxjgfrpvkawvi1ayal1qakw;
       public          postgres    false    243    223    3151            ?           2606    27947 '   organisation fk99yv31iuod1qpjmhsxkhl0p8    FK CONSTRAINT     ?   ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT fk99yv31iuod1qpjmhsxkhl0p8 FOREIGN KEY (type_acteur_id) REFERENCES public.type_acteur(id);
 Q   ALTER TABLE ONLY public.organisation DROP CONSTRAINT fk99yv31iuod1qpjmhsxkhl0p8;
       public          postgres    false    3151    223    233            ?           2606    27862 +   immatriculation fk9fbhkri6ke85pfatd8ox5sbc6    FK CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT fk9fbhkri6ke85pfatd8ox5sbc6 FOREIGN KEY (vehicule_id) REFERENCES public.vehicule(id);
 U   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT fk9fbhkri6ke85pfatd8ox5sbc6;
       public          postgres    false    215    3209    248            ?           2606    27787 )   collaboration fk9uveh63oksik257faslyee200    FK CONSTRAINT     ?   ALTER TABLE ONLY public.collaboration
    ADD CONSTRAINT fk9uveh63oksik257faslyee200 FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 S   ALTER TABLE ONLY public.collaboration DROP CONSTRAINT fk9uveh63oksik257faslyee200;
       public          postgres    false    233    3171    213            ?           2606    27401 $   jhi_user_authority fk_authority_name    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES public.jhi_authority(name);
 N   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT fk_authority_name;
       public          postgres    false    3095    205    204            ?           2606    27447 8   jhi_persistent_audit_evt_data fk_evt_pers_audit_evt_data    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data
    ADD CONSTRAINT fk_evt_pers_audit_evt_data FOREIGN KEY (event_id) REFERENCES public.jhi_persistent_audit_event(event_id);
 b   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data DROP CONSTRAINT fk_evt_pers_audit_evt_data;
       public          postgres    false    209    208    3104            ?           2606    27409    jhi_user_authority fk_user_id    FK CONSTRAINT        ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 G   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT fk_user_id;
       public          postgres    false    205    3085    203            ?           2606    27416 -   jhi_persistent_token fk_user_persistent_token    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jhi_persistent_token
    ADD CONSTRAINT fk_user_persistent_token FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 W   ALTER TABLE ONLY public.jhi_persistent_token DROP CONSTRAINT fk_user_persistent_token;
       public          postgres    false    3085    203    206            ?           2606    27932 (   organisation fka85psf5wti2hfv8u3txjpfsin    FK CONSTRAINT     ?   ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT fka85psf5wti2hfv8u3txjpfsin FOREIGN KEY (gerant_id) REFERENCES public.personne_physique(id);
 R   ALTER TABLE ONLY public.organisation DROP CONSTRAINT fka85psf5wti2hfv8u3txjpfsin;
       public          postgres    false    234    233    3173            ?           2606    28017 :   type_acteur_type_organisations fkafjhxt09c0e3pjkpfutbifhb5    FK CONSTRAINT     ?   ALTER TABLE ONLY public.type_acteur_type_organisations
    ADD CONSTRAINT fkafjhxt09c0e3pjkpfutbifhb5 FOREIGN KEY (type_organisations_id) REFERENCES public.type_organisation(id);
 d   ALTER TABLE ONLY public.type_acteur_type_organisations DROP CONSTRAINT fkafjhxt09c0e3pjkpfutbifhb5;
       public          postgres    false    243    235    3179            ?           2606    28092 /   vehicule_occasional fkau6lcgouoiuy5ga6f6wy9yrt3    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasional
    ADD CONSTRAINT fkau6lcgouoiuy5ga6f6wy9yrt3 FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 Y   ALTER TABLE ONLY public.vehicule_occasional DROP CONSTRAINT fkau6lcgouoiuy5ga6f6wy9yrt3;
       public          postgres    false    250    233    3171            ?           2606    27792 )   collaboration fkbmtgux7aofv9pw321ew9euhmm    FK CONSTRAINT     ?   ALTER TABLE ONLY public.collaboration
    ADD CONSTRAINT fkbmtgux7aofv9pw321ew9euhmm FOREIGN KEY (revendeur_id) REFERENCES public.organisation(id);
 S   ALTER TABLE ONLY public.collaboration DROP CONSTRAINT fkbmtgux7aofv9pw321ew9euhmm;
       public          postgres    false    3171    233    213            ?           2606    27982 2   plaque_immatriculation fkbvg10k0o7ikot46bvpgqd6w5w    FK CONSTRAINT     ?   ALTER TABLE ONLY public.plaque_immatriculation
    ADD CONSTRAINT fkbvg10k0o7ikot46bvpgqd6w5w FOREIGN KEY (certificat_immatriculation_id) REFERENCES public.certificat_immatriculation(id);
 \   ALTER TABLE ONLY public.plaque_immatriculation DROP CONSTRAINT fkbvg10k0o7ikot46bvpgqd6w5w;
       public          postgres    false    237    212    3115            ?           2606    28032 -   type_organisation fkc6oagtj3bq5dyj15m0l7fb1ah    FK CONSTRAINT     ?   ALTER TABLE ONLY public.type_organisation
    ADD CONSTRAINT fkc6oagtj3bq5dyj15m0l7fb1ah FOREIGN KEY (categorie_organisation_id) REFERENCES public.categorie_organisation(id);
 W   ALTER TABLE ONLY public.type_organisation DROP CONSTRAINT fkc6oagtj3bq5dyj15m0l7fb1ah;
       public          postgres    false    235    3113    211            ?           2606    27777 #   carte_w fkcl0m7t8jpgqkl5hemtix118b0    FK CONSTRAINT     ?   ALTER TABLE ONLY public.carte_w
    ADD CONSTRAINT fkcl0m7t8jpgqkl5hemtix118b0 FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 M   ALTER TABLE ONLY public.carte_w DROP CONSTRAINT fkcl0m7t8jpgqkl5hemtix118b0;
       public          postgres    false    210    233    3171            ?           2606    28047 $   vehicule fkd50gpu4108hb2jn575a8ln3es    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT fkd50gpu4108hb2jn575a8ln3es FOREIGN KEY (livraison_vehicule_id) REFERENCES public.livraison_vehicule(id);
 N   ALTER TABLE ONLY public.vehicule DROP CONSTRAINT fkd50gpu4108hb2jn575a8ln3es;
       public          postgres    false    228    248    3161            ?           2606    27822 1   doc_identification_pm fkdbgbkx5vjkjby7857gsvnm4r8    FK CONSTRAINT     ?   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT fkdbgbkx5vjkjby7857gsvnm4r8 FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 [   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT fkdbgbkx5vjkjby7857gsvnm4r8;
       public          postgres    false    233    217    3171            ?           2606    28072 -   vehicule_occasion fkdrinoxivvhyl799epwg08emct    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasion
    ADD CONSTRAINT fkdrinoxivvhyl799epwg08emct FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 W   ALTER TABLE ONLY public.vehicule_occasion DROP CONSTRAINT fkdrinoxivvhyl799epwg08emct;
       public          postgres    false    233    249    3171            ?           2606    27867 9   info_commande_carnet_a_souche fkdx1dd6555icpevwr4gnjtk6m8    FK CONSTRAINT     ?   ALTER TABLE ONLY public.info_commande_carnet_a_souche
    ADD CONSTRAINT fkdx1dd6555icpevwr4gnjtk6m8 FOREIGN KEY (commande_carnet_souche_id) REFERENCES public.commande_carnet_souche(id);
 c   ALTER TABLE ONLY public.info_commande_carnet_a_souche DROP CONSTRAINT fkdx1dd6555icpevwr4gnjtk6m8;
       public          postgres    false    214    3119    224            ?           2606    27902 3   livraison_carnet_souche fkepi6uv415stgll9imj2v9q9nv    FK CONSTRAINT     ?   ALTER TABLE ONLY public.livraison_carnet_souche
    ADD CONSTRAINT fkepi6uv415stgll9imj2v9q9nv FOREIGN KEY (commande_carnet_souche_id) REFERENCES public.commande_carnet_souche(id);
 ]   ALTER TABLE ONLY public.livraison_carnet_souche DROP CONSTRAINT fkepi6uv415stgll9imj2v9q9nv;
       public          postgres    false    3119    227    214            ?           2606    27957 -   personne_physique fkf5eebbmvmjxiuqhyqrjs7ytku    FK CONSTRAINT     ?   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT fkf5eebbmvmjxiuqhyqrjs7ytku FOREIGN KEY (doc_identification_id) REFERENCES public.doc_identification_pp(id);
 W   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT fkf5eebbmvmjxiuqhyqrjs7ytku;
       public          postgres    false    234    219    3139            ?           2606    28052 $   vehicule fkff19w3mhvxfk9n9k4l5jm7yoe    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT fkff19w3mhvxfk9n9k4l5jm7yoe FOREIGN KEY (marque_vehicule_id) REFERENCES public.marque_vehicule(id);
 N   ALTER TABLE ONLY public.vehicule DROP CONSTRAINT fkff19w3mhvxfk9n9k4l5jm7yoe;
       public          postgres    false    3167    248    231            ?           2606    27812 -   commande_vehicule fkfyvuxgpnwlooobjjcti5r546k    FK CONSTRAINT     ?   ALTER TABLE ONLY public.commande_vehicule
    ADD CONSTRAINT fkfyvuxgpnwlooobjjcti5r546k FOREIGN KEY (revendeur_id) REFERENCES public.organisation(id);
 W   ALTER TABLE ONLY public.commande_vehicule DROP CONSTRAINT fkfyvuxgpnwlooobjjcti5r546k;
       public          postgres    false    3171    216    233            ?           2606    27972 -   personne_physique fkgmwcun2a3uafx1gqhg7nuf0sa    FK CONSTRAINT     ?   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT fkgmwcun2a3uafx1gqhg7nuf0sa FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 W   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT fkgmwcun2a3uafx1gqhg7nuf0sa;
       public          postgres    false    234    203    3085            ?           2606    27842 +   immatriculation fkgqtyh39266eyjjxw04ehenaq9    FK CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT fkgqtyh39266eyjjxw04ehenaq9 FOREIGN KEY (certificat_immatriculation_id) REFERENCES public.certificat_immatriculation(id);
 U   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT fkgqtyh39266eyjjxw04ehenaq9;
       public          postgres    false    215    3115    212            ?           2606    28062 $   vehicule fki080lhmoghy09o4jodon708sy    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT fki080lhmoghy09o4jodon708sy FOREIGN KEY (type_vehicule_id) REFERENCES public.type_vehicule(id);
 N   ALTER TABLE ONLY public.vehicule DROP CONSTRAINT fki080lhmoghy09o4jodon708sy;
       public          postgres    false    248    3199    245            ?           2606    28117 /   vehicule_traversant fkj0n3nh8yy2kygfx7hi6imqt88    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_traversant
    ADD CONSTRAINT fkj0n3nh8yy2kygfx7hi6imqt88 FOREIGN KEY (personne_physique_id) REFERENCES public.personne_physique(id);
 Y   ALTER TABLE ONLY public.vehicule_traversant DROP CONSTRAINT fkj0n3nh8yy2kygfx7hi6imqt88;
       public          postgres    false    3173    251    234            ?           2606    28067 $   vehicule fkjb2icro1jh4c1114ehfwjynr4    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule
    ADD CONSTRAINT fkjb2icro1jh4c1114ehfwjynr4 FOREIGN KEY (vente_id) REFERENCES public.vente(id);
 N   ALTER TABLE ONLY public.vehicule DROP CONSTRAINT fkjb2icro1jh4c1114ehfwjynr4;
       public          postgres    false    3217    248    252            ?           2606    27857 +   immatriculation fkjbjdmhhu1cjhy0ejre6rdwbiq    FK CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT fkjbjdmhhu1cjhy0ejre6rdwbiq FOREIGN KEY (personne_physique_id) REFERENCES public.personne_physique(id);
 U   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT fkjbjdmhhu1cjhy0ejre6rdwbiq;
       public          postgres    false    3173    215    234            ?           2606    27852 +   immatriculation fkjttdr2lkbe5d600sjbw1rnpd0    FK CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT fkjttdr2lkbe5d600sjbw1rnpd0 FOREIGN KEY (personne_morale_id) REFERENCES public.personne_morale(id);
 U   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT fkjttdr2lkbe5d600sjbw1rnpd0;
       public          postgres    false    3157    215    226            ?           2606    27997 "   profil fkjuqqdx6u16udmj05tbg2ywn1c    FK CONSTRAINT     ?   ALTER TABLE ONLY public.profil
    ADD CONSTRAINT fkjuqqdx6u16udmj05tbg2ywn1c FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 L   ALTER TABLE ONLY public.profil DROP CONSTRAINT fkjuqqdx6u16udmj05tbg2ywn1c;
       public          postgres    false    240    233    3171            ?           2606    28002 ,   profil_authority fkk4idrlj7ks64c1p4qr7lic9s9    FK CONSTRAINT     ?   ALTER TABLE ONLY public.profil_authority
    ADD CONSTRAINT fkk4idrlj7ks64c1p4qr7lic9s9 FOREIGN KEY (authority_name) REFERENCES public.jhi_authority(name);
 V   ALTER TABLE ONLY public.profil_authority DROP CONSTRAINT fkk4idrlj7ks64c1p4qr7lic9s9;
       public          postgres    false    241    3095    204            ?           2606    27847 +   immatriculation fkkt3u4pja33esyfr6h8tw30sps    FK CONSTRAINT     ?   ALTER TABLE ONLY public.immatriculation
    ADD CONSTRAINT fkkt3u4pja33esyfr6h8tw30sps FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 U   ALTER TABLE ONLY public.immatriculation DROP CONSTRAINT fkkt3u4pja33esyfr6h8tw30sps;
       public          postgres    false    215    3171    233            ?           2606    27817 1   doc_identification_pm fkku7386xbktvexs0p6ouqmb4nv    FK CONSTRAINT     ?   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT fkku7386xbktvexs0p6ouqmb4nv FOREIGN KEY (nation_id) REFERENCES public.nation(id);
 [   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT fkku7386xbktvexs0p6ouqmb4nv;
       public          postgres    false    232    217    3169            ?           2606    27922 .   livraison_vehicule fkl4w039hntfk7c4kqw67v6vofd    FK CONSTRAINT     ?   ALTER TABLE ONLY public.livraison_vehicule
    ADD CONSTRAINT fkl4w039hntfk7c4kqw67v6vofd FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 X   ALTER TABLE ONLY public.livraison_vehicule DROP CONSTRAINT fkl4w039hntfk7c4kqw67v6vofd;
       public          postgres    false    233    228    3171            ?           2606    27837 &   firstlogin fkljl2mlf1u8qlb4f3peashuo0e    FK CONSTRAINT     ?   ALTER TABLE ONLY public.firstlogin
    ADD CONSTRAINT fkljl2mlf1u8qlb4f3peashuo0e FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 P   ALTER TABLE ONLY public.firstlogin DROP CONSTRAINT fkljl2mlf1u8qlb4f3peashuo0e;
       public          postgres    false    3085    203    220            ?           2606    27872 9   info_commande_carnet_a_souche fklnhl315iblw0wgv04rpsj1w23    FK CONSTRAINT     ?   ALTER TABLE ONLY public.info_commande_carnet_a_souche
    ADD CONSTRAINT fklnhl315iblw0wgv04rpsj1w23 FOREIGN KEY (type_carnet_id) REFERENCES public.type_carnet(id);
 c   ALTER TABLE ONLY public.info_commande_carnet_a_souche DROP CONSTRAINT fklnhl315iblw0wgv04rpsj1w23;
       public          postgres    false    3197    224    244            ?           2606    27877 2   info_commande_vehicule fklr72f410dsx1whv388lhdovg4    FK CONSTRAINT     ?   ALTER TABLE ONLY public.info_commande_vehicule
    ADD CONSTRAINT fklr72f410dsx1whv388lhdovg4 FOREIGN KEY (commande_vehicule_id) REFERENCES public.commande_vehicule(id);
 \   ALTER TABLE ONLY public.info_commande_vehicule DROP CONSTRAINT fklr72f410dsx1whv388lhdovg4;
       public          postgres    false    3127    225    216            ?           2606    28122 !   vente fkm5yeljs1wloarty8mcl3aj5ed    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vente
    ADD CONSTRAINT fkm5yeljs1wloarty8mcl3aj5ed FOREIGN KEY (personne_morale_id) REFERENCES public.personne_morale(id);
 K   ALTER TABLE ONLY public.vente DROP CONSTRAINT fkm5yeljs1wloarty8mcl3aj5ed;
       public          postgres    false    252    3157    226            ?           2606    28102 /   vehicule_occasional fkm67bbgwojeuxyeqld1vr7dme7    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasional
    ADD CONSTRAINT fkm67bbgwojeuxyeqld1vr7dme7 FOREIGN KEY (personne_physique_id) REFERENCES public.personne_physique(id);
 Y   ALTER TABLE ONLY public.vehicule_occasional DROP CONSTRAINT fkm67bbgwojeuxyeqld1vr7dme7;
       public          postgres    false    3173    250    234            ?           2606    27937 (   organisation fkmnrx687cmrsrf9bxk3ct6ntd2    FK CONSTRAINT     ?   ALTER TABLE ONLY public.organisation
    ADD CONSTRAINT fkmnrx687cmrsrf9bxk3ct6ntd2 FOREIGN KEY (organisation_localite_id) REFERENCES public.organisation_localite(id);
 R   ALTER TABLE ONLY public.organisation DROP CONSTRAINT fkmnrx687cmrsrf9bxk3ct6ntd2;
       public          postgres    false    3137    218    233            ?           2606    27962 -   personne_physique fkn09bj2erwht5enalm12v8cxk1    FK CONSTRAINT     ?   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT fkn09bj2erwht5enalm12v8cxk1 FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 W   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT fkn09bj2erwht5enalm12v8cxk1;
       public          postgres    false    233    3171    234            ?           2606    27827 1   doc_identification_pm fkn2smol69c9ux048t2i02udh92    FK CONSTRAINT     ?   ALTER TABLE ONLY public.doc_identification_pm
    ADD CONSTRAINT fkn2smol69c9ux048t2i02udh92 FOREIGN KEY (personne_morale_id) REFERENCES public.personne_morale(id);
 [   ALTER TABLE ONLY public.doc_identification_pm DROP CONSTRAINT fkn2smol69c9ux048t2i02udh92;
       public          postgres    false    3157    226    217            ?           2606    28107 /   vehicule_traversant fknhdrfc2pkwm0tcs54vp5ukwgf    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_traversant
    ADD CONSTRAINT fknhdrfc2pkwm0tcs54vp5ukwgf FOREIGN KEY (organisation_id) REFERENCES public.organisation(id);
 Y   ALTER TABLE ONLY public.vehicule_traversant DROP CONSTRAINT fknhdrfc2pkwm0tcs54vp5ukwgf;
       public          postgres    false    251    3171    233            ?           2606    28112 /   vehicule_traversant fkny0mbv0y8tuwirdy36oxujpud    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_traversant
    ADD CONSTRAINT fkny0mbv0y8tuwirdy36oxujpud FOREIGN KEY (personne_morale_id) REFERENCES public.personne_morale(id);
 Y   ALTER TABLE ONLY public.vehicule_traversant DROP CONSTRAINT fkny0mbv0y8tuwirdy36oxujpud;
       public          postgres    false    251    226    3157            ?           2606    27927 .   livraison_vehicule fko1q2j7u9k7fu2yh3ukfdynfa1    FK CONSTRAINT     ?   ALTER TABLE ONLY public.livraison_vehicule
    ADD CONSTRAINT fko1q2j7u9k7fu2yh3ukfdynfa1 FOREIGN KEY (revendeur_id) REFERENCES public.organisation(id);
 X   ALTER TABLE ONLY public.livraison_vehicule DROP CONSTRAINT fko1q2j7u9k7fu2yh3ukfdynfa1;
       public          postgres    false    228    233    3171            ?           2606    27977 )   plaque_garage fko7ryo5q64mceva5nie48dyamf    FK CONSTRAINT     ?   ALTER TABLE ONLY public.plaque_garage
    ADD CONSTRAINT fko7ryo5q64mceva5nie48dyamf FOREIGN KEY (cartew_id) REFERENCES public.carte_w(id);
 S   ALTER TABLE ONLY public.plaque_garage DROP CONSTRAINT fko7ryo5q64mceva5nie48dyamf;
       public          postgres    false    210    236    3109            ?           2606    27762 +   carnet_a_souche fko8wlmv0f4g7o6kmjpm1vl2rqi    FK CONSTRAINT     ?   ALTER TABLE ONLY public.carnet_a_souche
    ADD CONSTRAINT fko8wlmv0f4g7o6kmjpm1vl2rqi FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 U   ALTER TABLE ONLY public.carnet_a_souche DROP CONSTRAINT fko8wlmv0f4g7o6kmjpm1vl2rqi;
       public          postgres    false    233    207    3171            ?           2606    28087 /   vehicule_occasional fkoffxeyjd97i74thyjgowah4fw    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasional
    ADD CONSTRAINT fkoffxeyjd97i74thyjgowah4fw FOREIGN KEY (cartew_id) REFERENCES public.carte_w(id);
 Y   ALTER TABLE ONLY public.vehicule_occasional DROP CONSTRAINT fkoffxeyjd97i74thyjgowah4fw;
       public          postgres    false    210    250    3109            ?           2606    28127 !   vente fkomkcfrtqojp3gf13knp2n4qbw    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vente
    ADD CONSTRAINT fkomkcfrtqojp3gf13knp2n4qbw FOREIGN KEY (personne_physique_id) REFERENCES public.personne_physique(id);
 K   ALTER TABLE ONLY public.vente DROP CONSTRAINT fkomkcfrtqojp3gf13knp2n4qbw;
       public          postgres    false    252    234    3173            ?           2606    27767 +   carnet_a_souche fkosd8y9ieuf89u48phfl81fue5    FK CONSTRAINT     ?   ALTER TABLE ONLY public.carnet_a_souche
    ADD CONSTRAINT fkosd8y9ieuf89u48phfl81fue5 FOREIGN KEY (livraison_carnet_souche_id) REFERENCES public.livraison_carnet_souche(id);
 U   ALTER TABLE ONLY public.carnet_a_souche DROP CONSTRAINT fkosd8y9ieuf89u48phfl81fue5;
       public          postgres    false    227    3159    207            ?           2606    27917 .   livraison_vehicule fkoy7ddsm4yi1ngf40al7cetv75    FK CONSTRAINT     ?   ALTER TABLE ONLY public.livraison_vehicule
    ADD CONSTRAINT fkoy7ddsm4yi1ngf40al7cetv75 FOREIGN KEY (commande_vehicule_id) REFERENCES public.commande_vehicule(id);
 X   ALTER TABLE ONLY public.livraison_vehicule DROP CONSTRAINT fkoy7ddsm4yi1ngf40al7cetv75;
       public          postgres    false    3127    216    228            ?           2606    28132 !   vente fkp9thxfkw4a18njsbex8kt082y    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vente
    ADD CONSTRAINT fkp9thxfkw4a18njsbex8kt082y FOREIGN KEY (revendeur_id) REFERENCES public.organisation(id);
 K   ALTER TABLE ONLY public.vente DROP CONSTRAINT fkp9thxfkw4a18njsbex8kt082y;
       public          postgres    false    233    252    3171            ?           2606    28007 ,   profil_authority fkprgrvsim4oab2g9mlq2apavs9    FK CONSTRAINT     ?   ALTER TABLE ONLY public.profil_authority
    ADD CONSTRAINT fkprgrvsim4oab2g9mlq2apavs9 FOREIGN KEY (profils_id) REFERENCES public.profil(id);
 V   ALTER TABLE ONLY public.profil_authority DROP CONSTRAINT fkprgrvsim4oab2g9mlq2apavs9;
       public          postgres    false    241    240    3189            ?           2606    28042 $   user_otp fkq8r85420ywgy8ym7yjrqk2hjr    FK CONSTRAINT     ?   ALTER TABLE ONLY public.user_otp
    ADD CONSTRAINT fkq8r85420ywgy8ym7yjrqk2hjr FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 N   ALTER TABLE ONLY public.user_otp DROP CONSTRAINT fkq8r85420ywgy8ym7yjrqk2hjr;
       public          postgres    false    247    3085    203            ?           2606    27967 -   personne_physique fkqd9sr7lsm4vsnvxbo2f84yi22    FK CONSTRAINT     ?   ALTER TABLE ONLY public.personne_physique
    ADD CONSTRAINT fkqd9sr7lsm4vsnvxbo2f84yi22 FOREIGN KEY (profil_id) REFERENCES public.profil(id);
 W   ALTER TABLE ONLY public.personne_physique DROP CONSTRAINT fkqd9sr7lsm4vsnvxbo2f84yi22;
       public          postgres    false    234    240    3189            ?           2606    28037 *   user_device_id fkqf94bj3om28j1lnebad39x0g1    FK CONSTRAINT     ?   ALTER TABLE ONLY public.user_device_id
    ADD CONSTRAINT fkqf94bj3om28j1lnebad39x0g1 FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);
 T   ALTER TABLE ONLY public.user_device_id DROP CONSTRAINT fkqf94bj3om28j1lnebad39x0g1;
       public          postgres    false    3085    246    203            ?           2606    27782 6   certificat_immatriculation fkraek7uqxkd2p2mv94rhhk3p09    FK CONSTRAINT     ?   ALTER TABLE ONLY public.certificat_immatriculation
    ADD CONSTRAINT fkraek7uqxkd2p2mv94rhhk3p09 FOREIGN KEY (carnetasouche_id) REFERENCES public.carnet_a_souche(id);
 `   ALTER TABLE ONLY public.certificat_immatriculation DROP CONSTRAINT fkraek7uqxkd2p2mv94rhhk3p09;
       public          postgres    false    207    3101    212            ?           2606    27992 '   pose_plaque fkrb4ig3ac94l1oqc2pt9mlfw01    FK CONSTRAINT     ?   ALTER TABLE ONLY public.pose_plaque
    ADD CONSTRAINT fkrb4ig3ac94l1oqc2pt9mlfw01 FOREIGN KEY (revendeur_id) REFERENCES public.organisation(id);
 Q   ALTER TABLE ONLY public.pose_plaque DROP CONSTRAINT fkrb4ig3ac94l1oqc2pt9mlfw01;
       public          postgres    false    238    233    3171            ?           2606    27807 -   commande_vehicule fkrevqjd3q1h1mf58dcdlta4t8j    FK CONSTRAINT     ?   ALTER TABLE ONLY public.commande_vehicule
    ADD CONSTRAINT fkrevqjd3q1h1mf58dcdlta4t8j FOREIGN KEY (concessionnaire_id) REFERENCES public.organisation(id);
 W   ALTER TABLE ONLY public.commande_vehicule DROP CONSTRAINT fkrevqjd3q1h1mf58dcdlta4t8j;
       public          postgres    false    216    233    3171            ?           2606    28077 -   vehicule_occasion fkso5m4phy0yugyg1hf8l2xisev    FK CONSTRAINT     ?   ALTER TABLE ONLY public.vehicule_occasion
    ADD CONSTRAINT fkso5m4phy0yugyg1hf8l2xisev FOREIGN KEY (personne_morale_id) REFERENCES public.personne_morale(id);
 W   ALTER TABLE ONLY public.vehicule_occasion DROP CONSTRAINT fkso5m4phy0yugyg1hf8l2xisev;
       public          postgres    false    249    3157    226            ?           2606    27912 2   livraison_carnet_souche fkuy8jsb7b9x6wul02o2lbee81    FK CONSTRAINT     ?   ALTER TABLE ONLY public.livraison_carnet_souche
    ADD CONSTRAINT fkuy8jsb7b9x6wul02o2lbee81 FOREIGN KEY (supernet_id) REFERENCES public.organisation(id);
 \   ALTER TABLE ONLY public.livraison_carnet_souche DROP CONSTRAINT fkuy8jsb7b9x6wul02o2lbee81;
       public          postgres    false    233    227    3171            j      x?????? ? ?      m      x?????? ? ?      n   ?   x?m?1?0k?~????PP?9%'t??w??)??c8??H?vW;?????&O(?`?%??P
?6g0?~J??.<?2	?j??Fð?Ga?]}??q3???"d,	uAB??{?m????C?jL???.?U??pŻ??? ?N]      o      x?????? ? ?      p      x?????? ? ?      q      x?????? ? ?      s      x?????? ? ?      d   ?  x??Zێ??}??b>?#wW߽ȃa;?b׎?v6yX@???0??Y?ڍ?>%Y3&eQ?(?????SU}?(???ſ?ˇu??"T?\޽X??oJ???"???.-???l^?ʦ??|??g?^.
`?n9?2w??K?_r9s?)^????????Ma_??????c	??!?$X??P'l????&?B?Y?/??2?????]Z???.???b?gb{A?F?F?:?̯?S?cZ??8?xf?L?c܂????*????	?"?4?w?!??u????7?}U?͗S7m7i߉1~??%?_~J_??ۿ?C??%9q?̛?sZ???kU??nE???V???r??l?鈕?lv1g⊜Y??hs?3&?d???"2???#???O??0?????a˯'???坌?	z?A[,?+??L???º?hN??8'???u??2???Kl?2lؔժ?u??V?,޽????/?ޓ?r<H@??gF`?ف?LbV??	??d|^v??
?C?????????:Q?vEI????1O????9gS?V?IEZ>t?>?Ќa?tq]?F?(??l݆#"??@??(?)??Y??w??B??u??&e?,?_q?I7?Zl???͑68?????ݔ??E??
A???@??!A????o??????U?E٤^L ?殰mL?_d?Yk?:r:?]F9΅???U??|?7<?J?;>??|????-%???Y?B?u$?????)ǅ&F?Y4?e??P8v???ܑ???b??A?z??W?i?^??????}???H?y?(5e??/?V?
>?c?w??,?q??ټ??=?i?>(s?\Z??^?P?F?O?Vr??};\òa$m$/xWz?t?	?ZBf? d*s$?"Gc?fA??'⟲MZ????~Q??/?UhҦ?E*??@?/?PAq??2? tl??v?TԳ?C?渳w?O0??h??0Mܪv?G???S?ӎY??s????H??,????]??????u?|74)F??*!??AH??,??G?9T?"Ft?)u,%??Q?4?Ӣ??I??ŧ?p???i??M֫$???8?3O<e?Pp)?T??o|
?I	??J???Dr_??M~???]F????&:?)?]?<Oݻ4)1?0(N? 1ƌHݼ?!/Va^v?? ?B??U?G??uT???Jat??$?3?*βKZz???1?X?9???#٨?S???.??^W?UzWո? N2k?͎?R?BZ[ ??????ȥ)e??{??????l??L?8? ??z??\?;??(?
?ʨT?F2P?6??????z[?cA??|
?S|??!?י?}U5#d?w???j?@O1zr??H???fTD?y?(?w?^3???h&m??mq?)?y$j????8??????E?h?K\??k?ߎ?????1A?(1m?F??h?(+UR????DÕ?.?N??F???^????y~j?!????H????1{|6??????^ D5/DG>9!(??q???Q
??@??0???PXo-]@??????8e
???#H??B?+?,Ɛ,?(~.???$?q6la?~???V???0E??m??P?????꾹??????Z???NC?7%??QjҪ???`?lD?I?z}? ? *??T?Fms6@m?d????w:f??A0?o????4??????l7ėS?d?W??P???!?i???IwU]????~T??mm??AOM+??tC?^e????{?????jx??	??܉n`)g??????4hJX#
љ?ea??K????I\f?A2????>??g_??AТ??uZ?BtFk]$??<?W?sؾ???F???C᷅??Ry\%?4ߘ?TI? ???z?:?~[d???e? ? ʎ>T>F?????k????%0?8?iT??%???I?:???!W?Ƃ?LH?
??utH٥?yv?x%xoEv?30????X??m??ٳ? g??      c      x?3?L???"?=... U?      t   ?   x???=
B1?:9EN??$??t???FD??j??M?-?5[L?à

???Ϊ??q??v?ӵ??\oʑ??do ???9???u$?C?_?!???D,?)Rd)?H4H??"??&E;18?0????&9???p?V?I뢵??s?      v   ?   x?m??n?0??y&ۉ?p?*U??p???!????Y?r?	??g>??\?0??1???r?A?-[AY$???8t?????_?T?L9?w?z#????Cƌ??B_?????%?m?\???NqN??Ǚp?r?wg??^?S?8zra?????,[k$?F]?8?᷎l???8.,}u???m?;m?F???a\&]???]l???]????dJMZ????N
	8??????????UU? dVk?      w      x?????? ? ?      y      x?????? ? ?      r      x?????? ? ?      {      x?????? ? ?      |      x?????? ? ?      g   Z   x?-???0D??CQ2,??l?R??{???휥??7G?QM+#?T?8?<?????!ue????FQ>?????x?n]tچkpx1(      k     x????N?0?????6?~???Hb?D?fb?[?U@??????zQp?y?????1???0<????F? ?Ps????
J??????m]?Y]?l?/????-?;??Ki,??	??U??EU}QatC??~im?QŴ????ܒ2+$Qs5B%???a??EE4F??????	???~Zm????
?????ӱ낟??dq:?h?Z7?????R?HE??]x_S??Q?$B?v??ꔱIc?*??ԖR"???ܑ$I>i      l   y   x?-???0Ek{
O`?EZ#М?cA|??E$#1??D?O??4??l?p,%j???$4??򌊼H?-?b4???r?g/?q'?????w?ӱ??J?????A?Y??????{???0f      i      x?????? ? ?      f   ?  x???I??JF?Y??-z?I&3?Q@e?m?d*,?????~U,:??'????4禍
???~`????CX??LF????/t??Ƕ?G:??N?8?N?ZYL???x??_yxyR5- -?W???????|x#?WV幨??k??e?o?????-Cs?c:ֵ???J??Ӛ??)?Q???;??OB??????I??8??"-_1⍘??i?????,S3????(??s??8????
???.?????7Dڴ??V???;??????]?&?S??ҙ
k?#g?V??w?p꘳?Ґ???.??\?a?ڌP{q????~m,??+w??ʐ?I? s1Ӕ??.??J?.~??oW$?~???U??nn?Zd?#?M?e؈??}"Ņ??0??k?`W??4??$???&dʦ5??K??B"???O$???M? I???cN????pLH?sW@????Ӛ?b?<??B?????#N7־??z쐱??|??cy?%w?;B%Qk`??h#%C/[q$?Ӯ?r?o?("???s???5@?,wSc???Knx
??&?ho?CN????v??Vj??Xk
w???rd5?u?F6О?G???~?l???k?8\P?ҍͲ????&\??>?$??Ȼ ?iT???o?b&?6??ͣ-,??g!??XRJ>bǞ??2?U?m??1I??`(U?U~?D???-[?U+V??=w۪??ǐ???u8?u?y%??G	??h?$??^???C,????&?6g?<s8??H????q?{K???e?xRߋ??q!???o&?M??l?H&?#?????7co#F???u?}??&F?	?.F?}??iԶ??Ch6N?9???ea]??p?0?-8?\x9?4??qdD?6U?̻'Şl???_g/?*???7??┕4??"=|?\4d?Cn?Ѐ?P???:)????%?A??K?`絟??q?K??k????ԏ?'?N̲?f²??4?`????e,?]5O?t#h??^Ww`??K?GM\??%{??????k???????#?-f@??{z1`???%??Rn砪?h$r??>??N???_T$
????HS̋???h???`??{??nW?h??/?*???y?7`?(????g?oR?? aAᅟ???&
?P,?????:??jX?HtuV?dL??|ܱ?{?fEw=?7?+_?n??6?qȜ?'f?{j?h?:a4 9(`?[?y??????? E??????E??      h   ?   x?m?A? ?5s???@diZ??*	உ??????g???"??n?{v???BL?ܲ?/??ц#??	K???1?1???^?M?~"0B??"H???G???n??J??5+>????*??~"?;?k?      ~      x?????? ? ?            x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?   W  x????n?@???ܛ4??m?h?F?'cpc7Q0??S??/V?X?r?&dB?0???,?}??8?RU?:ϲ?.??Sv???^
]n??)$??*2u??*???C??ʞ懝??@??1?\?]F\XǷ?V?Y?C	??K???U\u???xܞ?^5a????˼?!??71N"?
D??J???^]?/?j?1?????(c\???^?? ??@xc?6E?4??1???#8[h???w@?Tx??a??׆pHoR??
2??7?w?q(?2??5IH/Iu?6?_?3U?Q<?8y??s?n???{?"?F?9?0j?/?vb30^????????+??ĺ??ٲ?NRO      u     x?m??j?0??'O?]WSr??2?V?1????B???>T??/V???? z>9?poB?F?~|~y???~?l?h^Y~?ٺ?-ǟ_?(%?͙_?>?h?L?*m???CĞo?????:?tQ>?%a?n?^??Ig?N??G??v?٧????ݵ;p?"k??
;??)D?_o???/????Vv0??2??5??Er?"n?ߥ
?ШA+????b?Fպ?{ϖL????Ӫ??pl????]???q?????????X?(=????6<?2ƾ??Y      }      x?????? ? ?      ?   ?  x?m??n? ??O??????Z˺9?Z??ԛ?%Ȏ??X????6?NS?B?I|?????NF???
?n???pp<U??m?c?????h[???4?)?T?Q!???R2?r??{'KR?h?=???????;>?^?^"?1w?Ef??????(?j??Ox?7F=?r?_s?T?(?S??Nh?P?87X?C??????J?rj?K??9%3?T??r???Y][???Y?uf??^?9?FŌD?n^?2?ﾻ???K???4?????\??ZyY???\ ?????????O?yh?9?͂?\y??wj?ʷޏ????????ǈ%???e??~??\???1_o?`S@i?'???,??,?n?	?o?`i?[????^1)Tv	)?kN^>B?n??t      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?   ?   x?u?O?0???޺E??????C???S ??1???gB?????>???>-?T?????8Tc??~h?{?W?n?Ki?????p]?$bu~?tڻB?a6?,????Wz??+?Q?Q??y"????q?Ƨ,v9?j??N8G?*?cX?ʖ????P[(???RF????Mⱜ7l??T?3d????Dl??R???dT?[2?#?-!?{??/      ?   :   x?3??45??p?t?p?2
??X?X?	?????? ??qqq ?C%?      ?      x?????? ? ?      ?      x?????? ? ?      z   ?   x?]?K?0@??S?????H??Pt妑?i?t??7?sp1[ܹ?yo?F?D?j??=????Zm??????i?L?d	y?4G8 q}G?)b?????>+?NU&?*OU????ٿ??8??O?)jk??EV?<׳??????T[??<??i?
?B?g5?34?q??]??"????Fb?c?"S)      ?      x?????? ? ?      ?      x?????? ? ?      ?   L   x?3445??LMO??IU(1RRr?S?J?R2?S99?M?8?S???J??d?E ?F?1z\\\ ??      ?      x?????? ? ?      ?      x?????? ? ?      ?   f   x?5ιA?X*?X?W/????u	NB?ۛPH}	?ߌC???=Rd????	???1?T)ҵ(?Fޥ%?:?䂨?HÎ???)???|????#j      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?      ?      x?????? ? ?     