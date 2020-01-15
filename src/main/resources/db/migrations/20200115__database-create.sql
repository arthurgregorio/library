--
-- PostgreSQL database dump
--

-- Dumped from database version 10.7 (Debian 10.7-1.pgdg90+1)
-- Dumped by pg_dump version 11.3

-- Started on 2020-01-15 13:43:54 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 94814)
-- Name: configuration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration;


--
-- TOC entry 5 (class 2615 OID 94815)
-- Name: registration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA registration;


SET default_with_oids = false;

--
-- TOC entry 201 (class 1259 OID 94826)
-- Name: authorizations; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.authorizations (
                                              id bigint NOT NULL,
                                              created_on timestamp without time zone NOT NULL,
                                              updated_on timestamp without time zone,
                                              functionality character varying(90) NOT NULL,
                                              permission character varying(90) NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 94831)
-- Name: authorizations_audit; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.authorizations_audit (
                                                    id bigint NOT NULL,
                                                    revision bigint NOT NULL,
                                                    revision_type smallint,
                                                    functionality character varying(90),
                                                    permission character varying(90)
);


--
-- TOC entry 203 (class 1259 OID 94836)
-- Name: grants; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.grants (
                                      id bigint NOT NULL,
                                      created_on timestamp without time zone NOT NULL,
                                      updated_on timestamp without time zone,
                                      id_authorization bigint NOT NULL,
                                      id_group bigint NOT NULL
);


--
-- TOC entry 204 (class 1259 OID 94841)
-- Name: grants_audit; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.grants_audit (
                                            id bigint NOT NULL,
                                            revision bigint NOT NULL,
                                            revision_type smallint,
                                            id_authorization bigint,
                                            id_group bigint
);


--
-- TOC entry 205 (class 1259 OID 94846)
-- Name: groups; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.groups (
                                      id bigint NOT NULL,
                                      created_on timestamp without time zone NOT NULL,
                                      updated_on timestamp without time zone,
                                      active boolean NOT NULL,
                                      name character varying(45) NOT NULL,
                                      id_parent bigint
);


--
-- TOC entry 206 (class 1259 OID 94851)
-- Name: groups_audit; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.groups_audit (
                                            id bigint NOT NULL,
                                            revision bigint NOT NULL,
                                            revision_type smallint,
                                            active boolean,
                                            name character varying(45),
                                            id_parent bigint
);


--
-- TOC entry 207 (class 1259 OID 94856)
-- Name: profiles; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.profiles (
                                        id bigint NOT NULL,
                                        created_on timestamp without time zone NOT NULL,
                                        updated_on timestamp without time zone,
                                        active_theme character varying(45) NOT NULL,
                                        dark_sidebar boolean NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 94861)
-- Name: profiles_audit; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.profiles_audit (
                                              id bigint NOT NULL,
                                              revision bigint NOT NULL,
                                              revision_type smallint,
                                              active_theme character varying(45),
                                              dark_sidebar boolean
);


--
-- TOC entry 209 (class 1259 OID 94866)
-- Name: users; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.users (
                                     id bigint NOT NULL,
                                     created_on timestamp without time zone NOT NULL,
                                     updated_on timestamp without time zone,
                                     active boolean NOT NULL,
                                     email character varying(90) NOT NULL,
                                     name character varying(90) NOT NULL,
                                     password character varying(60),
                                     store_type character varying(255) NOT NULL,
                                     username character varying(20) NOT NULL,
                                     id_group bigint NOT NULL,
                                     id_profile bigint NOT NULL
);


--
-- TOC entry 210 (class 1259 OID 94874)
-- Name: users_audit; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.users_audit (
                                           id bigint NOT NULL,
                                           revision bigint NOT NULL,
                                           revision_type smallint,
                                           active boolean,
                                           email character varying(90),
                                           name character varying(90),
                                           password character varying(60),
                                           store_type character varying(255),
                                           username character varying(20),
                                           id_group bigint,
                                           id_profile bigint
);


--
-- TOC entry 200 (class 1259 OID 94824)
-- Name: pooled_sequence_generator; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pooled_sequence_generator
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 199 (class 1259 OID 94818)
-- Name: revisions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.revisions (
                                  id bigint NOT NULL,
                                  created_by character varying(45) NOT NULL,
                                  created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 94816)
-- Name: revisions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.revisions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2978 (class 0 OID 0)
-- Dependencies: 198
-- Name: revisions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.revisions_id_seq OWNED BY public.revisions.id;


--
-- TOC entry 211 (class 1259 OID 94884)
-- Name: authors; Type: TABLE; Schema: registration; Owner: -
--

