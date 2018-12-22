--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4 (Debian 10.4-2.pgdg90+1)
-- Dumped by pg_dump version 10.4

-- Started on 2018-12-22 16:43:32 UTC

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
-- TOC entry 5 (class 2615 OID 16388)
-- Name: configuration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration;


--
-- TOC entry 4 (class 2615 OID 16389)
-- Name: configuration_audit; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration_audit;


--
-- TOC entry 10 (class 2615 OID 16390)
-- Name: registration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA registration;


--
-- TOC entry 8 (class 2615 OID 16391)
-- Name: registration_audit; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA registration_audit;


--
-- TOC entry 200 (class 1259 OID 16392)
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
-- TOC entry 201 (class 1259 OID 16397)
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
-- TOC entry 202 (class 1259 OID 16402)
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
-- TOC entry 203 (class 1259 OID 16407)
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
-- TOC entry 204 (class 1259 OID 16412)
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
-- TOC entry 205 (class 1259 OID 16422)
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
-- TOC entry 206 (class 1259 OID 16427)
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
-- TOC entry 207 (class 1259 OID 16432)
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
-- TOC entry 208 (class 1259 OID 16437)
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
-- TOC entry 209 (class 1259 OID 16442)
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
-- TOC entry 216 (class 1259 OID 16484)
-- Name: pooled_sequence_generator; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pooled_sequence_generator
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 215 (class 1259 OID 16478)
-- Name: revisions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.revisions (
    id bigint NOT NULL,
    created_by character varying(45) NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 214 (class 1259 OID 16476)
-- Name: revisions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.revisions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2983 (class 0 OID 0)
-- Dependencies: 214
-- Name: revisions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.revisions_id_seq OWNED BY public.revisions.id;


--
-- TOC entry 210 (class 1259 OID 16450)
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
-- TOC entry 211 (class 1259 OID 16455)
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
-- TOC entry 212 (class 1259 OID 16463)
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
-- TOC entry 213 (class 1259 OID 16468)
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
-- TOC entry 2791 (class 2604 OID 16481)
-- Name: revisions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions ALTER COLUMN id SET DEFAULT nextval('public.revisions_id_seq'::regclass);


--
-- TOC entry 2958 (class 0 OID 16392)
-- Dependencies: 200
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.authorizations VALUES (1, '2018-12-22 14:33:51.982946', NULL, 'author', 'add');
INSERT INTO configuration.authorizations VALUES (2, '2018-12-22 14:33:52.014943', NULL, 'author', 'update');
INSERT INTO configuration.authorizations VALUES (3, '2018-12-22 14:33:52.020943', NULL, 'author', 'delete');
INSERT INTO configuration.authorizations VALUES (4, '2018-12-22 14:33:52.026943', NULL, 'author', 'detail');
INSERT INTO configuration.authorizations VALUES (5, '2018-12-22 14:33:52.032946', NULL, 'author', 'access');
INSERT INTO configuration.authorizations VALUES (6, '2018-12-22 14:33:52.040946', NULL, 'book', 'add');
INSERT INTO configuration.authorizations VALUES (7, '2018-12-22 14:33:52.049047', NULL, 'book', 'update');
INSERT INTO configuration.authorizations VALUES (8, '2018-12-22 14:33:52.055044', NULL, 'book', 'delete');
INSERT INTO configuration.authorizations VALUES (9, '2018-12-22 14:33:52.064045', NULL, 'book', 'detail');
INSERT INTO configuration.authorizations VALUES (10, '2018-12-22 14:33:52.077045', NULL, 'book', 'access');
INSERT INTO configuration.authorizations VALUES (11, '2018-12-22 14:33:52.084048', NULL, 'user', 'add');
INSERT INTO configuration.authorizations VALUES (12, '2018-12-22 14:33:52.098044', NULL, 'user', 'update');
INSERT INTO configuration.authorizations VALUES (13, '2018-12-22 14:33:52.105044', NULL, 'user', 'delete');
INSERT INTO configuration.authorizations VALUES (14, '2018-12-22 14:33:52.112044', NULL, 'user', 'detail');
INSERT INTO configuration.authorizations VALUES (15, '2018-12-22 14:33:52.122043', NULL, 'user', 'access');
INSERT INTO configuration.authorizations VALUES (16, '2018-12-22 14:33:52.13105', NULL, 'group', 'add');
INSERT INTO configuration.authorizations VALUES (17, '2018-12-22 14:33:52.141045', NULL, 'group', 'update');
INSERT INTO configuration.authorizations VALUES (18, '2018-12-22 14:33:52.148043', NULL, 'group', 'delete');
INSERT INTO configuration.authorizations VALUES (19, '2018-12-22 14:33:52.153043', NULL, 'group', 'detail');
INSERT INTO configuration.authorizations VALUES (20, '2018-12-22 14:33:52.158045', NULL, 'group', 'access');


--
-- TOC entry 2959 (class 0 OID 16397)
-- Dependencies: 201
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.grants VALUES (26, '2018-12-22 14:33:52.196045', NULL, 1, 21);
INSERT INTO configuration.grants VALUES (27, '2018-12-22 14:33:52.199042', NULL, 2, 21);
INSERT INTO configuration.grants VALUES (28, '2018-12-22 14:33:52.199042', NULL, 3, 21);
INSERT INTO configuration.grants VALUES (29, '2018-12-22 14:33:52.199042', NULL, 4, 21);
INSERT INTO configuration.grants VALUES (30, '2018-12-22 14:33:52.200042', NULL, 5, 21);
INSERT INTO configuration.grants VALUES (31, '2018-12-22 14:33:52.200042', NULL, 6, 21);
INSERT INTO configuration.grants VALUES (32, '2018-12-22 14:33:52.203042', NULL, 7, 21);
INSERT INTO configuration.grants VALUES (33, '2018-12-22 14:33:52.203042', NULL, 8, 21);
INSERT INTO configuration.grants VALUES (34, '2018-12-22 14:33:52.204041', NULL, 9, 21);
INSERT INTO configuration.grants VALUES (35, '2018-12-22 14:33:52.204041', NULL, 10, 21);
INSERT INTO configuration.grants VALUES (36, '2018-12-22 14:33:52.204041', NULL, 11, 21);
INSERT INTO configuration.grants VALUES (37, '2018-12-22 14:33:52.207048', NULL, 12, 21);
INSERT INTO configuration.grants VALUES (38, '2018-12-22 14:33:52.207048', NULL, 13, 21);
INSERT INTO configuration.grants VALUES (39, '2018-12-22 14:33:52.208043', NULL, 14, 21);
INSERT INTO configuration.grants VALUES (40, '2018-12-22 14:33:52.208043', NULL, 15, 21);
INSERT INTO configuration.grants VALUES (41, '2018-12-22 14:33:52.208043', NULL, 16, 21);
INSERT INTO configuration.grants VALUES (42, '2018-12-22 14:33:52.210041', NULL, 17, 21);
INSERT INTO configuration.grants VALUES (43, '2018-12-22 14:33:52.211044', NULL, 18, 21);
INSERT INTO configuration.grants VALUES (44, '2018-12-22 14:33:52.211044', NULL, 19, 21);
INSERT INTO configuration.grants VALUES (45, '2018-12-22 14:33:52.211044', NULL, 20, 21);


--
-- TOC entry 2960 (class 0 OID 16402)
-- Dependencies: 202
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups VALUES (21, '2018-12-22 14:33:52.166044', NULL, true, 'Administradores', NULL);


--
-- TOC entry 2961 (class 0 OID 16407)
-- Dependencies: 203
-- Data for Name: profiles; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles VALUES (51, '2018-12-22 14:33:52.302044', NULL, 'BLACK', true);


--
-- TOC entry 2962 (class 0 OID 16412)
-- Dependencies: 204
-- Data for Name: users; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users VALUES (46, '2018-12-22 14:33:52.299044', NULL, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$bC4k82hWdteN4rbiSZuP1eNlmiiQ1X6Dr94zt0mZxhtVl0FJd4LZm', 'LOCAL', 'admin', 21, 51);


--
-- TOC entry 2963 (class 0 OID 16422)
-- Dependencies: 205
-- Data for Name: audit_profiles; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.audit_profiles VALUES (51, 1, 0, 'BLACK', true);


--
-- TOC entry 2964 (class 0 OID 16427)
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
-- TOC entry 2965 (class 0 OID 16432)
-- Dependencies: 207
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.grants VALUES (26, 1, 0, 1, 21);
INSERT INTO configuration_audit.grants VALUES (27, 1, 0, 2, 21);
INSERT INTO configuration_audit.grants VALUES (28, 1, 0, 3, 21);
INSERT INTO configuration_audit.grants VALUES (29, 1, 0, 4, 21);
INSERT INTO configuration_audit.grants VALUES (30, 1, 0, 5, 21);
INSERT INTO configuration_audit.grants VALUES (31, 1, 0, 6, 21);
INSERT INTO configuration_audit.grants VALUES (32, 1, 0, 7, 21);
INSERT INTO configuration_audit.grants VALUES (33, 1, 0, 8, 21);
INSERT INTO configuration_audit.grants VALUES (34, 1, 0, 9, 21);
INSERT INTO configuration_audit.grants VALUES (35, 1, 0, 10, 21);
INSERT INTO configuration_audit.grants VALUES (36, 1, 0, 11, 21);
INSERT INTO configuration_audit.grants VALUES (37, 1, 0, 12, 21);
INSERT INTO configuration_audit.grants VALUES (38, 1, 0, 13, 21);
INSERT INTO configuration_audit.grants VALUES (39, 1, 0, 14, 21);
INSERT INTO configuration_audit.grants VALUES (40, 1, 0, 15, 21);
INSERT INTO configuration_audit.grants VALUES (41, 1, 0, 16, 21);
INSERT INTO configuration_audit.grants VALUES (42, 1, 0, 17, 21);
INSERT INTO configuration_audit.grants VALUES (43, 1, 0, 18, 21);
INSERT INTO configuration_audit.grants VALUES (44, 1, 0, 19, 21);
INSERT INTO configuration_audit.grants VALUES (45, 1, 0, 20, 21);


--
-- TOC entry 2966 (class 0 OID 16437)
-- Dependencies: 208
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.groups VALUES (21, 1, 0, true, 'Administradores', NULL);


--
-- TOC entry 2967 (class 0 OID 16442)
-- Dependencies: 209
-- Data for Name: users; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.users VALUES (46, 1, 0, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$bC4k82hWdteN4rbiSZuP1eNlmiiQ1X6Dr94zt0mZxhtVl0FJd4LZm', 'LOCAL', 'admin', 21, 51);


--
-- TOC entry 2973 (class 0 OID 16478)
-- Dependencies: 215
-- Data for Name: revisions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.revisions VALUES (1, 'unknown', '2018-12-22 14:33:52.363');


--
-- TOC entry 2968 (class 0 OID 16450)
-- Dependencies: 210
-- Data for Name: authors; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2969 (class 0 OID 16455)
-- Dependencies: 211
-- Data for Name: books; Type: TABLE DATA; Schema: registration; Owner: -
--



--
-- TOC entry 2970 (class 0 OID 16463)
-- Dependencies: 212
-- Data for Name: authors; Type: TABLE DATA; Schema: registration_audit; Owner: -
--



--
-- TOC entry 2971 (class 0 OID 16468)
-- Dependencies: 213
-- Data for Name: books; Type: TABLE DATA; Schema: registration_audit; Owner: -
--



--
-- TOC entry 2984 (class 0 OID 0)
-- Dependencies: 216
-- Name: pooled_sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.pooled_sequence_generator', 51, true);


--
-- TOC entry 2985 (class 0 OID 0)
-- Dependencies: 214
-- Name: revisions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.revisions_id_seq', 1, true);


--
-- TOC entry 2793 (class 2606 OID 16396)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id);


--
-- TOC entry 2795 (class 2606 OID 16401)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id);


--
-- TOC entry 2797 (class 2606 OID 16406)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2799 (class 2606 OID 16411)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2801 (class 2606 OID 16421)
-- Name: users uk_rcle35tk5t6py9hf7uow9qkcw; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT uk_rcle35tk5t6py9hf7uow9qkcw UNIQUE (id_profile);


--
-- TOC entry 2803 (class 2606 OID 16419)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2805 (class 2606 OID 16426)
-- Name: audit_profiles audit_profiles_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.audit_profiles
    ADD CONSTRAINT audit_profiles_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2807 (class 2606 OID 16431)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2809 (class 2606 OID 16436)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2811 (class 2606 OID 16441)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2813 (class 2606 OID 16449)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2823 (class 2606 OID 16483)
-- Name: revisions revisions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions
    ADD CONSTRAINT revisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2815 (class 2606 OID 16454)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- TOC entry 2817 (class 2606 OID 16462)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- TOC entry 2819 (class 2606 OID 16467)
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2821 (class 2606 OID 16475)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id, revision);


