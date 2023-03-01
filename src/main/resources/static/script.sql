INSERT INTO public.administrator (secret_key) VALUES ('4eebed5d-2f6a-4b1b-ab1c-1b08530c1848');

INSERT INTO public.currency (name) VALUES ('RUB');
INSERT INTO public.currency (name) VALUES ('TON');
INSERT INTO public.currency (name) VALUES ('BTC');

INSERT INTO public.currency_amount (id, amount, currency_name) VALUES ('c88e8995-2fff-45b6-b24f-df496672419c', 650, 'RUB');
INSERT INTO public.currency_amount (id, amount, currency_name) VALUES ('7d3d16fa-4239-4436-a9f2-43a109559622', 1.0869565217391304, 'TON');

INSERT INTO public.wallet (id) VALUES ('a899aa00-0958-4183-aeb2-0dc3614ffe2e');
INSERT INTO public.wallet (id) VALUES ('eb06d8ae-2e06-482e-9888-5044a3627937');
INSERT INTO public.wallet (id) VALUES ('6a85a355-4532-4cb8-bec2-b2c1b8f0852a');
INSERT INTO public.wallet (id) VALUES ('a5280038-5ba1-415a-a4fd-e8f75f6927b1');

INSERT INTO public.person (secret_key, email, user_name, wallet_id) VALUES ('bb605a1d-e8d8-48ac-aef5-951387846754', 'test', 'ilya', 'a899aa00-0958-4183-aeb2-0dc3614ffe2e');
INSERT INTO public.person (secret_key, email, user_name, wallet_id) VALUES ('fd84fe1a-c71d-4e48-9f9b-0a568efaf458', 'vasyu_kolbasit@mail.ru', 'vasya_vezunchik', 'a5280038-5ba1-415a-a4fd-e8f75f6927b1');

INSERT INTO public.rate (id, value, currency_from_name, currency_to_name) VALUES ('e91dcea8-c1a2-4e3e-9fc4-66e3671d9e50', 0.000096, 'TON', 'BTC');
INSERT INTO public.rate (id, value, currency_from_name, currency_to_name) VALUES ('adaad7d3-34d3-407a-bae6-5cdfac408e20', 10416.666666666666, 'BTC', 'TON');
INSERT INTO public.rate (id, value, currency_from_name, currency_to_name) VALUES ('3c60af48-79d8-4c42-aece-3cb07a8bb9d0', 184, 'TON', 'RUB');
INSERT INTO public.rate (id, value, currency_from_name, currency_to_name) VALUES ('b41349b2-8cf5-4a42-bead-884d62ac9514', 0.005434782608695652, 'RUB', 'TON');

INSERT INTO public.wallet_currencies (wallet_id, currencies_id) VALUES ('a5280038-5ba1-415a-a4fd-e8f75f6927b1', 'c88e8995-2fff-45b6-b24f-df496672419c');
INSERT INTO public.wallet_currencies (wallet_id, currencies_id) VALUES ('a5280038-5ba1-415a-a4fd-e8f75f6927b1', '7d3d16fa-4239-4436-a9f2-43a109559622');

INSERT INTO public.transaction (id, date, currency_name, wallet_from_id, wallet_to_id) VALUES ('439c09be-3c6f-4744-aa15-8db6d91c4c2b', '2023-03-01', 'RUB', null, 'a5280038-5ba1-415a-a4fd-e8f75f6927b1');
INSERT INTO public.transaction (id, date, currency_name, wallet_from_id, wallet_to_id) VALUES ('f1f133cc-b84f-464c-a2b8-8a3e13c6fd79', '2023-03-01', 'RUB', 'a5280038-5ba1-415a-a4fd-e8f75f6927b1', null);
INSERT INTO public.transaction (id, date, currency_name, wallet_from_id, wallet_to_id) VALUES ('6f049469-c181-4b68-b66e-1aa29203c27b', '2023-03-01', 'RUB', 'a5280038-5ba1-415a-a4fd-e8f75f6927b1', null);
INSERT INTO public.transaction (id, date, currency_name, wallet_from_id, wallet_to_id) VALUES ('dc687926-fa38-4be5-b4d2-1d863284f69a', '2023-03-01', 'TON', null, 'a5280038-5ba1-415a-a4fd-e8f75f6927b1');
INSERT INTO public.transaction (id, date, currency_name, wallet_from_id, wallet_to_id) VALUES ('b9bc7deb-3ad9-42cd-b74e-b590f2a36de0', '2023-03-01', 'RUB', 'a5280038-5ba1-415a-a4fd-e8f75f6927b1', 'a5280038-5ba1-415a-a4fd-e8f75f6927b1');
