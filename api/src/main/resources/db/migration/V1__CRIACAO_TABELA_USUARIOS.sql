CREATE SCHEMA securety;
CREATE TABLE securety.users(
    id serial,
    login VARCHAR NOT NULL UNIQUE,
    PASSWORD VARCHAR NOT NULL,
    nome VARCHAR NOT NULL,
    role VARCHAR

)