CREATE TABLE registration.authors (
                                      id bigint NOT NULL,
                                      created_on timestamp without time zone NOT NULL,
                                      updated_on timestamp without time zone,
                                      active boolean NOT NULL,
                                      born_date date,
                                      email character varying(90) NOT NULL,
                                      name character varying(90) NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 94889)
-- Name: authors_audit; Type: TABLE; Schema: registration; Owner: -
--

CREATE TABLE registration.authors_audit (
                                            id bigint NOT NULL,
                                            revision bigint NOT NULL,
                                            revision_type smallint,
                                            active boolean,
                                            born_date date,
                                            email character varying(90),
                                            name character varying(90)
);


--
-- TOC entry 213 (class 1259 OID 94894)
-- Name: books; Type: TABLE; Schema: registration; Owner: -
--

CREATE TABLE registration.books (
                                    id bigint NOT NULL,
                                    created_on timestamp without time zone NOT NULL,
                                    updated_on timestamp without time zone,
                                    active boolean NOT NULL,
                                    isbn character varying(20) NOT NULL,
                                    published_on date,
                                    subtitle character varying(90),
                                    summary character varying(500) NOT NULL,
                                    title character varying(90) NOT NULL,
                                    id_author bigint NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 94902)
-- Name: books_audit; Type: TABLE; Schema: registration; Owner: -
--

CREATE TABLE registration.books_audit (
                                          id bigint NOT NULL,
                                          revision bigint NOT NULL,
                                          revision_type smallint,
                                          active boolean,
                                          isbn character varying(20),
                                          published_on date,
                                          subtitle character varying(90),
                                          summary character varying(500),
                                          title character varying(90),
                                          id_author bigint
);


--
-- TOC entry 2789 (class 2604 OID 94821)
-- Name: revisions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions ALTER COLUMN id SET DEFAULT nextval('public.revisions_id_seq'::regclass);


