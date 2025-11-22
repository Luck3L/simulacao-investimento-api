INSERT INTO dbo.tb_cliente VALUES ('Isadora Farias'), ('Ricardo Almeida'), ('Sofia Pereira'), ('Gustavo Rodrigues'), ('Patrícia Costa')

INSERT INTO dbo.tb_perfil VALUES (1, 'Conservador', 'Perfil com baixa movimentação, foco em liquidez'),
(2, 'Moderado', 'Perfil equilibrado entre liquidez e rentabilidade'),
(3, 'Agressivo', 'Perfil para quem busca por alta rentabilidade, maior risco')

INSERT INTO dbo.tb_produto VALUES
('Poupança', 'Renda Fixa', 6.17, 'Muito Baixo', 1, 1),
('CDB Pós-fixado', 'Renda Fixa', 10.50, 'Baixo', 1, 1),
('CDB Prefixado', 'Renda Fixa', 11.00, 'Baixo', 2, 7),
('LCI/LCA', 'Renda Fixa', 9.80, 'Baixo', 1, 7),
('Tesouro Selic', 'Título Público', 10.65, 'Muito Baixo', 1, 0),
('Tesouro IPCA+', 'Título Público', 6.00, 'Baixo', 2, 10),
('Fundos Renda Fixa', 'Fundo de Investimento', 9.50, 'Baixo', 2, 5),
('Fundos Multimercado', 'Fundo de Investimento', 12.00, 'Médio', 2, 8),
('Fundos de Ações', 'Fundo de Investimento', 15.00, 'Alto', 3, 9),
('FIIs', 'Fundo Imobiliário', 13.00, 'Médio/Alto', 3, 5),
('Previdência (PGBL/VGBL)', 'Previdência', 8.50, 'Baixo a Médio', 2, 10),
('Home Broker (Ações)', 'Renda Variável', 18.00, 'Alto', 3, 4);