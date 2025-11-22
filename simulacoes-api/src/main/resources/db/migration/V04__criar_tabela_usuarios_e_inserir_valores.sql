CREATE TABLE dbo.tb_usuario
	(
	id_usuario int NOT NULL IDENTITY,
	no_usuario varchar(50) NOT NULL,
	co_senha varchar(150) NOT NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.tb_usuario ADD CONSTRAINT
	PK_tb_usuario PRIMARY KEY CLUSTERED 
	(
	id_usuario
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO

INSERT INTO dbo.tb_usuario VALUES('usuario', '$2a$10$ZLS.jDf9YYcYFG7nWIHrE.cIkDTuGnm30X17bJ83Yx0rjhYuaTvWO')