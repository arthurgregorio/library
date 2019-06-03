--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4 (Debian 10.4-2.pgdg90+1)
-- Dumped by pg_dump version 11.2

-- Started on 2019-06-03 17:15:32 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 98649)
-- Name: configuration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration;


--
-- TOC entry 11 (class 2615 OID 98651)
-- Name: configuration_audit; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration_audit;


--
-- TOC entry 5 (class 2615 OID 98650)
-- Name: registration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA registration;


--
-- TOC entry 6 (class 2615 OID 98652)
-- Name: registration_audit; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA registration_audit;


SET default_with_oids = false;

--
-- TOC entry 200 (class 1259 OID 98653)
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
-- TOC entry 201 (class 1259 OID 98658)
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
-- TOC entry 202 (class 1259 OID 98663)
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
-- TOC entry 203 (class 1259 OID 98668)
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
-- TOC entry 204 (class 1259 OID 98673)
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
-- TOC entry 205 (class 1259 OID 98683)
-- Name: audit_profiles; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.audit_profiles (
                                                    id bigint NOT NULL,
                                                    revision bigint NOT NULL,
                                                    revision_type smallint,
                                                    active_theme character varying(45),
                                                    dark_sidebar boolean
);


--
-- TOC entry 206 (class 1259 OID 98688)
-- Name: authorizations; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.authorizations (
                                                    id bigint NOT NULL,
                                                    revision bigint NOT NULL,
                                                    revision_type smallint,
                                                    functionality character varying(90),
                                                    permission character varying(90)
);


--
-- TOC entry 207 (class 1259 OID 98693)
-- Name: grants; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.grants (
                                            id bigint NOT NULL,
                                            revision bigint NOT NULL,
                                            revision_type smallint,
                                            id_authorization bigint,
                                            id_group bigint
);


--
-- TOC entry 208 (class 1259 OID 98698)
-- Name: groups; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.groups (
                                            id bigint NOT NULL,
                                            revision bigint NOT NULL,
                                            revision_type smallint,
                                            active boolean,
                                            name character varying(45),
                                            id_parent bigint
);


