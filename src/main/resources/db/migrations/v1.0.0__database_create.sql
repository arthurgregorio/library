--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4 (Debian 10.4-2.pgdg90+1)
-- Dumped by pg_dump version 10.4

-- Started on 2018-11-14 12:34:12 UTC

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
-- TOC entry 8 (class 2615 OID 50139)
-- Name: configuration; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration;


--
-- TOC entry 7 (class 2615 OID 50140)
-- Name: configuration_audit; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA configuration_audit;


--
-- TOC entry 198 (class 1259 OID 50141)
-- Name: authorizations; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.authorizations (
    id bigint NOT NULL,
    functionality character varying(90) NOT NULL,
    permission character varying(90) NOT NULL
);


--
-- TOC entry 199 (class 1259 OID 50146)
-- Name: grants; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.grants (
    id bigint NOT NULL,
    id_authorization bigint NOT NULL,
    id_group bigint NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 50151)
-- Name: groups; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.groups (
    id bigint NOT NULL,
    active boolean NOT NULL,
    name character varying(45) NOT NULL,
    id_parent bigint
);


--
-- TOC entry 201 (class 1259 OID 50156)
-- Name: profiles; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.profiles (
    id bigint NOT NULL,
    active_theme character varying(45) NOT NULL,
    dark_sidebar boolean NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 50161)
-- Name: users; Type: TABLE; Schema: configuration; Owner: -
--

