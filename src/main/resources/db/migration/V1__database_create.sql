--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.3 (Ubuntu 10.3-1.pgdg16.04+1)

-- Started on 2018-04-13 15:54:37 -03

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
-- TOC entry 9 (class 2615 OID 74004)
-- Name: audit; Type: SCHEMA; Schema: -; Owner: sa_library
--

CREATE SCHEMA audit;


ALTER SCHEMA audit OWNER TO sa_library;

--
-- TOC entry 4 (class 2615 OID 74005)
-- Name: security; Type: SCHEMA; Schema: -; Owner: sa_library
--

CREATE SCHEMA security;


ALTER SCHEMA security OWNER TO sa_library;

--
-- TOC entry 5 (class 2615 OID 74006)
-- Name: security_audit; Type: SCHEMA; Schema: -; Owner: sa_library
--

CREATE SCHEMA security_audit;


ALTER SCHEMA security_audit OWNER TO sa_library;

--
-- TOC entry 1 (class 3079 OID 12980)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 200 (class 1259 OID 74009)
-- Name: revisions; Type: TABLE; Schema: audit; Owner: sa_library
--

CREATE TABLE audit.revisions (
    id bigint NOT NULL,
    created_by character varying(45) NOT NULL,
    created_on timestamp without time zone NOT NULL,
    "timestamp" bigint NOT NULL
);


ALTER TABLE audit.revisions OWNER TO sa_library;

--
-- TOC entry 199 (class 1259 OID 74007)
-- Name: revisions_id_seq; Type: SEQUENCE; Schema: audit; Owner: sa_library
--

CREATE SEQUENCE audit.revisions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE audit.revisions_id_seq OWNER TO sa_library;

--
-- TOC entry 2963 (class 0 OID 0)
-- Dependencies: 199
-- Name: revisions_id_seq; Type: SEQUENCE OWNED BY; Schema: audit; Owner: sa_library
--

ALTER SEQUENCE audit.revisions_id_seq OWNED BY audit.revisions.id;


--
-- TOC entry 202 (class 1259 OID 74017)
-- Name: authorizations; Type: TABLE; Schema: security; Owner: sa_library
--

CREATE TABLE security.authorizations (
    id bigint NOT NULL,
    functionality character varying(90) NOT NULL,
    permission character varying(90) NOT NULL
);


ALTER TABLE security.authorizations OWNER TO sa_library;

--
-- TOC entry 201 (class 1259 OID 74015)
-- Name: authorizations_id_seq; Type: SEQUENCE; Schema: security; Owner: sa_library
--

CREATE SEQUENCE security.authorizations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE security.authorizations_id_seq OWNER TO sa_library;

--
-- TOC entry 2964 (class 0 OID 0)
-- Dependencies: 201
-- Name: authorizations_id_seq; Type: SEQUENCE OWNED BY; Schema: security; Owner: sa_library
--

ALTER SEQUENCE security.authorizations_id_seq OWNED BY security.authorizations.id;


--
-- TOC entry 204 (class 1259 OID 74025)
-- Name: grants; Type: TABLE; Schema: security; Owner: sa_library
--

CREATE TABLE security.grants (
    id bigint NOT NULL,
    id_authorization bigint NOT NULL,
    id_group bigint NOT NULL
);


ALTER TABLE security.grants OWNER TO sa_library;

--
-- TOC entry 203 (class 1259 OID 74023)
-- Name: grants_id_seq; Type: SEQUENCE; Schema: security; Owner: sa_library
--

CREATE SEQUENCE security.grants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE security.grants_id_seq OWNER TO sa_library;

--
-- TOC entry 2965 (class 0 OID 0)
-- Dependencies: 203
-- Name: grants_id_seq; Type: SEQUENCE OWNED BY; Schema: security; Owner: sa_library
--

ALTER SEQUENCE security.grants_id_seq OWNED BY security.grants.id;


--
-- TOC entry 206 (class 1259 OID 74033)
-- Name: groups; Type: TABLE; Schema: security; Owner: sa_library
--

CREATE TABLE security.groups (
    id bigint NOT NULL,
    blocked boolean NOT NULL,
    name character varying(45) NOT NULL,
    id_parent bigint
);


ALTER TABLE security.groups OWNER TO sa_library;

--
-- TOC entry 205 (class 1259 OID 74031)
-- Name: groups_id_seq; Type: SEQUENCE; Schema: security; Owner: sa_library
--