--
-- TOC entry 209 (class 1259 OID 98703)
-- Name: users; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.users (
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
-- TOC entry 216 (class 1259 OID 98745)
-- Name: pooled_sequence_generator; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pooled_sequence_generator
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 215 (class 1259 OID 98739)
-- Name: revisions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.revisions (
                                  id bigint NOT NULL,
                                  created_by character varying(45) NOT NULL,
                                  created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 98737)
-- Name: revisions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.revisions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2980 (class 0 OID 0)
-- Dependencies: 214
-- Name: revisions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.revisions_id_seq OWNED BY public.revisions.id;


--
-- TOC entry 210 (class 1259 OID 98711)
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
-- TOC entry 211 (class 1259 OID 98716)
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
-- TOC entry 212 (class 1259 OID 98724)
-- Name: authors; Type: TABLE; Schema: registration_audit; Owner: -
--

CREATE TABLE registration_audit.authors (
                                            id bigint NOT NULL,
                                            revision bigint NOT NULL,
                                            revision_type smallint,
                                            active boolean,
                                            born_date date,
                                            email character varying(90),
                                            name character varying(90)
);


--
-- TOC entry 213 (class 1259 OID 98729)
-- Name: books; Type: TABLE; Schema: registration_audit; Owner: -
--

CREATE TABLE registration_audit.books (
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
-- TOC entry 2791 (class 2604 OID 98742)
-- Name: revisions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions ALTER COLUMN id SET DEFAULT nextval('public.revisions_id_seq'::regclass);


--
-- TOC entry 2958 (class 0 OID 98653)
-- Dependencies: 200
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.authorizations VALUES (1, '2019-06-03 14:13:35.58897', NULL, 'author', 'add');
INSERT INTO configuration.authorizations VALUES (2, '2019-06-03 14:13:35.611705', NULL, 'author', 'update');
INSERT INTO configuration.authorizations VALUES (3, '2019-06-03 14:13:35.614482', NULL, 'author', 'delete');
INSERT INTO configuration.authorizations VALUES (4, '2019-06-03 14:13:35.617088', NULL, 'author', 'detail');
INSERT INTO configuration.authorizations VALUES (5, '2019-06-03 14:13:35.620071', NULL, 'author', 'access');
INSERT INTO configuration.authorizations VALUES (6, '2019-06-03 14:13:35.624173', NULL, 'book', 'add');
INSERT INTO configuration.authorizations VALUES (7, '2019-06-03 14:13:35.628896', NULL, 'book', 'update');
INSERT INTO configuration.authorizations VALUES (8, '2019-06-03 14:13:35.632599', NULL, 'book', 'delete');
INSERT INTO configuration.authorizations VALUES (9, '2019-06-03 14:13:35.636819', NULL, 'book', 'detail');
INSERT INTO configuration.authorizations VALUES (10, '2019-06-03 14:13:35.640919', NULL, 'book', 'access');
INSERT INTO configuration.authorizations VALUES (11, '2019-06-03 14:13:35.644318', NULL, 'user', 'add');
INSERT INTO configuration.authorizations VALUES (12, '2019-06-03 14:13:35.648513', NULL, 'user', 'update');
INSERT INTO configuration.authorizations VALUES (13, '2019-06-03 14:13:35.651986', NULL, 'user', 'delete');
INSERT INTO configuration.authorizations VALUES (14, '2019-06-03 14:13:35.655625', NULL, 'user', 'detail');
INSERT INTO configuration.authorizations VALUES (15, '2019-06-03 14:13:35.65936', NULL, 'user', 'access');
INSERT INTO configuration.authorizations VALUES (16, '2019-06-03 14:13:35.664415', NULL, 'group', 'add');
INSERT INTO configuration.authorizations VALUES (17, '2019-06-03 14:13:35.669318', NULL, 'group', 'update');
INSERT INTO configuration.authorizations VALUES (18, '2019-06-03 14:13:35.672577', NULL, 'group', 'delete');
INSERT INTO configuration.authorizations VALUES (19, '2019-06-03 14:13:35.675901', NULL, 'group', 'detail');
INSERT INTO configuration.authorizations VALUES (20, '2019-06-03 14:13:35.679099', NULL, 'group', 'access');


--
-- TOC entry 2959 (class 0 OID 98658)
-- Dependencies: 201
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.grants VALUES (26, '2019-06-03 14:13:35.750801', NULL, 1, 21);
INSERT INTO configuration.grants VALUES (27, '2019-06-03 14:13:35.752084', NULL, 2, 21);
INSERT INTO configuration.grants VALUES (28, '2019-06-03 14:13:35.752436', NULL, 3, 21);
INSERT INTO configuration.grants VALUES (29, '2019-06-03 14:13:35.752704', NULL, 4, 21);
INSERT INTO configuration.grants VALUES (30, '2019-06-03 14:13:35.752953', NULL, 5, 21);
INSERT INTO configuration.grants VALUES (31, '2019-06-03 14:13:35.753196', NULL, 6, 21);
INSERT INTO configuration.grants VALUES (32, '2019-06-03 14:13:35.754267', NULL, 7, 21);
INSERT INTO configuration.grants VALUES (33, '2019-06-03 14:13:35.754558', NULL, 8, 21);
INSERT INTO configuration.grants VALUES (34, '2019-06-03 14:13:35.754771', NULL, 9, 21);
INSERT INTO configuration.grants VALUES (35, '2019-06-03 14:13:35.754979', NULL, 10, 21);
INSERT INTO configuration.grants VALUES (36, '2019-06-03 14:13:35.7552', NULL, 11, 21);
INSERT INTO configuration.grants VALUES (37, '2019-06-03 14:13:35.756131', NULL, 12, 21);
INSERT INTO configuration.grants VALUES (38, '2019-06-03 14:13:35.756326', NULL, 13, 21);
INSERT INTO configuration.grants VALUES (39, '2019-06-03 14:13:35.756507', NULL, 14, 21);
INSERT INTO configuration.grants VALUES (40, '2019-06-03 14:13:35.756715', NULL, 15, 21);
INSERT INTO configuration.grants VALUES (41, '2019-06-03 14:13:35.756894', NULL, 16, 21);
INSERT INTO configuration.grants VALUES (42, '2019-06-03 14:13:35.757724', NULL, 17, 21);
INSERT INTO configuration.grants VALUES (43, '2019-06-03 14:13:35.757918', NULL, 18, 21);
INSERT INTO configuration.grants VALUES (44, '2019-06-03 14:13:35.758103', NULL, 19, 21);
INSERT INTO configuration.grants VALUES (45, '2019-06-03 14:13:35.758383', NULL, 20, 21);


--
-- TOC entry 2960 (class 0 OID 98663)
-- Dependencies: 202
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups VALUES (21, '2019-06-03 14:13:35.734939', NULL, true, 'Administradores', NULL);


--
-- TOC entry 2961 (class 0 OID 98668)
-- Dependencies: 203
-- Data for Name: profiles; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles VALUES (51, '2019-06-03 14:13:35.926293', NULL, 'BLACK', true);


--
-- TOC entry 2962 (class 0 OID 98673)
-- Dependencies: 204
-- Data for Name: users; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users VALUES (46, '2019-06-03 14:13:35.925151', NULL, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$fEDOoH8/X0KuCSKDz4NqBueFDnIItzhbXS9yW3T4RV.0GHO9/Ad.C', 'LOCAL', 'admin', 21, 51);


--
-- TOC entry 2963 (class 0 OID 98683)
-- Dependencies: 205
-- Data for Name: audit_profiles; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.audit_profiles VALUES (51, 3, 0, 'BLACK', true);


--
-- TOC entry 2964 (class 0 OID 98688)
-- Dependencies: 206
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.authorizations VALUES (1, 1, 0, 'author', 'add');
INSERT INTO configuration_audit.authorizations VALUES (2, 1, 0, 'author', 'update');
INSERT INTO configuration_audit.authorizations VALUES (3, 1, 0, 'author', 'delete');
INSERT INTO configuration_audit.authorizations VALUES (4, 1, 0, 'author', 'detail');
INSERT INTO configuration_audit.authorizations VALUES (5, 1, 0, 'author', 'access');
INSERT INTO configuration_audit.authorizations VALUES (6, 1, 0, 'book', 'add');
INSERT INTO configuration_audit.authorizations VALUES (7, 1, 0, 'book', 'update');
INSERT INTO configuration_audit.authorizations VALUES (8, 1, 0, 'book', 'delete');
INSERT INTO configuration_audit.authorizations VALUES (9, 1, 0, 'book', 'detail');
INSERT INTO configuration_audit.authorizations VALUES (10, 1, 0, 'book', 'access');
INSERT INTO configuration_audit.authorizations VALUES (11, 1, 0, 'user', 'add');
INSERT INTO configuration_audit.authorizations VALUES (12, 1, 0, 'user', 'update');
INSERT INTO configuration_audit.authorizations VALUES (13, 1, 0, 'user', 'delete');
INSERT INTO configuration_audit.authorizations VALUES (14, 1, 0, 'user', 'detail');
INSERT INTO configuration_audit.authorizations VALUES (15, 1, 0, 'user', 'access');
INSERT INTO configuration_audit.authorizations VALUES (16, 1, 0, 'group', 'add');
INSERT INTO configuration_audit.authorizations VALUES (17, 1, 0, 'group', 'update');
INSERT INTO configuration_audit.authorizations VALUES (18, 1, 0, 'group', 'delete');
INSERT INTO configuration_audit.authorizations VALUES (19, 1, 0, 'group', 'detail');
INSERT INTO configuration_audit.authorizations VALUES (20, 1, 0, 'group', 'access');


--
-- TOC entry 2965 (class 0 OID 98693)
-- Dependencies: 207
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.grants VALUES (26, 2, 0, 1, 21);
INSERT INTO configuration_audit.grants VALUES (27, 2, 0, 2, 21);
INSERT INTO configuration_audit.grants VALUES (28, 2, 0, 3, 21);
INSERT INTO configuration_audit.grants VALUES (29, 2, 0, 4, 21);
INSERT INTO configuration_audit.grants VALUES (30, 2, 0, 5, 21);
INSERT INTO configuration_audit.grants VALUES (31, 2, 0, 6, 21);
INSERT INTO configuration_audit.grants VALUES (32, 2, 0, 7, 21);
INSERT INTO configuration_audit.grants VALUES (33, 2, 0, 8, 21);
INSERT INTO configuration_audit.grants VALUES (34, 2, 0, 9, 21);
INSERT INTO configuration_audit.grants VALUES (35, 2, 0, 10, 21);
INSERT INTO configuration_audit.grants VALUES (36, 2, 0, 11, 21);
INSERT INTO configuration_audit.grants VALUES (37, 2, 0, 12, 21);
INSERT INTO configuration_audit.grants VALUES (38, 2, 0, 13, 21);
INSERT INTO configuration_audit.grants VALUES (39, 2, 0, 14, 21);
INSERT INTO configuration_audit.grants VALUES (40, 2, 0, 15, 21);
INSERT INTO configuration_audit.grants VALUES (41, 2, 0, 16, 21);
INSERT INTO configuration_audit.grants VALUES (42, 2, 0, 17, 21);
INSERT INTO configuration_audit.grants VALUES (43, 2, 0, 18, 21);
INSERT INTO configuration_audit.grants VALUES (44, 2, 0, 19, 21);
INSERT INTO configuration_audit.grants VALUES (45, 2, 0, 20, 21);


--
-- TOC entry 2966 (class 0 OID 98698)
-- Dependencies: 208
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.groups VALUES (21, 2, 0, true, 'Administradores', NULL);


--
-- TOC entry 2967 (class 0 OID 98703)
-- Dependencies: 209
-- Data for Name: users; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.users VALUES (46, 3, 0, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$fEDOoH8/X0KuCSKDz4NqBueFDnIItzhbXS9yW3T4RV.0GHO9/Ad.C', 'LOCAL', 'admin', 21, 51);


--
-- TOC entry 2973 (class 0 OID 98739)
-- Dependencies: 215
-- Data for Name: revisions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.revisions VALUES (1, 'unknown', '2019-06-03 14:13:35.682');
INSERT INTO public.revisions VALUES (2, 'unknown', '2019-06-03 14:13:35.778');
INSERT INTO public.revisions VALUES (3, 'unknown', '2019-06-03 14:13:35.931');


--
-- TOC entry 2968 (class 0 OID 98711)
-- Dependencies: 210
-- Data for Name: authors; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2969 (class 0 OID 98716)
-- Dependencies: 211
-- Data for Name: books; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2970 (class 0 OID 98724)
-- Dependencies: 212
-- Data for Name: authors; Type: TABLE DATA; Schema: registration_audit; Owner: -
--



--
-- TOC entry 2971 (class 0 OID 98729)
-- Dependencies: 213
-- Data for Name: books; Type: TABLE DATA; Schema: registration_audit; Owner: -
--



--
-- TOC entry 2981 (class 0 OID 0)
-- Dependencies: 216
-- Name: pooled_sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.pooled_sequence_generator', 51, true);


--
-- TOC entry 2982 (class 0 OID 0)
-- Dependencies: 214
-- Name: revisions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.revisions_id_seq', 3, true);


--
-- TOC entry 2793 (class 2606 OID 98657)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id);


--
-- TOC entry 2795 (class 2606 OID 98662)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id);


--
-- TOC entry 2797 (class 2606 OID 98667)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2799 (class 2606 OID 98672)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2801 (class 2606 OID 98682)
-- Name: users uk_rcle35tk5t6py9hf7uow9qkcw; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT uk_rcle35tk5t6py9hf7uow9qkcw UNIQUE (id_profile);


--
-- TOC entry 2803 (class 2606 OID 98680)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2805 (class 2606 OID 98687)
-- Name: audit_profiles audit_profiles_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.audit_profiles
    ADD CONSTRAINT audit_profiles_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2807 (class 2606 OID 98692)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2809 (class 2606 OID 98697)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2811 (class 2606 OID 98702)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2813 (class 2606 OID 98710)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2823 (class 2606 OID 98744)
-- Name: revisions revisions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions
    ADD CONSTRAINT revisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2815 (class 2606 OID 98715)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- TOC entry 2817 (class 2606 OID 98723)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- TOC entry 2819 (class 2606 OID 98728)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2821 (class 2606 OID 98736)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2824 (class 2606 OID 98747)
-- Name: grants fk_grants_authorizations; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fk_grants_authorizations FOREIGN KEY (id_authorization) REFERENCES configuration.authorizations(id);


--
-- TOC entry 2825 (class 2606 OID 98752)
-- Name: grants fk_grants_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fk_grants_groups FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2826 (class 2606 OID 98757)
-- Name: groups fk_groups_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT fk_groups_groups FOREIGN KEY (id_parent) REFERENCES configuration.groups(id);


--
-- TOC entry 2827 (class 2606 OID 98762)
-- Name: users fk_users_groups; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk_users_groups FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2828 (class 2606 OID 98767)
-- Name: users fk_users_profiles; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk_users_profiles FOREIGN KEY (id_profile) REFERENCES configuration.profiles(id);


--
-- TOC entry 2829 (class 2606 OID 98772)
-- Name: audit_profiles fk_audit_profiles_revisions; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.audit_profiles
    ADD CONSTRAINT fk_audit_profiles_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2830 (class 2606 OID 98777)
-- Name: authorizations fk_authorizations_revisions; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.authorizations
    ADD CONSTRAINT fk_authorizations_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2831 (class 2606 OID 98782)
-- Name: grants fk_grants_revisions; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.grants
    ADD CONSTRAINT fk_grants_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2832 (class 2606 OID 98787)
-- Name: groups fk_groups_revisions; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.groups
    ADD CONSTRAINT fk_groups_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2833 (class 2606 OID 98792)
-- Name: users fk_users_revisions; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.users
    ADD CONSTRAINT fk_users_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2834 (class 2606 OID 98797)
-- Name: books fk_books_authors; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT fk_books_authors FOREIGN KEY (id_author) REFERENCES registration.authors(id);


--
-- TOC entry 2835 (class 2606 OID 98802)
-- Name: authors fk_authors_revisions; Type: FK CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.authors
    ADD CONSTRAINT fk_authors_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2836 (class 2606 OID 98807)
-- Name: books fk_books_revisions; Type: FK CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.books
    ADD CONSTRAINT fk_books_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id);


-- Completed on 2019-06-03 17:15:32 UTC

--
-- PostgreSQL database dump complete
--