-- Criação da tabela de reuniões
CREATE TABLE IF NOT EXISTS reuniao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_nome VARCHAR(255) NOT NULL,
    vendedor_nome VARCHAR(255) NOT NULL,
    data_reuniao VARCHAR(10) NOT NULL,
    duracao_minutos INT NOT NULL,
    transcricao CLOB,
    sentimento VARCHAR(20) NOT NULL,
    tempo_fala_vendedor INT NOT NULL,
    tempo_fala_cliente INT NOT NULL,
    produto_relacionado VARCHAR(50) NOT NULL
);

-- Carga inicial de dados para testes
INSERT INTO reuniao (cliente_nome, vendedor_nome, data_reuniao, duracao_minutos, transcricao, sentimento, tempo_fala_vendedor, tempo_fala_cliente, produto_relacionado)
VALUES (
    'Indústrias Metalflex Ltda',
    'Carlos Eduardo',
    '2026-09-10',
    75,
    'O cliente relatou estar muito insatisfeito com a lentidão e pensa em cancelar o contrato para ir para o concorrente. Precisamos resolver a conciliação fiscal e o backoffice com urgência.',
    'NEGATIVO',
    45,
    30,
    'PROTHEUS'
);

INSERT INTO reuniao (cliente_nome, vendedor_nome, data_reuniao, duracao_minutos, transcricao, sentimento, tempo_fala_vendedor, tempo_fala_cliente, produto_relacionado)
VALUES (
    'Agro Alimentos Sul S.A.',
    'Mariana Souza',
    '2026-09-11',
    40,
    'Conversamos sobre a gestão de suprimentos e gargalos na linha de produção da fábrica. Tivemos um problema pontual na entrega, mas o cliente está receptivo à cadeia logística.',
    'NEUTRO',
    20,
    20,
    'DATASUL'
);

INSERT INTO reuniao (cliente_nome, vendedor_nome, data_reuniao, duracao_minutos, transcricao, sentimento, tempo_fala_vendedor, tempo_fala_cliente, produto_relacionado)
VALUES (
    'Colégio & Faculdade Horizonte',
    'Lucas Pinheiro',
    '2026-09-12',
    30,
    'Alinhamento muito produtivo com a equipe de RH. O cliente elogiou a facilidade do cálculo de folha e pagamento dos colaboradores, sem evasão cadastral.',
    'POSITIVO',
    10,
    20,
    'RM'
);
