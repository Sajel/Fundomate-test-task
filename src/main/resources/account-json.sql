CREATE TABLE public.account (
	id uuid NULL,
	initial_balance numeric NOT NULL,
	transactions jsonb NOT NULL,
	CONSTRAINT account_pk PRIMARY KEY (id)
);