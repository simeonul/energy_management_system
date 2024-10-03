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
-- Data for Name: devices; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.devices (id, address, description, max_energy_consumption, user_id) VALUES ('2f5e3c6b-c268-4561-8398-ec1e473b9232', 'Str. Bistritei Nr.3A', 'Frigder Samsung', 30, 'a91f9c8d-7dac-4682-b5fa-43fd02058a12');
INSERT INTO public.devices (id, address, description, max_energy_consumption, user_id) VALUES ('ad9b3195-9722-4dae-a447-155d8374dd0a', 'Str. Unirii nr.77', 'Televizor LG', 35, 'a91f9c8d-7dac-4682-b5fa-43fd02058a12');


--
-- PostgreSQL database dump complete
--