--
-- TOC entry 2959 (class 0 OID 94826)
-- Dependencies: 201
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (1, '2020-01-15 10:31:50.455188', NULL, 'author', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (2, '2020-01-15 10:31:50.500249', NULL, 'author', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (3, '2020-01-15 10:31:50.505233', NULL, 'author', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (4, '2020-01-15 10:31:50.510058', NULL, 'author', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (5, '2020-01-15 10:31:50.514973', NULL, 'author', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (6, '2020-01-15 10:31:50.520006', NULL, 'book', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (7, '2020-01-15 10:31:50.525522', NULL, 'book', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (8, '2020-01-15 10:31:50.529965', NULL, 'book', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (9, '2020-01-15 10:31:50.540733', NULL, 'book', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (10, '2020-01-15 10:31:50.548937', NULL, 'book', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (11, '2020-01-15 10:31:50.55679', NULL, 'user', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (12, '2020-01-15 10:31:50.569024', NULL, 'user', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (13, '2020-01-15 10:31:50.575349', NULL, 'user', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (14, '2020-01-15 10:31:50.580265', NULL, 'user', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (15, '2020-01-15 10:31:50.584915', NULL, 'user', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (16, '2020-01-15 10:31:50.591807', NULL, 'group', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (17, '2020-01-15 10:31:50.599619', NULL, 'group', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (18, '2020-01-15 10:31:50.603829', NULL, 'group', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (19, '2020-01-15 10:31:50.608126', NULL, 'group', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (20, '2020-01-15 10:31:50.613064', NULL, 'group', 'access');


--
-- TOC entry 2960 (class 0 OID 94831)
-- Dependencies: 202
-- Data for Name: authorizations_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (1, 1, 0, 'author', 'add');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (2, 1, 0, 'author', 'update');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (3, 1, 0, 'author', 'delete');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (4, 1, 0, 'author', 'detail');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (5, 1, 0, 'author', 'access');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (6, 1, 0, 'book', 'add');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (7, 1, 0, 'book', 'update');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (8, 1, 0, 'book', 'delete');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (9, 1, 0, 'book', 'detail');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (10, 1, 0, 'book', 'access');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (11, 1, 0, 'user', 'add');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (12, 1, 0, 'user', 'update');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (13, 1, 0, 'user', 'delete');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (14, 1, 0, 'user', 'detail');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (15, 1, 0, 'user', 'access');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (16, 1, 0, 'group', 'add');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (17, 1, 0, 'group', 'update');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (18, 1, 0, 'group', 'delete');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (19, 1, 0, 'group', 'detail');
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (20, 1, 0, 'group', 'access');


--
-- TOC entry 2961 (class 0 OID 94836)
-- Dependencies: 203
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (26, '2020-01-15 10:31:50.727558', NULL, 1, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (27, '2020-01-15 10:31:50.73003', NULL, 2, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (28, '2020-01-15 10:31:50.730674', NULL, 3, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (29, '2020-01-15 10:31:50.731232', NULL, 4, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (30, '2020-01-15 10:31:50.731781', NULL, 5, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (31, '2020-01-15 10:31:50.732385', NULL, 6, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (32, '2020-01-15 10:31:50.735501', NULL, 7, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (33, '2020-01-15 10:31:50.736172', NULL, 8, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (34, '2020-01-15 10:31:50.736772', NULL, 9, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (35, '2020-01-15 10:31:50.737383', NULL, 10, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (36, '2020-01-15 10:31:50.738036', NULL, 11, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (37, '2020-01-15 10:31:50.741126', NULL, 12, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (38, '2020-01-15 10:31:50.74176', NULL, 13, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (39, '2020-01-15 10:31:50.742298', NULL, 14, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (40, '2020-01-15 10:31:50.742893', NULL, 15, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (41, '2020-01-15 10:31:50.743564', NULL, 16, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (42, '2020-01-15 10:31:50.74772', NULL, 17, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (43, '2020-01-15 10:31:50.748329', NULL, 18, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (44, '2020-01-15 10:31:50.74892', NULL, 19, 21);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (45, '2020-01-15 10:31:50.749426', NULL, 20, 21);


--
-- TOC entry 2962 (class 0 OID 94841)
-- Dependencies: 204
-- Data for Name: grants_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (26, 2, 0, 1, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (27, 2, 0, 2, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (28, 2, 0, 3, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (29, 2, 0, 4, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (30, 2, 0, 5, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (31, 2, 0, 6, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (32, 2, 0, 7, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (33, 2, 0, 8, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (34, 2, 0, 9, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (35, 2, 0, 10, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (36, 2, 0, 11, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (37, 2, 0, 12, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (38, 2, 0, 13, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (39, 2, 0, 14, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (40, 2, 0, 15, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (41, 2, 0, 16, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (42, 2, 0, 17, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (43, 2, 0, 18, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (44, 2, 0, 19, 21);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (45, 2, 0, 20, 21);


--
-- TOC entry 2963 (class 0 OID 94846)
-- Dependencies: 205
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups (id, created_on, updated_on, active, name, id_parent) VALUES (21, '2020-01-15 10:31:50.703812', NULL, true, 'Administradores', NULL);


--
-- TOC entry 2964 (class 0 OID 94851)
-- Dependencies: 206
-- Data for Name: groups_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups_audit (id, revision, revision_type, active, name, id_parent) VALUES (21, 2, 0, true, 'Administradores', NULL);


--
-- TOC entry 2965 (class 0 OID 94856)
-- Dependencies: 207
-- Data for Name: profiles; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles (id, created_on, updated_on, active_theme, dark_sidebar) VALUES (51, '2020-01-15 10:31:51.054872', NULL, 'BLACK', true);


--
-- TOC entry 2966 (class 0 OID 94861)
-- Dependencies: 208
-- Data for Name: profiles_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles_audit (id, revision, revision_type, active_theme, dark_sidebar) VALUES (51, 3, 0, 'BLACK', true);


--
-- TOC entry 2967 (class 0 OID 94866)
-- Dependencies: 209
-- Data for Name: users; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users (id, created_on, updated_on, active, email, name, password, store_type, username, id_group, id_profile) VALUES (46, '2020-01-15 10:31:51.053133', NULL, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$ekrCZUh/xTO400/hSoTUC.0bBosfYuH0IxdET/LsFd2mEAEcQKaga', 'LOCAL', 'admin', 21, 51);


--
-- TOC entry 2968 (class 0 OID 94874)
-- Dependencies: 210
-- Data for Name: users_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users_audit (id, revision, revision_type, active, email, name, password, store_type, username, id_group, id_profile) VALUES (46, 3, 0, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$ekrCZUh/xTO400/hSoTUC.0bBosfYuH0IxdET/LsFd2mEAEcQKaga', 'LOCAL', 'admin', 21, 51);


--
-- TOC entry 2957 (class 0 OID 94818)
-- Dependencies: 199
-- Data for Name: revisions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.revisions (id, created_by, created_on) VALUES (1, 'unknown', '2020-01-15 10:31:50.617');
INSERT INTO public.revisions (id, created_by, created_on) VALUES (2, 'unknown', '2020-01-15 10:31:50.799');
INSERT INTO public.revisions (id, created_by, created_on) VALUES (3, 'unknown', '2020-01-15 10:31:51.061');


--
-- TOC entry 2969 (class 0 OID 94884)
-- Dependencies: 211
-- Data for Name: authors; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2970 (class 0 OID 94889)
-- Dependencies: 212
-- Data for Name: authors_audit; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2971 (class 0 OID 94894)
-- Dependencies: 213
-- Data for Name: books; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2972 (class 0 OID 94902)
-- Dependencies: 214
-- Data for Name: books_audit; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2979 (class 0 OID 0)
-- Dependencies: 200
-- Name: pooled_sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.pooled_sequence_generator', 51, true);


--
-- TOC entry 2980 (class 0 OID 0)
-- Dependencies: 198
-- Name: revisions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.revisions_id_seq', 3, true);


--
-- TOC entry 2795 (class 2606 OID 94835)
-- Name: authorizations_audit authorizations_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations_audit
    ADD CONSTRAINT authorizations_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2793 (class 2606 OID 94830)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id);


--
-- TOC entry 2799 (class 2606 OID 94845)
-- Name: grants_audit grants_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants_audit
    ADD CONSTRAINT grants_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2797 (class 2606 OID 94840)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id);


--
-- TOC entry 2803 (class 2606 OID 94855)
-- Name: groups_audit groups_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups_audit
    ADD CONSTRAINT groups_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2801 (class 2606 OID 94850)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2807 (class 2606 OID 94865)
-- Name: profiles_audit profiles_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles_audit
    ADD CONSTRAINT profiles_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2805 (class 2606 OID 94860)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2809 (class 2606 OID 94883)
-- Name: users uk_rcle35tk5t6py9hf7uow9qkcw; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT uk_rcle35tk5t6py9hf7uow9qkcw UNIQUE (id_profile);


--
-- TOC entry 2813 (class 2606 OID 94881)
-- Name: users_audit users_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users_audit
    ADD CONSTRAINT users_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2811 (class 2606 OID 94873)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2791 (class 2606 OID 94823)
-- Name: revisions revisions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions
    ADD CONSTRAINT revisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2817 (class 2606 OID 94893)
-- Name: authors_audit authors_audit_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors_audit
    ADD CONSTRAINT authors_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2815 (class 2606 OID 94888)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- TOC entry 2821 (class 2606 OID 94909)
-- Name: books_audit books_audit_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books_audit
    ADD CONSTRAINT books_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2819 (class 2606 OID 94901)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- TOC entry 2822 (class 2606 OID 94910)
-- Name: authorizations_audit fk_authorizations_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations_audit
    ADD CONSTRAINT fk_authorizations_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2825 (class 2606 OID 94925)
-- Name: grants_audit fk_grants_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants_audit
    ADD CONSTRAINT fk_grants_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2823 (class 2606 OID 94915)
-- Name: grants fk_grants_authorizations; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fk_grants_authorizations FOREIGN KEY (id_authorization) REFERENCES configuration.authorizations(id);


--
-- TOC entry 2824 (class 2606 OID 94920)
-- Name: grants fk_grants_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fk_grants_groups FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2827 (class 2606 OID 94935)
-- Name: groups_audit fk_groups_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups_audit
    ADD CONSTRAINT fk_groups_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2826 (class 2606 OID 94930)
-- Name: groups fk_groups_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT fk_groups_groups FOREIGN KEY (id_parent) REFERENCES configuration.groups(id);


--
-- TOC entry 2828 (class 2606 OID 94940)
-- Name: profiles_audit fk_profiles_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles_audit
    ADD CONSTRAINT fk_profiles_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2831 (class 2606 OID 94955)
-- Name: users_audit fk_users_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users_audit
    ADD CONSTRAINT fk_users_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2829 (class 2606 OID 94945)
-- Name: users fk_users_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk_users_groups FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2830 (class 2606 OID 94950)
-- Name: users fk_users_profiles; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk_users_profiles FOREIGN KEY (id_profile) REFERENCES configuration.profiles(id);


--
-- TOC entry 2832 (class 2606 OID 94960)
-- Name: authors_audit fk_authors_audit_revisions; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors_audit
    ADD CONSTRAINT fk_authors_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2834 (class 2606 OID 94970)
-- Name: books_audit fk_books_audit_revisions; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books_audit
    ADD CONSTRAINT fk_books_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2833 (class 2606 OID 94965)
-- Name: books fk_books_authors; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT fk_books_authors FOREIGN KEY (id_author) REFERENCES registration.authors(id);


-- Completed on 2020-01-15 13:43:55 UTC

--
-- PostgreSQL database dump complete
--