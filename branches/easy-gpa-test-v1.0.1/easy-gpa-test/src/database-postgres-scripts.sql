
CREATE TABLE tb_device
(
  id serial NOT NULL,
  nome character varying NOT NULL,
  sistema_operacional character varying,
  CONSTRAINT tb_device_pkey PRIMARY KEY (id)
);

CREATE TABLE tb_usuario
(
  id serial NOT NULL,
  nome_completo character varying(10485760) NOT NULL,
  nome_usuario character varying(10485760) NOT NULL,
  senha character varying(10485760) NOT NULL,
  id_device integer,
  CONSTRAINT tb_usuario_pkey PRIMARY KEY (id)
);

CREATE TABLE tb_veiculo
(
  id serial NOT NULL,
  nome character varying,
  marca character varying,
  CONSTRAINT tb_veiculo_pkey PRIMARY KEY (id)
);

CREATE TABLE tb_motorista
(
  id serial NOT NULL,
  nome character varying,
  idade integer,
  telefone character varying,
  CONSTRAINT tb_motorista_pkey PRIMARY KEY (id)
);

CREATE TABLE tb_historico
(
  id serial NOT NULL,
  latitude character varying(30) NOT NULL,
  longitude character varying(30) NOT NULL,
  data date NOT NULL,
  hora time with time zone NOT NULL,
  id_usuario integer NOT NULL,
  altitude double precision,
  CONSTRAINT tb_historico_pkey PRIMARY KEY (id),
  CONSTRAINT tb_historico_id_usuario_fkey FOREIGN KEY (id_usuario)
      REFERENCES tb_usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tb_entregas
(
  id serial NOT NULL,
  id_motorista integer,
  id_veiculo integer,
  origem character varying,
  destino character varying,
  CONSTRAINT tb_entregas_pkey PRIMARY KEY (id),
  CONSTRAINT tb_entregas_id_motorista_fkey FOREIGN KEY (id_motorista)
      REFERENCES tb_motorista (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT tb_entregas_id_veiculo_fkey FOREIGN KEY (id_veiculo)
      REFERENCES tb_veiculo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
