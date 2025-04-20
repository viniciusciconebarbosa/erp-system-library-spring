-- Inserindo usuários iniciais
INSERT INTO usuarios (id, nome, email, senha, idade, role) VALUES
(UUID_TO_BIN(UUID()), 'Admin Sistema', 'admin@biblioteca.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 35, 'ADMIN'),
(UUID_TO_BIN(UUID()), 'Maria Silva', 'maria@email.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 28, 'COMUM'),
(UUID_TO_BIN(UUID()), 'João Santos', 'joao@email.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 42, 'COMUM');

-- Inserindo livros iniciais
INSERT INTO livros (id, titulo, genero, capa_foto, disponivel_locacao, classificacao_etaria, estado_conservacao) VALUES
(UUID_TO_BIN(UUID()), 'Dom Casmurro', 'ROMANCE', 'dom-casmurro.jpg', true, 'LIVRE', 'BOM'),
(UUID_TO_BIN(UUID()), '1984', 'FICCAO', '1984.jpg', true, 'DEZOITO_ANOS', 'OTIMO'),
(UUID_TO_BIN(UUID()), 'O Senhor dos Anéis', 'FICCAO', 'senhor-dos-aneis.jpg', true, 'DOZE_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'Java: Como Programar', 'TECNICO', 'java-programar.jpg', true, 'LIVRE', 'NOVO'),
(UUID_TO_BIN(UUID()), 'Clean Code', 'TECNICO', 'clean-code.jpg', true, 'LIVRE', 'BOM'),
(UUID_TO_BIN(UUID()), 'O Chamado de Cthulhu', 'TERROR', 'cthulhu.jpg', true, 'DEZESSEIS_ANOS', 'REGULAR'),
(UUID_TO_BIN(UUID()), 'A Arte da Guerra', 'NAO_FICCAO', 'arte-guerra.jpg', true, 'QUATORZE_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'Pedagogia do Oprimido', 'EDUCACAO', 'pedagogia.jpg', true, 'DEZESSEIS_ANOS', 'BOM'),
(UUID_TO_BIN(UUID()), 'O Pequeno Príncipe', 'FICCAO', 'pequeno-principe.jpg', true, 'LIVRE', 'OTIMO'),
(UUID_TO_BIN(UUID()), 'Algoritmos: Teoria e Prática', 'TECNICO', 'algoritmos.jpg', true, 'LIVRE', 'BOM');