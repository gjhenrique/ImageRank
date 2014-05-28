CREATE TABLE classes
(
  id serial NOT NULL,
  nome character varying(255) NOT NULL,
  CONSTRAINT classes_pkey1 PRIMARY KEY (id )
);


CREATE TABLE experimentos
(
  id bigserial NOT NULL,

  nivel integer NOT NULL,
  coeficiente integer[] NOT NULL,
  nome_imagem character varying(255),
  classe_id integer NOT NULL,
  imagem bytea NOT NULL,
  CONSTRAINT experimentos_pkey PRIMARY KEY (id ),
  CONSTRAINT experimentos_classe_id_fkey FOREIGN KEY (classe_id)
      REFERENCES classes (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE RESTRICT
);