CREATE SEQUENCE security.groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE security.groups_id_seq OWNER TO sa_library;

--
-- TOC entry 2966 (class 0 OID 0)
-- Dependencies: 205
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: security; Owner: sa_library
--

ALTER SEQUENCE security.groups_id_seq OWNED BY security.groups.id;


--
-- TOC entry 208 (class 1259 OID 74041)
-- Name: profiles; Type: TABLE; Schema: security; Owner: sa_library
--

CREATE TABLE security.profiles (
    id bigint NOT NULL,
    color character varying(255) NOT NULL
);


ALTER TABLE security.profiles OWNER TO sa_library;

--
-- TOC entry 207 (class 1259 OID 74039)
-- Name: profiles_id_seq; Type: SEQUENCE; Schema: security; Owner: sa_library
--

CREATE SEQUENCE security.profiles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE security.profiles_id_seq OWNER TO sa_library;

--
-- TOC entry 2967 (class 0 OID 0)
-- Dependencies: 207
-- Name: profiles_id_seq; Type: SEQUENCE OWNED BY; Schema: security; Owner: sa_library
--

ALTER SEQUENCE security.profiles_id_seq OWNED BY security.profiles.id;


--
-- TOC entry 210 (class 1259 OID 74049)
-- Name: users; Type: TABLE; Schema: security; Owner: sa_library
--

CREATE TABLE security.users (
    id bigint NOT NULL,
    blocked boolean NOT NULL,
    email character varying(90) NOT NULL,
    name character varying(90) NOT NULL,
    password character varying(60),
    store_type character varying(255) NOT NULL,
    username character varying(20) NOT NULL,
    id_group bigint NOT NULL
);


ALTER TABLE security.users OWNER TO sa_library;

--
-- TOC entry 209 (class 1259 OID 74047)
-- Name: users_id_seq; Type: SEQUENCE; Schema: security; Owner: sa_library
--

CREATE SEQUENCE security.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE security.users_id_seq OWNER TO sa_library;

--
-- TOC entry 2968 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: security; Owner: sa_library
--

ALTER SEQUENCE security.users_id_seq OWNED BY security.users.id;


--
-- TOC entry 211 (class 1259 OID 74058)
-- Name: authorizations; Type: TABLE; Schema: security_audit; Owner: sa_library
--

CREATE TABLE security_audit.authorizations (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    functionality character varying(90),
    permission character varying(90)
);


ALTER TABLE security_audit.authorizations OWNER TO sa_library;

--
-- TOC entry 212 (class 1259 OID 74063)
-- Name: grants; Type: TABLE; Schema: security_audit; Owner: sa_library
--

CREATE TABLE security_audit.grants (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    id_authorization bigint,
    id_group bigint
);


ALTER TABLE security_audit.grants OWNER TO sa_library;

--
-- TOC entry 213 (class 1259 OID 74068)
-- Name: groups; Type: TABLE; Schema: security_audit; Owner: sa_library
--

CREATE TABLE security_audit.groups (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    blocked boolean,
    name character varying(45),
    id_parent bigint
);


ALTER TABLE security_audit.groups OWNER TO sa_library;

--
-- TOC entry 214 (class 1259 OID 74073)
-- Name: profiles; Type: TABLE; Schema: security_audit; Owner: sa_library
--

CREATE TABLE security_audit.profiles (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    color character varying(255)
);


ALTER TABLE security_audit.profiles OWNER TO sa_library;

--
-- TOC entry 215 (class 1259 OID 74078)
-- Name: users; Type: TABLE; Schema: security_audit; Owner: sa_library
--

CREATE TABLE security_audit.users (
    id bigint NOT NULL,
    rev bigint NOT NULL,
    revtype smallint,
    blocked boolean,
    email character varying(90),
    name character varying(90),
    password character varying(60),
    store_type character varying(255),
    username character varying(20),
    id_group bigint
);


ALTER TABLE security_audit.users OWNER TO sa_library;

--
-- TOC entry 2780 (class 2604 OID 74012)
-- Name: revisions id; Type: DEFAULT; Schema: audit; Owner: sa_library
--

ALTER TABLE ONLY audit.revisions ALTER COLUMN id SET DEFAULT nextval('audit.revisions_id_seq'::regclass);


