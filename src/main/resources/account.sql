CREATE TABLE public.account (
	id uuid NULL,
	initial_balance numeric NOT NULL,
	CONSTRAINT account_pk PRIMARY KEY (id)
);

CREATE TABLE public."transaction" (
	id uuid NULL,
	execution_date_time timestamp NOT NULL,
	amount numeric NOT NULL,
	account_ud uuid NOT NULL,
	details varchar NULL,
	CONSTRAINT transaction_account_fk FOREIGN KEY (id) REFERENCES public.account(id)
);
