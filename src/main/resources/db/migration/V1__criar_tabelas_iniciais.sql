-- Criação das tabelas para o ERP de Biblioteca

CREATE TABLE usuarios (
    id BINARY(16) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    idade INT,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT check_role CHECK (role IN ('ADMIN', 'COMUM'))
);

CREATE TABLE livros (
    id BINARY(16) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    genero VARCHAR(50),
    capa_foto VARCHAR(255),
    disponivel_locacao BOOLEAN NOT NULL DEFAULT TRUE,
    classificacao_etaria VARCHAR(50),
    estado_conservacao VARCHAR(50),
    doador_id BINARY(16),
    sinopse TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_doador FOREIGN KEY (doador_id) REFERENCES usuarios(id),
    CONSTRAINT check_genero CHECK (genero IN ('FICCAO', 'NAO_FICCAO', 'TERROR', 'ROMANCE', 'EDUCACAO', 'TECNICO'))
);

CREATE TABLE locacoes (
    id BINARY(16) NOT NULL,
    livro_id BINARY(16) NOT NULL,
    usuario_id BINARY(16) NOT NULL,
    data_locacao DATETIME NOT NULL,
    data_devolucao DATETIME,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVA',
    PRIMARY KEY (id),
    CONSTRAINT fk_locacao_livro FOREIGN KEY (livro_id) REFERENCES livros(id),
    CONSTRAINT fk_locacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT check_status CHECK (status IN ('ATIVA', 'FINALIZADA', 'CANCELADA'))
);

CREATE INDEX idx_livro_titulo ON livros(titulo);
CREATE FULLTEXT INDEX idx_livro_capa_foto ON livros(capa_foto);