--
-- TOC entry 2781 (class 2604 OID 74020)
-- Name: authorizations id; Type: DEFAULT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.authorizations ALTER COLUMN id SET DEFAULT nextval('security.authorizations_id_seq'::regclass);


--
-- TOC entry 2782 (class 2604 OID 74028)
-- Name: grants id; Type: DEFAULT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.grants ALTER COLUMN id SET DEFAULT nextval('security.grants_id_seq'::regclass);


--
-- TOC entry 2783 (class 2604 OID 74036)
-- Name: groups id; Type: DEFAULT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.groups ALTER COLUMN id SET DEFAULT nextval('security.groups_id_seq'::regclass);


--
-- TOC entry 2784 (class 2604 OID 74044)
-- Name: profiles id; Type: DEFAULT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.profiles ALTER COLUMN id SET DEFAULT nextval('security.profiles_id_seq'::regclass);


--
-- TOC entry 2785 (class 2604 OID 74052)
-- Name: users id; Type: DEFAULT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.users ALTER COLUMN id SET DEFAULT nextval('security.users_id_seq'::regclass);


--
-- TOC entry 2939 (class 0 OID 74009)
-- Dependencies: 200
-- Data for Name: revisions; Type: TABLE DATA; Schema: audit; Owner: sa_library
--

INSERT INTO audit.revisions VALUES (1, 'unknow', '2018-04-13 14:54:36.669', 1523642076669);


--
-- TOC entry 2941 (class 0 OID 74017)
-- Dependencies: 202
-- Data for Name: authorizations; Type: TABLE DATA; Schema: security; Owner: sa_library
--

INSERT INTO security.authorizations VALUES (1, 'user', 'insert');
INSERT INTO security.authorizations VALUES (2, 'user', 'update');
INSERT INTO security.authorizations VALUES (3, 'user', 'delete');
INSERT INTO security.authorizations VALUES (4, 'user', 'detail');
INSERT INTO security.authorizations VALUES (5, 'user', 'access');
INSERT INTO security.authorizations VALUES (6, 'group', 'insert');
INSERT INTO security.authorizations VALUES (7, 'group', 'update');
INSERT INTO security.authorizations VALUES (8, 'group', 'delete');
INSERT INTO security.authorizations VALUES (9, 'group', 'detail');
INSERT INTO security.authorizations VALUES (10, 'group', 'access');


--
-- TOC entry 2943 (class 0 OID 74025)
-- Dependencies: 204
-- Data for Name: grants; Type: TABLE DATA; Schema: security; Owner: sa_library
--

INSERT INTO security.grants VALUES (1, 1, 1);
INSERT INTO security.grants VALUES (2, 2, 1);
INSERT INTO security.grants VALUES (3, 3, 1);
INSERT INTO security.grants VALUES (4, 4, 1);
INSERT INTO security.grants VALUES (5, 5, 1);
INSERT INTO security.grants VALUES (6, 6, 1);
INSERT INTO security.grants VALUES (7, 7, 1);
INSERT INTO security.grants VALUES (8, 8, 1);
INSERT INTO security.grants VALUES (9, 9, 1);
INSERT INTO security.grants VALUES (10, 10, 1);


--
-- TOC entry 2945 (class 0 OID 74033)
-- Dependencies: 206
-- Data for Name: groups; Type: TABLE DATA; Schema: security; Owner: sa_library
--

INSERT INTO security.groups VALUES (1, false, 'Administradores', NULL);


--
-- TOC entry 2947 (class 0 OID 74041)
-- Dependencies: 208
-- Data for Name: profiles; Type: TABLE DATA; Schema: security; Owner: sa_library
--



--
-- TOC entry 2949 (class 0 OID 74049)
-- Dependencies: 210
-- Data for Name: users; Type: TABLE DATA; Schema: security; Owner: sa_library
--

