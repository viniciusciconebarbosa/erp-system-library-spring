-- Inserindo usuários iniciais
INSERT INTO usuarios (id, nome, email, senha, idade, role) VALUES
(UUID_TO_BIN(UUID()), 'Admin Sistema', 'admin@biblioteca.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 35, 'ADMIN'),
(UUID_TO_BIN(UUID()), 'Maria Silva', 'maria@email.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 28, 'COMUM'),
(UUID_TO_BIN(UUID()), 'João Santos', 'joao@email.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 42, 'COMUM');

-- Inserindo livros iniciais com caminhos das capas
INSERT INTO livros (id, titulo, autor, genero, capa_foto, disponivel_locacao, classificacao_etaria, estado_conservacao) VALUES
(UUID_TO_BIN(UUID()), 'A Guerra dos Tronos', 'George R. R. Martin', 'FICCAO', '/uploads/capas/aguerradostronos.jpg', true, 'DEZOITO_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'Harry Potter e a Ordem da Fênix', 'J.K. Rowling', 'FICCAO', '/uploads/capas/aOrderDaFenix.jpg', true, 'DOZE_ANOS', 'OTIMO'),
(UUID_TO_BIN(UUID()), 'Clean Code', 'Robert C. Martin', 'TECNICO', '/uploads/capas/cleancode.jpg', true, 'LIVRE', 'BOM'),
(UUID_TO_BIN(UUID()), 'Eragon', 'Christopher Paolini', 'FICCAO', '/uploads/capas/eragonPaolini.jpg', true, 'DOZE_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'George Martin: O Começo', 'James Hibberd', 'NAO_FICCAO', '/uploads/capas/georgemartinOcomeco.jpg', true, 'LIVRE', 'OTIMO'),
(UUID_TO_BIN(UUID()), 'Harry Potter e a Câmara Secreta', 'J.K. Rowling', 'FICCAO', '/uploads/capas/harrypotterEacamarasecreta.jpg', true, 'DOZE_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'Matéria Escura', 'Blake Crouch', 'FICCAO', '/uploads/capas/materiaescura.jpg', true, 'DEZESSEIS_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'Padrões de Projeto', 'Erich Gamma', 'TECNICO', '/uploads/capas/padroesdeprojeto.jpg', true, 'LIVRE', 'BOM'),
(UUID_TO_BIN(UUID()), 'Refatoração', 'Martin Fowler', 'TECNICO', '/uploads/capas/refatoracao.jpg', true, 'LIVRE', 'OTIMO');