CREATE TABLE configuration.users (
    id bigint NOT NULL,
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
-- TOC entry 203 (class 1259 OID 50171)
-- Name: audit_profiles; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.audit_profiles (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    active_theme character varying(45),
    dark_sidebar boolean
);


--
-- TOC entry 204 (class 1259 OID 50176)
-- Name: authorizations; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.authorizations (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    functionality character varying(90),
    permission character varying(90)
);


--
-- TOC entry 205 (class 1259 OID 50181)
-- Name: grants; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.grants (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    id_authorization bigint,
    id_group bigint
);


--
-- TOC entry 206 (class 1259 OID 50186)
-- Name: groups; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.groups (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    active boolean,
    name character varying(45),
    id_parent bigint
);


--
-- TOC entry 207 (class 1259 OID 50191)
-- Name: users; Type: TABLE; Schema: configuration_audit; Owner: -
--

CREATE TABLE configuration_audit.users (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
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
-- TOC entry 210 (class 1259 OID 50207)
-- Name: pooled_sequence_generator; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pooled_sequence_generator
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 209 (class 1259 OID 50201)
-- Name: revisions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.revisions (
    id bigint NOT NULL,
    created_by character varying(45) NOT NULL,
    created_on timestamp without time zone NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 50199)
-- Name: revisions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.revisions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2948 (class 0 OID 0)
-- Dependencies: 208
-- Name: revisions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.revisions_id_seq OWNED BY public.revisions.id;


--
-- TOC entry 2771 (class 2604 OID 50204)
-- Name: revisions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions ALTER COLUMN id SET DEFAULT nextval('public.revisions_id_seq'::regclass);


--
-- TOC entry 2927 (class 0 OID 50141)
-- Dependencies: 198
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration; Owner: -
--



--
-- TOC entry 2928 (class 0 OID 50146)
-- Dependencies: 199
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration; Owner: -
--



--
-- TOC entry 2929 (class 0 OID 50151)
-- Dependencies: 200
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.groups VALUES (1, true, 'Administradores', NULL);


--
-- TOC entry 2930 (class 0 OID 50156)
-- Dependencies: 201
-- Data for Name: profiles; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.profiles VALUES (11, 'BLACK', true);


--
-- TOC entry 2931 (class 0 OID 50161)
-- Dependencies: 202
-- Data for Name: users; Type: TABLE DATA; Schema: configuration; Owner: -
--

INSERT INTO configuration.users VALUES (6, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$zIp/Ul7t6opfmOTSqhXvHOxazmbOL.hdoM0zW.dBEQucOI2Y32.bm', 'LOCAL', 'admin', 1, 11);


--
-- TOC entry 2932 (class 0 OID 50171)
-- Dependencies: 203
-- Data for Name: audit_profiles; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.audit_profiles VALUES (11, 1, 0, 'BLACK', true);


--
-- TOC entry 2933 (class 0 OID 50176)
-- Dependencies: 204
-- Data for Name: authorizations; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--



--
-- TOC entry 2934 (class 0 OID 50181)
-- Dependencies: 205
-- Data for Name: grants; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--



--
-- TOC entry 2935 (class 0 OID 50186)
-- Dependencies: 206
-- Data for Name: groups; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.groups VALUES (1, 1, 0, true, 'Administradores', NULL);


--
-- TOC entry 2936 (class 0 OID 50191)
-- Dependencies: 207
-- Data for Name: users; Type: TABLE DATA; Schema: configuration_audit; Owner: -
--

INSERT INTO configuration_audit.users VALUES (6, 1, 0, true, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$zIp/Ul7t6opfmOTSqhXvHOxazmbOL.hdoM0zW.dBEQucOI2Y32.bm', 'LOCAL', 'admin', 1, 11);


--
-- TOC entry 2938 (class 0 OID 50201)
-- Dependencies: 209
-- Data for Name: revisions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.revisions VALUES (1, 'unknown', '2018-11-14 10:32:56.915');


--
-- TOC entry 2949 (class 0 OID 0)
-- Dependencies: 210
-- Name: pooled_sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.pooled_sequence_generator', 11, true);


--
-- TOC entry 2950 (class 0 OID 0)
-- Dependencies: 208
-- Name: revisions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.revisions_id_seq', 1, true);


--
-- TOC entry 2773 (class 2606 OID 50145)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id);


--
-- TOC entry 2775 (class 2606 OID 50150)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id);


--
-- TOC entry 2777 (class 2606 OID 50155)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2779 (class 2606 OID 50160)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2781 (class 2606 OID 50170)
-- Name: users uk_rcle35tk5t6py9hf7uow9qkcw; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT uk_rcle35tk5t6py9hf7uow9qkcw UNIQUE (id_profile);


--
-- TOC entry 2783 (class 2606 OID 50168)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2785 (class 2606 OID 50175)
-- Name: audit_profiles audit_profiles_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.audit_profiles
    ADD CONSTRAINT audit_profiles_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2787 (class 2606 OID 50180)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2789 (class 2606 OID 50185)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2791 (class 2606 OID 50190)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2793 (class 2606 OID 50198)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2795 (class 2606 OID 50206)
-- Name: revisions revisions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.revisions
    ADD CONSTRAINT revisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2799 (class 2606 OID 50224)
-- Name: users fk6ygxt7686dodh2vla65342ct4; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk6ygxt7686dodh2vla65342ct4 FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2800 (class 2606 OID 50229)
-- Name: users fk9wbwuppwb63kwaj2s6aaqi3th; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.users
    ADD CONSTRAINT fk9wbwuppwb63kwaj2s6aaqi3th FOREIGN KEY (id_profile) REFERENCES configuration.profiles(id);


--
-- TOC entry 2797 (class 2606 OID 50214)
-- Name: grants fkchpf69027hrvhv43xo5ijglg9; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fkchpf69027hrvhv43xo5ijglg9 FOREIGN KEY (id_group) REFERENCES configuration.groups(id);


--
-- TOC entry 2798 (class 2606 OID 50219)
-- Name: groups fkjofv20ia9udcgcbtkows5p05; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.groups
    ADD CONSTRAINT fkjofv20ia9udcgcbtkows5p05 FOREIGN KEY (id_parent) REFERENCES configuration.groups(id);


--
-- TOC entry 2796 (class 2606 OID 50209)
-- Name: grants fkojxnxlqxe5rkbrn1ebp14hkhf; Type: FK CONSTRAINT; Schema: configuration; Owner: -
--

ALTER TABLE ONLY configuration.grants
    ADD CONSTRAINT fkojxnxlqxe5rkbrn1ebp14hkhf FOREIGN KEY (id_authorization) REFERENCES configuration.authorizations(id);


--
-- TOC entry 2805 (class 2606 OID 50254)
-- Name: users fkfpdirvke1ki2dao9spagj22r6; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.users
    ADD CONSTRAINT fkfpdirvke1ki2dao9spagj22r6 FOREIGN KEY (rev) REFERENCES public.revisions(id);


--
-- TOC entry 2803 (class 2606 OID 50244)
-- Name: grants fkithgb8wg5uop4621f13dy5vjl; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.grants
    ADD CONSTRAINT fkithgb8wg5uop4621f13dy5vjl FOREIGN KEY (rev) REFERENCES public.revisions(id);


--
-- TOC entry 2801 (class 2606 OID 50234)
-- Name: audit_profiles fkmll2o1yy2wqt2i2o992psjyse; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.audit_profiles
    ADD CONSTRAINT fkmll2o1yy2wqt2i2o992psjyse FOREIGN KEY (rev) REFERENCES public.revisions(id);


--
-- TOC entry 2802 (class 2606 OID 50239)
-- Name: authorizations fko4ki48ki4pnixsk2fmb7n5kq3; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.authorizations
    ADD CONSTRAINT fko4ki48ki4pnixsk2fmb7n5kq3 FOREIGN KEY (rev) REFERENCES public.revisions(id);


--
-- TOC entry 2804 (class 2606 OID 50249)
-- Name: groups fkp3ytwkacs475avg13qd8rbfrs; Type: FK CONSTRAINT; Schema: configuration_audit; Owner: -
--

ALTER TABLE ONLY configuration_audit.groups
    ADD CONSTRAINT fkp3ytwkacs475avg13qd8rbfrs FOREIGN KEY (rev) REFERENCES public.revisions(id);


-- Completed on 2018-11-14 12:34:12 UTC

--
-- PostgreSQL database dump complete
--