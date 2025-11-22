-- Garante que estamos no banco de dados Master
USE [master];
GO

-- 1. CRIAÇÃO DO LOGIN DE SERVIDOR (se não existir)
IF NOT EXISTS (SELECT name FROM sys.server_principals WHERE name = 'usr_simulacao')
BEGIN
    CREATE LOGIN usr_simulacao WITH PASSWORD='simulacao_usr',
    DEFAULT_DATABASE=[simulacao],
    CHECK_EXPIRATION=OFF,
    CHECK_POLICY=OFF;
END
GO

-- Concede a permissão para o login criar bancos de dados (necessário se você usar createDatabaseIfNotExist no Spring)
GRANT CREATE ANY DATABASE TO usr_simulacao;
GO

-- 2. CRIAÇÃO DO BANCO DE DADOS (se não existir)
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'simulacao')
BEGIN
    CREATE DATABASE simulacao;
END
GO

-- 3. CRIAÇÃO DO USUÁRIO DE BANCO DE DADOS E PERMISSÕES
USE [simulacao];
GO

GRANT CREATE TABLE TO usr_simulacao;
GO

EXEC sp_addrolemember 'db_owner', 'usr_simulacao';
GO

-- Cria o Usuário de Banco de Dados (mapeando para o Login de Servidor)
IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'usr_simulacao')
BEGIN
    CREATE USER usr_simulacao FOR LOGIN usr_simulacao;
END
GO

-- Concede permissões de leitura e escrita
ALTER ROLE db_datareader ADD MEMBER usr_simulacao;
ALTER ROLE db_datawriter ADD MEMBER usr_simulacao;
GO