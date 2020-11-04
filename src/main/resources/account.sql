CREATE TABLE public.account (
	id uuid NULL,
	balance numeric NULL,
	CONSTRAINT account_pk PRIMARY KEY (id)
);

CREATE TABLE public."transaction" (
	id uuid NULL,
	execution_date_time timestamp NOT NULL,
	amount numeric NULL,
	account_ud uuid NULL,
	CONSTRAINT transaction_account_fk FOREIGN KEY (id) REFERENCES public.account(id)
);
