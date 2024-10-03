--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, email, name, password, role) VALUES ('b481d080-695c-4978-b56a-cfa41ac843bf', 'turcu.cezar@yahoo.com', 'Turcu Cezar', '$2a$10$e.yiAuQFEQRcbUk4/pn54u8tU/Qi5hoklFsskvbEFHDQO23DBJDxi', 'ADMIN');
INSERT INTO public.users (id, email, name, password, role) VALUES ('a91f9c8d-7dac-4682-b5fa-43fd02058a12', 'iuliu.pohu@yahoo.com', 'Iuliu Pohu', '$2a$10$nOxudF9wEcxxZIFq.9J7TOHxUT6vuNJEsKhsewcY8u43lnbUJEGVK', 'CLIENT');


--
-- PostgreSQL database dump complete
--

