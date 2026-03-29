

INSERT INTO usuarios (id, nome, email, senha, idade, role) VALUES
   (gen_random_uuid(), 'Admin Sistema', 'admin@biblioteca.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 35, 'ADMIN'),
   (gen_random_uuid(), 'Usuario teste', 'test@email.com', '$2a$10$ysAQmQgr6uc.yI2aJsUZyur0CDb2WcuUhZY5l/RCAmrGzPoPV3H.y', 18, 'ADMIN'),
   (gen_random_uuid(), 'Maria Silva', 'maria@email.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 28, 'COMUM'),
   (gen_random_uuid(), 'João Santos', 'joao@email.com', '$2a$10$YL3GZO3zHHOVw3YlL0.1/OVhp1oPY0zyY5.6RbiMqoX3re8xAXqxq', 42, 'COMUM');



INSERT INTO livros (id, titulo, autor, genero, capa_foto, disponivel_locacao, classificacao_etaria, estado_conservacao, sinopse) VALUES
    (gen_random_uuid(), 'A Guerra dos Tronos', 'George R. R. Martin', 'FICCAO', 'aaguerradostronos.jpg', true, 'DEZOITO_ANOS', 'BOM', 'Em um reino onde as estações duram anos, uma batalha épica pelo trono de ferro está prestes a começar. Intrigas políticas, segredos obscuros e uma antiga ameaça convergem nesta saga fantástica.'),
    (gen_random_uuid(), 'Harry Potter e a Ordem da Fênix', 'J.K. Rowling', 'FICCAO', 'aOrderDaFenix.jpg', false, 'DOZE_ANOS', 'OTIMO', 'Harry retorna para seu quinto ano em Hogwarts, enfrentando a negação do Ministério da Magia sobre o retorno de Voldemort e formando um grupo secreto para preparar os estudantes para o perigo iminente.'),
    (gen_random_uuid(), 'Clean Code', 'Robert C. Martin', 'TECNICO', 'cleancode.jpg', true, 'LIVRE', 'BOM', 'Um guia prático para produzir código limpo e manutenível. O autor apresenta princípios, padrões e práticas para criar software profissional de qualidade.'),
    (gen_random_uuid(), 'Eragon', 'Christopher Paolini', 'FICCAO', 'eragonPaolini.jpg', true, 'DOZE_ANOS', 'BOM', 'A jornada épica de um jovem fazendeiro que descobre um ovo de dragão misterioso, levando-o a uma aventura que mudará para sempre o destino de sua terra.'),
    (gen_random_uuid(), 'George Martin: O Começo', 'James Hibberd', 'NAO_FICCAO', 'georgemartinOcomeco.jpg', true, 'LIVRE', 'OTIMO', 'Uma biografia detalhada sobre a vida e carreira do autor de As Crônicas de Gelo e Fogo, revelando sua jornada desde os primeiros contos até se tornar um dos escritores mais influentes da fantasia moderna.'),
    (gen_random_uuid(), 'Harry Potter e a Câmara Secreta', 'J.K. Rowling', 'FICCAO', 'harrypotterEacamarasecreta.jpg', true, 'DOZE_ANOS', 'BOM', 'No segundo ano em Hogwarts, Harry enfrenta um mistério ainda mais perigoso quando alunos começam a ser petrificados e uma mensagem sinistra aparece nas paredes do castelo.'),
    (gen_random_uuid(), 'Matéria Escura', 'Blake Crouch', 'FICCAO', 'materiaescura.jpg', true, 'DEZESSEIS_ANOS', 'BOM', 'Um thriller científico que explora realidades alternativas quando um físico se vê em uma versão diferente de sua vida, questionando todas as suas escolhas e a própria natureza da realidade.'),
    (gen_random_uuid(), 'Padrões de Projeto', 'Erich Gamma', 'TECNICO', 'padroesdeprojeto.jpg', true, 'LIVRE', 'BOM', 'Uma referência essencial sobre padrões de design de software, apresentando soluções reutilizáveis para problemas comuns no desenvolvimento de software orientado a objetos.'),
    (gen_random_uuid(), 'teste', 'teste', 'TECNICO', '1118ehluz8ehlu.jpg', true, 'LIVRE', 'BOM', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
    (gen_random_uuid(), 'Refatoração', 'Martin Fowler', 'TECNICO', 'refatoracao.jpg', true, 'LIVRE', 'OTIMO', 'Um guia completo sobre como melhorar o design de código existente, apresentando técnicas sistemáticas para aprimorar a estrutura de software sem alterar seu comportamento externo.');