INSERT INTO security.users VALUES (1, false, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$XieoqJJqcle8gIoJchd4bunheOq07RC/f2Ak50ynZJlXbbJ4G95ce', 'LOCAL', 'admin', 1);


--
-- TOC entry 2950 (class 0 OID 74058)
-- Dependencies: 211
-- Data for Name: authorizations; Type: TABLE DATA; Schema: security_audit; Owner: sa_library
--

INSERT INTO security_audit.authorizations VALUES (1, 1, 0, 'user', 'insert');
INSERT INTO security_audit.authorizations VALUES (2, 1, 0, 'user', 'update');
INSERT INTO security_audit.authorizations VALUES (3, 1, 0, 'user', 'delete');
INSERT INTO security_audit.authorizations VALUES (4, 1, 0, 'user', 'detail');
INSERT INTO security_audit.authorizations VALUES (5, 1, 0, 'user', 'access');
INSERT INTO security_audit.authorizations VALUES (6, 1, 0, 'group', 'insert');
INSERT INTO security_audit.authorizations VALUES (7, 1, 0, 'group', 'update');
INSERT INTO security_audit.authorizations VALUES (8, 1, 0, 'group', 'delete');
INSERT INTO security_audit.authorizations VALUES (9, 1, 0, 'group', 'detail');
INSERT INTO security_audit.authorizations VALUES (10, 1, 0, 'group', 'access');


--
-- TOC entry 2951 (class 0 OID 74063)
-- Dependencies: 212
-- Data for Name: grants; Type: TABLE DATA; Schema: security_audit; Owner: sa_library
--

INSERT INTO security_audit.grants VALUES (1, 1, 0, 1, 1);
INSERT INTO security_audit.grants VALUES (2, 1, 0, 2, 1);
INSERT INTO security_audit.grants VALUES (3, 1, 0, 3, 1);
INSERT INTO security_audit.grants VALUES (4, 1, 0, 4, 1);
INSERT INTO security_audit.grants VALUES (5, 1, 0, 5, 1);
INSERT INTO security_audit.grants VALUES (6, 1, 0, 6, 1);
INSERT INTO security_audit.grants VALUES (7, 1, 0, 7, 1);
INSERT INTO security_audit.grants VALUES (8, 1, 0, 8, 1);
INSERT INTO security_audit.grants VALUES (9, 1, 0, 9, 1);
INSERT INTO security_audit.grants VALUES (10, 1, 0, 10, 1);


--
-- TOC entry 2952 (class 0 OID 74068)
-- Dependencies: 213
-- Data for Name: groups; Type: TABLE DATA; Schema: security_audit; Owner: sa_library
--

INSERT INTO security_audit.groups VALUES (1, 1, 0, false, 'Administradores', NULL);


--
-- TOC entry 2953 (class 0 OID 74073)
-- Dependencies: 214
-- Data for Name: profiles; Type: TABLE DATA; Schema: security_audit; Owner: sa_library
--



--
-- TOC entry 2954 (class 0 OID 74078)
-- Dependencies: 215
-- Data for Name: users; Type: TABLE DATA; Schema: security_audit; Owner: sa_library
--

INSERT INTO security_audit.users VALUES (1, 1, 0, false, 'contato@arthurgregorio.eti.br', 'Administrador', '$2a$10$XieoqJJqcle8gIoJchd4bunheOq07RC/f2Ak50ynZJlXbbJ4G95ce', 'LOCAL', 'admin', 1);


--
-- TOC entry 2969 (class 0 OID 0)
-- Dependencies: 199
-- Name: revisions_id_seq; Type: SEQUENCE SET; Schema: audit; Owner: sa_library
--

SELECT pg_catalog.setval('audit.revisions_id_seq', 1, true);


--
-- TOC entry 2970 (class 0 OID 0)
-- Dependencies: 201
-- Name: authorizations_id_seq; Type: SEQUENCE SET; Schema: security; Owner: sa_library
--

SELECT pg_catalog.setval('security.authorizations_id_seq', 10, true);


--
-- TOC entry 2971 (class 0 OID 0)
-- Dependencies: 203
-- Name: grants_id_seq; Type: SEQUENCE SET; Schema: security; Owner: sa_library
--

SELECT pg_catalog.setval('security.grants_id_seq', 10, true);


--
-- TOC entry 2972 (class 0 OID 0)
-- Dependencies: 205
-- Name: groups_id_seq; Type: SEQUENCE SET; Schema: security; Owner: sa_library
--

SELECT pg_catalog.setval('security.groups_id_seq', 1, true);


--
-- TOC entry 2973 (class 0 OID 0)
-- Dependencies: 207
-- Name: profiles_id_seq; Type: SEQUENCE SET; Schema: security; Owner: sa_library
--

SELECT pg_catalog.setval('security.profiles_id_seq', 1, false);


--
-- TOC entry 2974 (class 0 OID 0)
-- Dependencies: 209
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: security; Owner: sa_library
--

SELECT pg_catalog.setval('security.users_id_seq', 1, true);


--
-- TOC entry 2787 (class 2606 OID 74014)
-- Name: revisions revisions_pkey; Type: CONSTRAINT; Schema: audit; Owner: sa_library
--

ALTER TABLE ONLY audit.revisions
    ADD CONSTRAINT revisions_pkey PRIMARY KEY (id);


--
-- TOC entry 2789 (class 2606 OID 74022)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id);


