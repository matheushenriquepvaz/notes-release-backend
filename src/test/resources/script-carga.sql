create sequence HIBERNATE_SEQUENCE;

create table GRP_GRUPO
(
    GRP_ID   BIGINT
        primary key,
    GRP_DESC VARCHAR(255) not null,
    GRP_NOME VARCHAR(255) not null
        constraint UKOYLUBWCYO7SD5CANN7AR72R3M
            unique
);


create table PER_PERMISSAO
(
    PER_ID            BIGINT
        primary key,
    PER_IDENTIFICADOR VARCHAR(255) not null
        constraint UKDE76EK1ITC50MI1V07KBQ8DT1
            unique
);

create table GUP_GRUPO_PERMISSAO
(
    GRP_ID BIGINT not null,
    PER_ID BIGINT not null,
    constraint FKKVCM11ED7AGN468BSVW85MSPR
        foreign key (GRP_ID) references GRP_GRUPO (GRP_ID),
    constraint FKNT1V1JP0X0LMGGA1COFU376WY
        foreign key (PER_ID) references PER_PERMISSAO (PER_ID)
);

create table USR_USUARIO
(
    USR_ID BIGSERIAL NOT NULL PRIMARY KEY ,
    USR_ATIVO            BOOLEAN      not null,
    USR_BLOQUEADO        BOOLEAN      not null,
    USR_CPF              VARCHAR(255)
        constraint UK_NLH2LK898XQ5X9DBX2VRG0VV
            unique,
    USR_DT_CRIACAO       TIMESTAMP    not null,
    USR_DT_TERMO         TIMESTAMP,
    USR_EMAIL            VARCHAR(200) not null
        constraint UKQKWEW46H7KCHO84GK0HHWHTMO
            unique,
    USR_DT_FIM           TIMESTAMP,
    USR_HASH_TROCA_SENHA VARCHAR(255),
    USR_NOME             VARCHAR(200) not null,
    USR_APELIDO          VARCHAR(200),
    USR_RG               VARCHAR(255),
    USR_SENHA            VARCHAR(200) not null
);

create table GRU_GRUPO_USUARIO
(
    GRP_ID BIGINT not null,
    USR_ID BIGINT not null,
    constraint FKEJTR8YBPXJMGJBKMGYIPF72YY
        foreign key (USR_ID) references USR_USUARIO (USR_ID),
    constraint FKRO3HFAI2S9L9G04F5JEEKW6A6
        foreign key (GRP_ID) references GRP_GRUPO (GRP_ID)
);

create table USP_USUARIO_PERMISSAO
(
    USER_ID BIGINT not null,
    PER_ID  BIGINT not null,
    constraint FK884EI9IP3N3RN7D4PBYJ73VYG
        foreign key (USER_ID) references USR_USUARIO (USR_ID),
    constraint FKI3YYIYEFF2WHP6RD3GYOM7EWA
        foreign key (PER_ID) references PER_PERMISSAO (PER_ID)
);

create table END_ENDERECO
(
    END_ID BIGSERIAL NOT NULL PRIMARY KEY ,
    END_CEP_ZIP_CODE VARCHAR(255),
    END_COMPLEMENTO  VARCHAR(255),
    CIDADE_ID        BIGINT,
    ESTADO_ID        BIGINT,
    PAIS_ID          BIGINT,
    END_UF           VARCHAR(255)
);

create table TEL_TELEFONE
(
    TEL_ID BIGSERIAL NOT NULL PRIMARY KEY ,
    TEL_DDD          VARCHAR(3),
    TEL_DDI          VARCHAR(3),
    TEL_NUMERO       VARCHAR(255) not null,
    EMP_CNPJ         VARCHAR(255),
    USR_ID           BIGINT,
    TELEFONES_USR_ID BIGINT,
    EMP_ID           BIGINT,
    constraint FKAGC55NCY2HQ68SMRGMACO1O49
        foreign key (EMP_CNPJ) references EMP_EMPRESA (EMP_CNPJ),
    constraint FKAYWC6RRQIFBXXJ57HH0I5C4DN
        foreign key (EMP_ID) references EMP_EMPRESA (EMP_ID),
    constraint FKHXKT9JS2M8JLLHX15SHY6G6G8
        foreign key (TELEFONES_USR_ID) references USR_USUARIO (USR_ID),
    constraint FKK6RMD13NX5QQSP2V5G6S1XXAA
        foreign key (USR_ID) references USR_USUARIO (USR_ID)
);

INSERT INTO PUBLIC.GRP_GRUPO (GRP_ID, GRP_DESC, GRP_NOME) VALUES (1, 'Grupo de usuarios altorizados a logar em fase de teste', 'PERMISSIONADOS_LOG');

INSERT INTO PUBLIC.PER_PERMISSAO (PER_ID, PER_IDENTIFICADOR) VALUES (1, 'ADMIN');
INSERT INTO PUBLIC.PER_PERMISSAO (PER_ID, PER_IDENTIFICADOR) VALUES (3, 'USER');

insert into usr_usuario (usr_id, usr_email, usr_nome, usr_senha, usr_dt_criacao, usr_ativo, usr_bloqueado)
values (1, 'teste@teste.com', 'Matheus Vaz', '$2a$10$DDZ6hJac/54iCTUzQXf0GOgOXuyf6JNVgAP/tQ1XANTN8lFtVKzCe', current_timestamp, true, false);

