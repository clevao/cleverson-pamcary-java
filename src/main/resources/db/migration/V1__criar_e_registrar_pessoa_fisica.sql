CREATE TABLE PESSO_FISICA(
    CODIGO      SMALLINT       NOT NULL,
    NOME    	VARCHAR(60)      NOT NULL,
    CPF      VARCHAR(11)    NOT NULL,
    DATA_NASCIMENTO		TIMESTAMP,
    PRIMARY KEY(CODIGO)
);

CREATE SEQUENCE sequence_pessoa_fisica
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    NO CYCLE
    NO CACHE
;

insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'João da Silva', '54393014022', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Fernando Cardoso', '89702717086', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Gustavo Santos', '87938175099', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Guilherme Bezerra', '19792150064', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Antonio Silveira', '36505158065', '2018-12-28 02:00:21');

CREATE TABLE TELEFONE(
    CODIGO      SMALLINT       NOT NULL,
    CODIGO_PESSOA    	SMALLINT      NOT NULL,
    TELEFONE      VARCHAR(15)    NOT NULL,
    PRIMARY KEY(CODIGO)
);

CREATE SEQUENCE sequence_telefone
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    NO CYCLE
    NO CACHE
;

insert into TELEFONE values (sequence_telefone.NEXTVAL, '1', '15 30123310');
insert into TELEFONE values (sequence_telefone.NEXTVAL, '1', '15 88456-6598');
insert into TELEFONE values (sequence_telefone.NEXTVAL, '2', '15 30129999');