--
-- TOC entry 2827 (class 2606 OID 16501)
-- Name: users fk6ygxt7686dodh2vla65342ct4; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk6ygxt7686dodh2vla65342ct4 FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2828 (class 2606 OID 16506)
-- Name: users fk9wbwuppwb63kwaj2s6aaqi3th; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk9wbwuppwb63kwaj2s6aaqi3th FOREIGN KEY (id_profile) REFERENCES configuration.profiles(id);


--
-- TOC entry 2825 (class 2606 OID 16491)
-- Name: grants fkchpf69027hrvhv43xo5ijglg9; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fkchpf69027hrvhv43xo5ijglg9 FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2826 (class 2606 OID 16496)
-- Name: groups fkjofv20ia9udcgcbtkows5p05; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT fkjofv20ia9udcgcbtkows5p05 FOREIGN KEY (id_parent) REFERENCES configuration.groups(id);


--
-- TOC entry 2824 (class 2606 OID 16486)
-- Name: grants fkojxnxlqxe5rkbrn1ebp14hkhf; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fkojxnxlqxe5rkbrn1ebp14hkhf FOREIGN KEY (id_authorization) REFERENCES configuration.authorizations(id);


--
-- TOC entry 2830 (class 2606 OID 16516)
-- Name: authorizations fk22ub5uj9vtbapmrymbj42nl5y; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.authorizations
    ADD CONSTRAINT fk22ub5uj9vtbapmrymbj42nl5y FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2833 (class 2606 OID 16531)
