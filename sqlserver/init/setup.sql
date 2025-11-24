USE [master];
GO

-- Cria o login se não existir
IF NOT EXISTS (SELECT name FROM sys.server_principals WHERE name = 'usr_simulacao')
BEGIN
    CREATE LOGIN usr_simulacao WITH PASSWORD='simulacao_usr',
    CHECK_EXPIRATION=OFF,
    CHECK_POLICY=OFF;
END
GO

-- Garante que o login tenha permissão para criar bases
IF NOT EXISTS (
    SELECT * FROM sys.server_permissions
    WHERE grantee_principal_id = SUSER_ID('usr_simulacao')
      AND permission_name = 'CREATE ANY DATABASE'
)
BEGIN
    GRANT CREATE ANY DATABASE TO usr_simulacao;
END
GO

-- Cria a base se não existir
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'simulacao')
BEGIN
    CREATE DATABASE simulacao;
END
GO

USE [simulacao];
GO

-- Cria o usuário mapeado para o login se não existir
IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'usr_simulacao')
BEGIN
    CREATE USER usr_simulacao FOR LOGIN usr_simulacao;
END
GO

-- Adiciona às roles apenas se não for membro ainda
IF NOT EXISTS (
    SELECT * FROM sys.database_role_members
    WHERE role_principal_id = DATABASE_PRINCIPAL_ID('db_owner')
      AND member_principal_id = DATABASE_PRINCIPAL_ID('usr_simulacao')
)
BEGIN
    ALTER ROLE db_owner ADD MEMBER usr_simulacao;
END
GO

IF NOT EXISTS (
    SELECT * FROM sys.database_role_members
    WHERE role_principal_id = DATABASE_PRINCIPAL_ID('db_datareader')
      AND member_principal_id = DATABASE_PRINCIPAL_ID('usr_simulacao')
)
BEGIN
    ALTER ROLE db_datareader ADD MEMBER usr_simulacao;
END
GO

IF NOT EXISTS (
    SELECT * FROM sys.database_role_members
    WHERE role_principal_id = DATABASE_PRINCIPAL_ID('db_datawriter')
      AND member_principal_id = DATABASE_PRINCIPAL_ID('usr_simulacao')
)
BEGIN
    ALTER ROLE db_datawriter ADD MEMBER usr_simulacao;
END
GO
