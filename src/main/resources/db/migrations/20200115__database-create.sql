--
-- PostgreSQL database dump
--

-- Dumped from database version 10.7 (Debian 10.7-1.pgdg90+1)
-- Dumped by pg_dump version 11.3

-- Started on 2020-01-21 20:47:41 UTC

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
-- TOC entry 4 (class 2615 OID 103236)
-- Name: configuration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration;


--
-- TOC entry 9 (class 2615 OID 103237)
-- Name: registration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA registration;


SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 103238)
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
-- TOC entry 199 (class 1259 OID 103243)
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
-- TOC entry 200 (class 1259 OID 103248)
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
-- TOC entry 201 (class 1259 OID 103253)
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
-- TOC entry 202 (class 1259 OID 103258)
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
-- TOC entry 203 (class 1259 OID 103263)
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
-- TOC entry 204 (class 1259 OID 103268)
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
-- TOC entry 205 (class 1259 OID 103273)
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
-- TOC entry 206 (class 1259 OID 103278)
-- Name: users; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.users (
                                     id bigint NOT NULL,
                                     created_on timestamp without time zone NOT NULL,
                                     updated_on timestamp without time zone,
                                     active boolean NOT NULL,
                                     department character varying(90),
                                     email character varying(90) NOT NULL,
                                     name character varying(90) NOT NULL,
                                     password character varying(60),
                                     store_type character varying(255) NOT NULL,
                                     telephone character varying(90),
                                     username character varying(20) NOT NULL,
                                     id_group bigint NOT NULL,
                                     id_profile bigint NOT NULL
);


--
-- TOC entry 207 (class 1259 OID 103286)
-- Name: users_audit; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.users_audit (
                                           id bigint NOT NULL,
                                           revision bigint NOT NULL,
                                           revision_type smallint,
                                           active boolean,
                                           department character varying(90),
                                           email character varying(90),
                                           name character varying(90),
                                           password character varying(60),
                                           store_type character varying(255),
                                           telephone character varying(90),
                                           username character varying(20),
                                           id_group bigint,
                                           id_profile bigint
);


--
-- TOC entry 214 (class 1259 OID 103330)
-- Name: pooled_sequence_generator; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pooled_sequence_generator
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 213 (class 1259 OID 103324)
-- Name: revisions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.revisions (
                                  id bigint NOT NULL,
                                  created_by character varying(45) NOT NULL,
                                  created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 103322)
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
-- Dependencies: 212
-- Name: revisions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.revisions_id_seq OWNED BY public.revisions.id;


--
-- TOC entry 208 (class 1259 OID 103296)
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
-- TOC entry 209 (class 1259 OID 103301)
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
-- TOC entry 210 (class 1259 OID 103306)
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
-- TOC entry 211 (class 1259 OID 103314)
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
-- TOC entry 2789 (class 2604 OID 103327)
-- Name: revisions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions ALTER COLUMN id SET DEFAULT nextval('public.revisions_id_seq'::regclass);