-- Name: users fka52fjge29y0d6mdxu0m11jgu7; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.users
    ADD CONSTRAINT fka52fjge29y0d6mdxu0m11jgu7 FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2832 (class 2606 OID 16526)
-- Name: groups fkaxo8cbaxjeknqqnqn9styfwhb; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.groups
    ADD CONSTRAINT fkaxo8cbaxjeknqqnqn9styfwhb FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2831 (class 2606 OID 16521)
-- Name: grants fkb4b62ghhbe0pff4fvfqkta69w; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.grants
    ADD CONSTRAINT fkb4b62ghhbe0pff4fvfqkta69w FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2829 (class 2606 OID 16511)
-- Name: audit_profiles fkmtvbb3btq21gk0viyyj3hx2cc; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.audit_profiles
    ADD CONSTRAINT fkmtvbb3btq21gk0viyyj3hx2cc FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2834 (class 2606 OID 16536)
-- Name: books fk4ih38omjrl63dv9509hddgyia; Type: FK CONSTRAINT; Schema: registration; Owner: -
--

ALTER TABLE ONLY registration.books
    ADD CONSTRAINT fk4ih38omjrl63dv9509hddgyia FOREIGN KEY (id_author) REFERENCES registration.authors(id);


--
-- TOC entry 2835 (class 2606 OID 16541)
-- Name: authors fkgjhek5069rmo9a4gmocj621k8; Type: FK CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.authors
    ADD CONSTRAINT fkgjhek5069rmo9a4gmocj621k8 FOREIGN KEY (revision) REFERENCES public.revisions(id);


--
-- TOC entry 2836 (class 2606 OID 16546)
-- Name: books fkrn2u9utv4htfq3covrnjqxngr; Type: FK CONSTRAINT; Schema: registration_audit; Owner: -
--

ALTER TABLE ONLY registration_audit.books
    ADD CONSTRAINT fkrn2u9utv4htfq3covrnjqxngr FOREIGN KEY (revision) REFERENCES public.revisions(id);


-- Completed on 2018-12-22 16:43:32 UTC

--
-- PostgreSQL database dump complete
--