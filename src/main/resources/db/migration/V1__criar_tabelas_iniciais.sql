-- Criação das tabelas para o ERP de Biblioteca

CREATE TABLE usuarios (
    id UUID NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    idade INT,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT check_role CHECK (role IN ('ADMIN', 'COMUM'))
);

CREATE TABLE livros (
    id UUID NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    genero VARCHAR(50),
    capa_foto VARCHAR(255),
    disponivel_locacao BOOLEAN NOT NULL DEFAULT TRUE,
    classificacao_etaria VARCHAR(50),
    estado_conservacao VARCHAR(50),
    doador_id UUID,
    sinopse TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_doador FOREIGN KEY (doador_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT check_genero CHECK (genero IN ('FICCAO', 'NAO_FICCAO', 'TERROR', 'ROMANCE', 'EDUCACAO', 'TECNICO'))
);

CREATE TABLE locacoes (
    id UUID NOT NULL,
    livro_id UUID NOT NULL,
    usuario_id UUID NOT NULL,
    data_locacao TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVA',
    PRIMARY KEY (id),
    CONSTRAINT fk_locacao_livro FOREIGN KEY (livro_id) REFERENCES livros(id) ON DELETE CASCADE,
    CONSTRAINT fk_locacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT check_status CHECK (status IN ('ATIVA', 'FINALIZADA', 'CANCELADA'))
);

-- Índices
CREATE INDEX idx_livro_titulo ON livros(titulo);
CREATE INDEX idx_livro_capa_foto ON livros(capa_foto);