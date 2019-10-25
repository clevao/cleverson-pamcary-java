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

insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'João da Silva', '83152513097', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Fernando Cardoso', '38920870080', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Gustavo Santos', '85158954046', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Guilherme Bezerra', '14356771028', '2018-12-28 02:00:21');
insert into PESSO_FISICA values (sequence_pessoa_fisica.NEXTVAL, 'Antonio Silveira', '62113786028', '2018-12-28 02:00:21');