--
-- TOC entry 2791 (class 2606 OID 74030)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id);


--
-- TOC entry 2793 (class 2606 OID 74038)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- TOC entry 2795 (class 2606 OID 74046)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2797 (class 2606 OID 74057)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2799 (class 2606 OID 74062)
-- Name: authorizations authorizations_pkey; Type: CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.authorizations
    ADD CONSTRAINT authorizations_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2801 (class 2606 OID 74067)
-- Name: grants grants_pkey; Type: CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.grants
    ADD CONSTRAINT grants_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2803 (class 2606 OID 74072)
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2805 (class 2606 OID 74077)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2807 (class 2606 OID 74085)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id, rev);


--
-- TOC entry 2811 (class 2606 OID 74101)
-- Name: users fk6ygxt7686dodh2vla65342ct4; Type: FK CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.users
    ADD CONSTRAINT fk6ygxt7686dodh2vla65342ct4 FOREIGN KEY (id_group) REFERENCES security.groups(id);


--
-- TOC entry 2809 (class 2606 OID 74091)
-- Name: grants fkchpf69027hrvhv43xo5ijglg9; Type: FK CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.grants
    ADD CONSTRAINT fkchpf69027hrvhv43xo5ijglg9 FOREIGN KEY (id_group) REFERENCES security.groups(id);


--
-- TOC entry 2810 (class 2606 OID 74096)
-- Name: groups fkjofv20ia9udcgcbtkows5p05; Type: FK CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.groups
    ADD CONSTRAINT fkjofv20ia9udcgcbtkows5p05 FOREIGN KEY (id_parent) REFERENCES security.groups(id);


--
-- TOC entry 2808 (class 2606 OID 74086)
-- Name: grants fkojxnxlqxe5rkbrn1ebp14hkhf; Type: FK CONSTRAINT; Schema: security; Owner: sa_library
--

ALTER TABLE ONLY security.grants
    ADD CONSTRAINT fkojxnxlqxe5rkbrn1ebp14hkhf FOREIGN KEY (id_authorization) REFERENCES security.authorizations(id);


--
-- TOC entry 2816 (class 2606 OID 74126)
-- Name: users fkfpdirvke1ki2dao9spagj22r6; Type: FK CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.users
    ADD CONSTRAINT fkfpdirvke1ki2dao9spagj22r6 FOREIGN KEY (rev) REFERENCES audit.revisions(id);


--
-- TOC entry 2813 (class 2606 OID 74111)
-- Name: grants fkithgb8wg5uop4621f13dy5vjl; Type: FK CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.grants
    ADD CONSTRAINT fkithgb8wg5uop4621f13dy5vjl FOREIGN KEY (rev) REFERENCES audit.revisions(id);


--
-- TOC entry 2812 (class 2606 OID 74106)
-- Name: authorizations fko4ki48ki4pnixsk2fmb7n5kq3; Type: FK CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.authorizations
    ADD CONSTRAINT fko4ki48ki4pnixsk2fmb7n5kq3 FOREIGN KEY (rev) REFERENCES audit.revisions(id);


--
-- TOC entry 2814 (class 2606 OID 74116)
-- Name: groups fkp3ytwkacs475avg13qd8rbfrs; Type: FK CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.groups
    ADD CONSTRAINT fkp3ytwkacs475avg13qd8rbfrs FOREIGN KEY (rev) REFERENCES audit.revisions(id);


--
-- TOC entry 2815 (class 2606 OID 74121)
-- Name: profiles fkpffra0qdqfg0fuwjwldg20fa9; Type: FK CONSTRAINT; Schema: security_audit; Owner: sa_library
--

ALTER TABLE ONLY security_audit.profiles
    ADD CONSTRAINT fkpffra0qdqfg0fuwjwldg20fa9 FOREIGN KEY (rev) REFERENCES audit.revisions(id);


-- Completed on 2018-04-13 15:54:41 -03

--
-- PostgreSQL database dump complete
--

