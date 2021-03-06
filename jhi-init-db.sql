PGDMP                         x            jhi-init-db    9.6.4    9.6.4 (    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            ?           1262    37359    jhi-init-db    DATABASE     ?   CREATE DATABASE "jhi-init-db" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE "jhi-init-db";
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            ?           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12387    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            ?           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            ?            1259    37365    databasechangelog    TABLE     R  CREATE TABLE databasechangelog (
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
       public         postgres    false    3            ?            1259    37360    databasechangeloglock    TABLE     ?   CREATE TABLE databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);
 )   DROP TABLE public.databasechangeloglock;
       public         postgres    false    3            ?            1259    37385    jhi_authority    TABLE     H   CREATE TABLE jhi_authority (
    name character varying(50) NOT NULL
);
 !   DROP TABLE public.jhi_authority;
       public         postgres    false    3            ?            1259    37415    jhi_persistent_audit_event    TABLE     ?   CREATE TABLE jhi_persistent_audit_event (
    event_id bigint NOT NULL,
    principal character varying(50) NOT NULL,
    event_date timestamp without time zone,
    event_type character varying(255)
);
 .   DROP TABLE public.jhi_persistent_audit_event;
       public         postgres    false    3            ?            1259    37420    jhi_persistent_audit_evt_data    TABLE     ?   CREATE TABLE jhi_persistent_audit_evt_data (
    event_id bigint NOT NULL,
    name character varying(150) NOT NULL,
    value character varying(255)
);
 1   DROP TABLE public.jhi_persistent_audit_evt_data;
       public         postgres    false    3            ?            1259    37395    jhi_persistent_token    TABLE     ?   CREATE TABLE jhi_persistent_token (
    series character varying(20) NOT NULL,
    user_id bigint,
    token_value character varying(20) NOT NULL,
    token_date date,
    ip_address character varying(39),
    user_agent character varying(255)
);
 (   DROP TABLE public.jhi_persistent_token;
       public         postgres    false    3            ?            1259    37373    jhi_user    TABLE     ?  CREATE TABLE jhi_user (
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
       public         postgres    false    3            ?            1259    37390    jhi_user_authority    TABLE     t   CREATE TABLE jhi_user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);
 &   DROP TABLE public.jhi_user_authority;
       public         postgres    false    3            ?            1259    37371    sequence_generator    SEQUENCE     x   CREATE SEQUENCE sequence_generator
    START WITH 1050
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.sequence_generator;
       public       postgres    false    3            }          0    37365    databasechangelog 
   TABLE DATA               ?   COPY databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
    public       postgres    false    186   3       |          0    37360    databasechangeloglock 
   TABLE DATA               K   COPY databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
    public       postgres    false    185   k4       ?          0    37385    jhi_authority 
   TABLE DATA               &   COPY jhi_authority (name) FROM stdin;
    public       postgres    false    189   ?4       ?          0    37415    jhi_persistent_audit_event 
   TABLE DATA               Z   COPY jhi_persistent_audit_event (event_id, principal, event_date, event_type) FROM stdin;
    public       postgres    false    192   ?4       ?          0    37420    jhi_persistent_audit_evt_data 
   TABLE DATA               G   COPY jhi_persistent_audit_evt_data (event_id, name, value) FROM stdin;
    public       postgres    false    193   5       ?          0    37395    jhi_persistent_token 
   TABLE DATA               i   COPY jhi_persistent_token (series, user_id, token_value, token_date, ip_address, user_agent) FROM stdin;
    public       postgres    false    191   N5                 0    37373    jhi_user 
   TABLE DATA               ?   COPY jhi_user (id, login, password_hash, first_name, last_name, email, image_url, activated, lang_key, activation_key, reset_key, created_by, created_date, reset_date, last_modified_by, last_modified_date) FROM stdin;
    public       postgres    false    188   k5       ?          0    37390    jhi_user_authority 
   TABLE DATA               >   COPY jhi_user_authority (user_id, authority_name) FROM stdin;
    public       postgres    false    190   ?6       ?           0    0    sequence_generator    SEQUENCE SET     <   SELECT pg_catalog.setval('sequence_generator', 1050, true);
            public       postgres    false    187            ?           2606    37364 0   databasechangeloglock databasechangeloglock_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.databasechangeloglock DROP CONSTRAINT databasechangeloglock_pkey;
       public         postgres    false    185    185            ?           2606    37389     jhi_authority jhi_authority_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY jhi_authority
    ADD CONSTRAINT jhi_authority_pkey PRIMARY KEY (name);
 J   ALTER TABLE ONLY public.jhi_authority DROP CONSTRAINT jhi_authority_pkey;
       public         postgres    false    189    189            ?           2606    37419 :   jhi_persistent_audit_event jhi_persistent_audit_event_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY jhi_persistent_audit_event
    ADD CONSTRAINT jhi_persistent_audit_event_pkey PRIMARY KEY (event_id);
 d   ALTER TABLE ONLY public.jhi_persistent_audit_event DROP CONSTRAINT jhi_persistent_audit_event_pkey;
       public         postgres    false    192    192                       2606    37424 @   jhi_persistent_audit_evt_data jhi_persistent_audit_evt_data_pkey 
   CONSTRAINT     ?   ALTER TABLE ONLY jhi_persistent_audit_evt_data
    ADD CONSTRAINT jhi_persistent_audit_evt_data_pkey PRIMARY KEY (event_id, name);
 j   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data DROP CONSTRAINT jhi_persistent_audit_evt_data_pkey;
       public         postgres    false    193    193    193            ?           2606    37399 .   jhi_persistent_token jhi_persistent_token_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY jhi_persistent_token
    ADD CONSTRAINT jhi_persistent_token_pkey PRIMARY KEY (series);
 X   ALTER TABLE ONLY public.jhi_persistent_token DROP CONSTRAINT jhi_persistent_token_pkey;
       public         postgres    false    191    191            ?           2606    37394 *   jhi_user_authority jhi_user_authority_pkey 
   CONSTRAINT     v   ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);
 T   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT jhi_user_authority_pkey;
       public         postgres    false    190    190    190            ?           2606    37380    jhi_user jhi_user_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT jhi_user_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT jhi_user_pkey;
       public         postgres    false    188    188            ?           2606    37382    jhi_user ux_user_email 
   CONSTRAINT     K   ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT ux_user_email UNIQUE (email);
 @   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT ux_user_email;
       public         postgres    false    188    188            ?           2606    37384    jhi_user ux_user_login 
   CONSTRAINT     K   ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT ux_user_login UNIQUE (login);
 @   ALTER TABLE ONLY public.jhi_user DROP CONSTRAINT ux_user_login;
       public         postgres    false    188    188            ?           1259    37425    idx_persistent_audit_event    INDEX     k   CREATE INDEX idx_persistent_audit_event ON jhi_persistent_audit_event USING btree (principal, event_date);
 .   DROP INDEX public.idx_persistent_audit_event;
       public         postgres    false    192    192                        1259    37426    idx_persistent_audit_evt_data    INDEX     d   CREATE INDEX idx_persistent_audit_evt_data ON jhi_persistent_audit_evt_data USING btree (event_id);
 1   DROP INDEX public.idx_persistent_audit_evt_data;
       public         postgres    false    193                       2606    37400 $   jhi_user_authority fk_authority_name    FK CONSTRAINT     ?   ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES jhi_authority(name);
 N   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT fk_authority_name;
       public       postgres    false    2040    189    190                       2606    37427 8   jhi_persistent_audit_evt_data fk_evt_pers_audit_evt_data    FK CONSTRAINT     ?   ALTER TABLE ONLY jhi_persistent_audit_evt_data
    ADD CONSTRAINT fk_evt_pers_audit_evt_data FOREIGN KEY (event_id) REFERENCES jhi_persistent_audit_event(event_id);
 b   ALTER TABLE ONLY public.jhi_persistent_audit_evt_data DROP CONSTRAINT fk_evt_pers_audit_evt_data;
       public       postgres    false    192    2047    193                       2606    37405    jhi_user_authority fk_user_id    FK CONSTRAINT     q   ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);
 G   ALTER TABLE ONLY public.jhi_user_authority DROP CONSTRAINT fk_user_id;
       public       postgres    false    190    2034    188                       2606    37410 -   jhi_persistent_token fk_user_persistent_token    FK CONSTRAINT     ?   ALTER TABLE ONLY jhi_persistent_token
    ADD CONSTRAINT fk_user_persistent_token FOREIGN KEY (user_id) REFERENCES jhi_user(id);
 W   ALTER TABLE ONLY public.jhi_persistent_token DROP CONSTRAINT fk_user_persistent_token;
       public       postgres    false    191    2034    188            }   ?  x??Q?j?0<?_??"ɶ,?????B(4?F???֖Y???k?h?S?????̊?˅?k??x?;??ռ???*??\??U?t???????`eS???V?϶A?02#bFӘ?????Q?E????u?z@b??f?)?FQ?2"?f?M?L)WH{?^?0????`-[??N?
x:??v??q2??S?Vdi]I??e3???<??K??,a????q?50?F]???Ij?>7R5??<9E?C?.??]????[???)?y?????????????v?e?>??x;?r#??s}?ҺO????c|?϶8??/q?z      |      x?3?L???"?=... U?      ?      x???q?wt????
1C?]??b???? b??      ?   G   x?3400?LL????4202?5??54Q04?24?2?г44?t?p??tv????uvv?????? ??      ?   (   x?3400?,J??/IuLI)J-.?442?3 BC?=... ???      ?      x?????? ? ?         :  x???Ɏ?@E??w?f? ?F?QQ0"`7??̔`C}?Nǝ??{??sO??i?Lx4??I??u?l???f`$?p8??ϴ?V?/<?c??n*s??#blt`??s'~8@E???D8??<?^?T?j(q?tMD?:?l??d??݃?k??Y?w?? ?6???5Е(??6G?p???????G~OI (,????jr"??(?gL??D??e???Z?l???m??JD????'s????i	j1yI7?{NS??;?????j?????eyTl:???L??2t?4b???j?T|{??d??Onkd???)????O      ?   *   x?3???q?wt????2?pB?]????%??$L??1z\\\ =?h     