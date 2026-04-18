INSERT INTO tb_users (name, email, password, role)
SELECT 'Admin PRS', 'admin@prs.com', '$2a$10$BIrkveP1wspUR8NeqoZjt.SP67hBInPEqHKwD6IfemOnlJwQ/bo6q', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM tb_users WHERE email = 'admin@prs.com');

INSERT INTO tb_users (name, email, password, role)
SELECT 'Ana Lima', 'ana.lima@prs.com', '$2a$10$BIrkveP1wspUR8NeqoZjt.SP67hBInPEqHKwD6IfemOnlJwQ/bo6q', 'ANALYST'
WHERE NOT EXISTS (SELECT 1 FROM tb_users WHERE email = 'ana.lima@prs.com');

INSERT INTO tb_clients (name, email, company, phone, contract_date)
SELECT 'Carlos Mendes', 'carlos@techcorp.com', 'TechCorp Solutions', '(31) 98877-1234', '2024-01-15'
WHERE NOT EXISTS (SELECT 1 FROM tb_clients WHERE email = 'carlos@techcorp.com');

INSERT INTO tb_clients (name, email, company, phone, contract_date)
SELECT 'Fernanda Souza', 'fernanda@logistica.com', 'Logística Express', '(11) 97766-5678', '2024-03-01'
WHERE NOT EXISTS (SELECT 1 FROM tb_clients WHERE email = 'fernanda@logistica.com');

INSERT INTO tb_clients (name, email, company, phone, contract_date)
SELECT 'Ricardo Alves', 'ricardo@varejo.com', 'Varejo Digital', '(21) 96655-9012', '2024-05-20'
WHERE NOT EXISTS (SELECT 1 FROM tb_clients WHERE email = 'ricardo@varejo.com');

INSERT INTO tb_clients (name, email, company, phone, contract_date)
SELECT 'Patricia Costa', 'patricia@saude.com', 'Saúde em Dados', '(41) 95544-3456', '2024-07-10'
WHERE NOT EXISTS (SELECT 1 FROM tb_clients WHERE email = 'patricia@saude.com');

INSERT INTO tb_clients (name, email, company, phone, contract_date)
SELECT 'Marcos Oliveira', 'marcos@industria.com', 'Indústria 4.0', '(51) 94433-7890', '2024-09-05'
WHERE NOT EXISTS (SELECT 1 FROM tb_clients WHERE email = 'marcos@industria.com');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Dashboard de Vendas', 'Criação de dashboard Power BI para análise de vendas mensais', 1, 'COMPLETED', '2024-02-01', '2024-04-01', '2024-03-28', 18000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Dashboard de Vendas');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'ETL Pipeline', 'Implementação de pipeline de dados para integração de sistemas', 1, 'COMPLETED', '2024-05-01', '2024-07-01', '2024-07-05', 25000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'ETL Pipeline');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Relatório Logístico', 'Automação de relatórios de entregas e rotas', 2, 'IN_PROGRESS', '2024-08-01', '2024-10-01', null, 15000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Relatório Logístico');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'BI Financeiro', 'Análise financeira com indicadores de desempenho', 2, 'DELAYED', '2024-06-01', '2024-08-01', null, 22000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'BI Financeiro');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Sistema de Metas', 'Painel de metas e KPIs para equipe de vendas', 3, 'IN_PROGRESS', '2024-09-01', '2024-11-01', null, 12000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Sistema de Metas');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Análise de Estoque', 'Monitoramento de estoque em tempo real', 3, 'DELAYED', '2024-07-01', '2024-09-01', null, 19000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Análise de Estoque');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Agente IA Diagnóstico', 'Implementação de agente de IA para triagem de pacientes', 4, 'IN_PROGRESS', '2024-10-01', '2025-01-01', null, 45000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Agente IA Diagnóstico');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Dashboard Hospitalar', 'Painel de indicadores hospitalares em tempo real', 4, 'COMPLETED', '2024-04-01', '2024-06-01', '2024-06-15', 28000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Dashboard Hospitalar');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Automação Industrial', 'Automação de processos com análise preditiva', 5, 'IN_PROGRESS', '2024-11-01', '2025-02-01', null, 55000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Automação Industrial');

INSERT INTO tb_projects (title, description, client_id, status, start_date, expected_end_date, end_date, value)
SELECT 'Monitoramento de Máquinas', 'Sistema de monitoramento IoT integrado ao BI', 5, 'CANCELLED', '2024-08-01', '2024-10-01', null, 30000.00
WHERE NOT EXISTS (SELECT 1 FROM tb_projects WHERE title = 'Monitoramento de Máquinas');