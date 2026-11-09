-- =============================================================
-- Migration: gestao-pedidos
-- Servico: Gestao de Pedidos - Vinheria Agnello
-- Execute este script no banco Oracle da faculdade
-- =============================================================

-- -------------------------------------------------------------
-- Tabela de clientes (contatos da vinheria)
-- -------------------------------------------------------------
CREATE TABLE tb_clientes (
    id          NUMBER          NOT NULL,
    nome        VARCHAR2(200)   NOT NULL,
    email       VARCHAR2(200)   NOT NULL,
    assunto     VARCHAR2(200),
    mensagem    VARCHAR2(500)   NOT NULL,
    data_envio  TIMESTAMP,
    CONSTRAINT pk_clientes PRIMARY KEY (id)
);

CREATE SEQUENCE seq_tb_clientes
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- -------------------------------------------------------------
-- Tabela de pedidos (vendas de vinho)
-- -------------------------------------------------------------
CREATE TABLE tb_pedidos (
    id                  NUMBER          NOT NULL,
    id_vinho            NUMBER          NOT NULL,
    id_cliente          NUMBER,
    canal_venda         VARCHAR2(50)    NOT NULL,
    regiao_entrega      VARCHAR2(50)    NOT NULL,
    quantidade          NUMBER          NOT NULL,
    preco_unitario      NUMBER(10, 2)   NOT NULL,
    desconto_aplicado   NUMBER,
    data_venda          TIMESTAMP       NOT NULL,
    temporada           VARCHAR2(50),
    avaliacao_cliente   NUMBER,
    venda_sucesso       NUMBER(1)       NOT NULL,
    CONSTRAINT pk_pedidos       PRIMARY KEY (id),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente)
        REFERENCES tb_clientes(id),
    CONSTRAINT ck_pedido_canal  CHECK (
        canal_venda IN ('LOJA FISICA', 'E-COMMERCE', 'WHATSAPP', 'TELEFONE', 'PARCEIRO')
    ),
    CONSTRAINT ck_pedido_regiao CHECK (
        regiao_entrega IN ('NORTE', 'SUL', 'SUDESTE', 'NORDESTE', 'CENTRO-OESTE')
    ),
    CONSTRAINT ck_pedido_qtd    CHECK (quantidade > 0),
    CONSTRAINT ck_pedido_preco  CHECK (preco_unitario > 0),
    CONSTRAINT ck_pedido_desc   CHECK (desconto_aplicado IS NULL OR (desconto_aplicado >= 0 AND desconto_aplicado <= 100)),
    CONSTRAINT ck_pedido_aval   CHECK (avaliacao_cliente IS NULL OR (avaliacao_cliente >= 1 AND avaliacao_cliente <= 5)),
    CONSTRAINT ck_pedido_sucesso CHECK (venda_sucesso IN (0, 1))
);

CREATE SEQUENCE seq_tb_pedidos
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- -------------------------------------------------------------
-- Dados de exemplo (opcional — remova se nao quiser)
-- -------------------------------------------------------------
INSERT INTO tb_clientes (id, nome, email, assunto, mensagem, data_envio)
VALUES (seq_tb_clientes.nextval, 'Ana Lima', 'ana@email.com',
        'Duvida sobre entrega', 'Gostaria de saber o prazo para o Sul.',
        TIMESTAMP '2026-11-01 10:00:00');

INSERT INTO tb_clientes (id, nome, email, assunto, mensagem, data_envio)
VALUES (seq_tb_clientes.nextval, 'Bruno Souza', 'bruno@email.com',
        'Harmonizacao', 'Qual vinho indicam para cordeiro?',
        TIMESTAMP '2026-11-02 14:30:00');

INSERT INTO tb_pedidos (id, id_vinho, id_cliente, canal_venda, regiao_entrega,
                        quantidade, preco_unitario, desconto_aplicado,
                        data_venda, temporada, avaliacao_cliente, venda_sucesso)
VALUES (seq_tb_pedidos.nextval, 1, 1, 'E-COMMERCE', 'SUDESTE',
        3, 129.28, 10,
        TIMESTAMP '2026-11-08 18:00:00', 'Natal', 5, 1);

INSERT INTO tb_pedidos (id, id_vinho, id_cliente, canal_venda, regiao_entrega,
                        quantidade, preco_unitario, desconto_aplicado,
                        data_venda, temporada, avaliacao_cliente, venda_sucesso)
VALUES (seq_tb_pedidos.nextval, 2, 2, 'WHATSAPP', 'SUL',
        1, 241.45, NULL,
        TIMESTAMP '2026-11-08 19:00:00', 'Inverno', 4, 1);

COMMIT;