--
-- TOC entry 2956 (class 0 OID 103238)
-- Dependencies: 198
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (1, '2020-01-21 17:47:06.290137', NULL, 'author', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (2, '2020-01-21 17:47:06.320577', NULL, 'author', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (3, '2020-01-21 17:47:06.326245', NULL, 'author', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (4, '2020-01-21 17:47:06.33517', NULL, 'author', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (5, '2020-01-21 17:47:06.341582', NULL, 'author', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (6, '2020-01-21 17:47:06.346728', NULL, 'book', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (7, '2020-01-21 17:47:06.350992', NULL, 'book', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (8, '2020-01-21 17:47:06.353885', NULL, 'book', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (9, '2020-01-21 17:47:06.357381', NULL, 'book', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (10, '2020-01-21 17:47:06.36095', NULL, 'book', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (11, '2020-01-21 17:47:06.364492', NULL, 'user', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (12, '2020-01-21 17:47:06.368923', NULL, 'user', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (13, '2020-01-21 17:47:06.37441', NULL, 'user', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (14, '2020-01-21 17:47:06.381461', NULL, 'user', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (15, '2020-01-21 17:47:06.390153', NULL, 'user', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (16, '2020-01-21 17:47:06.400172', NULL, 'group', 'add');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (17, '2020-01-21 17:47:06.410657', NULL, 'group', 'update');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (18, '2020-01-21 17:47:06.416575', NULL, 'group', 'delete');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (19, '2020-01-21 17:47:06.420194', NULL, 'group', 'detail');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (20, '2020-01-21 17:47:06.423355', NULL, 'group', 'access');
INSERT INTO configuration.authorizations (id, created_on, updated_on, functionality, permission) VALUES (21, '2020-01-21 17:47:06.426301', NULL, 'import-user', 'access');


--
-- TOC entry 2957 (class 0 OID 103243)
-- Dependencies: 199
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
INSERT INTO configuration.authorizations_audit (id, revision, revision_type, functionality, permission) VALUES (21, 1, 0, 'import-user', 'access');


--
-- TOC entry 2958 (class 0 OID 103248)
-- Dependencies: 200
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (31, '2020-01-21 17:47:06.506248', NULL, 1, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (32, '2020-01-21 17:47:06.507491', NULL, 2, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (33, '2020-01-21 17:47:06.507781', NULL, 3, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (34, '2020-01-21 17:47:06.508072', NULL, 4, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (35, '2020-01-21 17:47:06.508371', NULL, 5, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (36, '2020-01-21 17:47:06.508644', NULL, 6, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (37, '2020-01-21 17:47:06.509676', NULL, 7, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (38, '2020-01-21 17:47:06.509954', NULL, 8, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (39, '2020-01-21 17:47:06.510241', NULL, 9, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (40, '2020-01-21 17:47:06.510576', NULL, 10, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (41, '2020-01-21 17:47:06.510871', NULL, 11, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (42, '2020-01-21 17:47:06.511873', NULL, 12, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (43, '2020-01-21 17:47:06.512206', NULL, 13, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (44, '2020-01-21 17:47:06.512504', NULL, 14, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (45, '2020-01-21 17:47:06.512768', NULL, 15, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (46, '2020-01-21 17:47:06.513034', NULL, 16, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (47, '2020-01-21 17:47:06.513997', NULL, 17, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (48, '2020-01-21 17:47:06.514283', NULL, 18, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (49, '2020-01-21 17:47:06.514551', NULL, 19, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (50, '2020-01-21 17:47:06.514828', NULL, 20, 26);
INSERT INTO configuration.grants (id, created_on, updated_on, id_authorization, id_group) VALUES (51, '2020-01-21 17:47:06.515139', NULL, 21, 26);


--
-- TOC entry 2959 (class 0 OID 103253)
-- Dependencies: 201
-- Data for Name: grants_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (31, 2, 0, 1, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (32, 2, 0, 2, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (33, 2, 0, 3, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (34, 2, 0, 4, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (35, 2, 0, 5, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (36, 2, 0, 6, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (37, 2, 0, 7, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (38, 2, 0, 8, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (39, 2, 0, 9, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (40, 2, 0, 10, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (41, 2, 0, 11, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (42, 2, 0, 12, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (43, 2, 0, 13, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (44, 2, 0, 14, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (45, 2, 0, 15, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (46, 2, 0, 16, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (47, 2, 0, 17, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (48, 2, 0, 18, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (49, 2, 0, 19, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (50, 2, 0, 20, 26);
INSERT INTO configuration.grants_audit (id, revision, revision_type, id_authorization, id_group) VALUES (51, 2, 0, 21, 26);


--
-- TOC entry 2960 (class 0 OID 103258)
-- Dependencies: 202
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups (id, created_on, updated_on, active, name, id_parent) VALUES (26, '2020-01-21 17:47:06.490228', NULL, true, 'Administradores', NULL);


--
-- TOC entry 2961 (class 0 OID 103263)
-- Dependencies: 203
-- Data for Name: groups_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups_audit (id, revision, revision_type, active, name, id_parent) VALUES (26, 2, 0, true, 'Administradores', NULL);


--
-- TOC entry 2962 (class 0 OID 103268)
-- Dependencies: 204
-- Data for Name: profiles; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles (id, created_on, updated_on, active_theme, dark_sidebar) VALUES (61, '2020-01-21 17:47:06.741211', NULL, 'BLACK', true);


--
-- TOC entry 2963 (class 0 OID 103273)
-- Dependencies: 205
-- Data for Name: profiles_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles_audit (id, revision, revision_type, active_theme, dark_sidebar) VALUES (61, 3, 0, 'BLACK', true);


--
-- TOC entry 2964 (class 0 OID 103278)
-- Dependencies: 206
-- Data for Name: users; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users (id, created_on, updated_on, active, department, email, name, password, store_type, telephone, username, id_group, id_profile) VALUES (56, '2020-01-21 17:47:06.739788', NULL, true, NULL, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$kaSKMDf8vtaoAmn8bPJYAee7jrui5.Icq2AsYz2KbKN3WmN.F/o.6', 'LOCAL', NULL, 'admin', 26, 61);


--
-- TOC entry 2965 (class 0 OID 103286)
-- Dependencies: 207
-- Data for Name: users_audit; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users_audit (id, revision, revision_type, active, department, email, name, password, store_type, telephone, username, id_group, id_profile) VALUES (56, 3, 0, true, NULL, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$kaSKMDf8vtaoAmn8bPJYAee7jrui5.Icq2AsYz2KbKN3WmN.F/o.6', 'LOCAL', NULL, 'admin', 26, 61);


--
-- TOC entry 2971 (class 0 OID 103324)
-- Dependencies: 213
-- Data for Name: revisions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.revisions (id, created_by, created_on) VALUES (1, 'unknown', '2020-01-21 17:47:06.43');
INSERT INTO public.revisions (id, created_by, created_on) VALUES (2, 'unknown', '2020-01-21 17:47:06.542');
INSERT INTO public.revisions (id, created_by, created_on) VALUES (3, 'unknown', '2020-01-21 17:47:06.756');


--
-- TOC entry 2966 (class 0 OID 103296)
-- Dependencies: 208
-- Data for Name: authors; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2967 (class 0 OID 103301)
-- Dependencies: 209
-- Data for Name: authors_audit; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2968 (class 0 OID 103306)
-- Dependencies: 210
-- Data for Name: books; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2969 (class 0 OID 103314)
-- Dependencies: 211
-- Data for Name: books_audit; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2979 (class 0 OID 0)
-- Dependencies: 214
-- Name: pooled_sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.pooled_sequence_generator', 61, true);


--
-- TOC entry 2980 (class 0 OID 0)
-- Dependencies: 212
-- Name: revisions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.revisions_id_seq', 3, true);


--
-- TOC entry 2793 (class 2606 OID 103247)
-- Name: authorizations_audit authorizations_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations_audit
    ADD CONSTRAINT authorizations_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2791 (class 2606 OID 103242)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id);


--
-- TOC entry 2797 (class 2606 OID 103257)
-- Name: grants_audit grants_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants_audit
    ADD CONSTRAINT grants_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2795 (class 2606 OID 103252)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id);


--
-- TOC entry 2801 (class 2606 OID 103267)
-- Name: groups_audit groups_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups_audit
    ADD CONSTRAINT groups_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2799 (class 2606 OID 103262)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2805 (class 2606 OID 103277)
-- Name: profiles_audit profiles_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles_audit
    ADD CONSTRAINT profiles_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2803 (class 2606 OID 103272)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2807 (class 2606 OID 103295)
-- Name: users uk_rcle35tk5t6py9hf7uow9qkcw; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT uk_rcle35tk5t6py9hf7uow9qkcw UNIQUE (id_profile);


--
-- TOC entry 2811 (class 2606 OID 103293)
-- Name: users_audit users_audit_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users_audit
    ADD CONSTRAINT users_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2809 (class 2606 OID 103285)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2821 (class 2606 OID 103329)
-- Name: revisions revisions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions
    ADD CONSTRAINT revisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2815 (class 2606 OID 103305)
-- Name: authors_audit authors_audit_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors_audit
    ADD CONSTRAINT authors_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2813 (class 2606 OID 103300)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- TOC entry 2819 (class 2606 OID 103321)
-- Name: books_audit books_audit_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books_audit
    ADD CONSTRAINT books_audit_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2817 (class 2606 OID 103313)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- TOC entry 2822 (class 2606 OID 103332)
-- Name: authorizations_audit fk_authorizations_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations_audit
    ADD CONSTRAINT fk_authorizations_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2825 (class 2606 OID 103347)
-- Name: grants_audit fk_grants_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants_audit
    ADD CONSTRAINT fk_grants_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2823 (class 2606 OID 103337)
-- Name: grants fk_grants_authorizations; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fk_grants_authorizations FOREIGN KEY (id_authorization) REFERENCES configuration.authorizations(id);


--
-- TOC entry 2824 (class 2606 OID 103342)
-- Name: grants fk_grants_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fk_grants_groups FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2827 (class 2606 OID 103357)
-- Name: groups_audit fk_groups_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups_audit
    ADD CONSTRAINT fk_groups_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2826 (class 2606 OID 103352)
-- Name: groups fk_groups_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT fk_groups_groups FOREIGN KEY (id_parent) REFERENCES configuration.groups(id);


--
-- TOC entry 2828 (class 2606 OID 103362)
-- Name: profiles_audit fk_profiles_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles_audit
    ADD CONSTRAINT fk_profiles_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2831 (class 2606 OID 103377)
-- Name: users_audit fk_users_audit_revisions; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users_audit
    ADD CONSTRAINT fk_users_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2829 (class 2606 OID 103367)
-- Name: users fk_users_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk_users_groups FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2830 (class 2606 OID 103372)
-- Name: users fk_users_profiles; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk_users_profiles FOREIGN KEY (id_profile) REFERENCES configuration.profiles(id);


--
-- TOC entry 2832 (class 2606 OID 103382)
-- Name: authors_audit fk_authors_audit_revisions; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors_audit
    ADD CONSTRAINT fk_authors_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2834 (class 2606 OID 103392)
-- Name: books_audit fk_books_audit_revisions; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books_audit
    ADD CONSTRAINT fk_books_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2833 (class 2606 OID 103387)
-- Name: books fk_books_authors; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT fk_books_authors FOREIGN KEY (id_author) REFERENCES registration.authors(id);


-- Completed on 2020-01-21 20:47:41 UTC

--
-- PostgreSQL database dump complete
--