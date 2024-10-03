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

INSERT INTO public.devices (id, device_id, max_energy_consumption, user_id) VALUES (1, '2f5e3c6b-c268-4561-8398-ec1e473b9232', 30, 'a91f9c8d-7dac-4682-b5fa-43fd02058a12');
INSERT INTO public.devices (id, device_id, max_energy_consumption, user_id) VALUES (2, 'ad9b3195-9722-4dae-a447-155d8374dd0a', 35, 'a91f9c8d-7dac-4682-b5fa-43fd02058a12');


--
-- Data for Name: devices_consumption; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.devices_consumption (id, device_id, current_energy_consumption, "timestamp") VALUES (1, '2f5e3c6b-c268-4561-8398-ec1e473b9232', 10.2355385, 1701291290);
INSERT INTO public.devices_consumption (id, device_id, current_energy_consumption, "timestamp") VALUES (2, 'ad9b3195-9722-4dae-a447-155d8374dd0a', 10.2355385, 1701291310);
INSERT INTO public.devices_consumption (id, device_id, current_energy_consumption, "timestamp") VALUES (3, '2f5e3c6b-c268-4561-8398-ec1e473b9232', 36.41577, 1701291320);
INSERT INTO public.devices_consumption (id, device_id, current_energy_consumption, "timestamp") VALUES (4, 'ad9b3195-9722-4dae-a447-155d8374dd0a', 36.41577, 1701291340);
INSERT INTO public.devices_consumption (id, device_id, current_energy_consumption, "timestamp") VALUES (5, '2f5e3c6b-c268-4561-8398-ec1e473b9232', 51.228, 1701291350);


--
-- Name: devices_consumption_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.devices_consumption_seq', 51, true);


--
-- Name: devices_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.devices_seq', 51, true);


--
-- PostgreSQL database dump complete
--

