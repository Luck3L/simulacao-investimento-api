USE [simulacao]
GO
/****** Object:  Table [dbo].[tb_cliente]    Script Date: 19/11/2025 21:03:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_cliente](
	[id_cliente] [bigint] IDENTITY(1,1) NOT NULL,
	[no_cliente] [varchar](50) NOT NULL,
 CONSTRAINT [PK_tb_cliente] PRIMARY KEY CLUSTERED 
(
	[id_cliente] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_investimento]    Script Date: 19/11/2025 21:03:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_investimento](
	[id_investimento] [bigint] IDENTITY(1,1) NOT NULL,
	[id_cliente] [bigint] NOT NULL,
	[id_produto] [int] NOT NULL,
	[vl_investimento] [money] NOT NULL,
	[dt_investimento] [datetime] NOT NULL,
 CONSTRAINT [PK_tb_investimento] PRIMARY KEY CLUSTERED 
(
	[id_investimento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_perfil]    Script Date: 19/11/2025 21:03:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_perfil](
	[id_perfil] [tinyint] NOT NULL,
	[no_perfil] [varchar](20) NOT NULL,
	[ds_perfil] [varchar](100) NOT NULL,
 CONSTRAINT [PK_tb_perfil] PRIMARY KEY CLUSTERED 
(
	[id_perfil] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_produto]    Script Date: 19/11/2025 21:03:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_produto](
	[id_produto] [int] IDENTITY(1,1) NOT NULL,
	[no_produto] [varchar](50) NOT NULL,
	[tp_produto] [varchar](50) NOT NULL,
	[vl_rentabilidade] [float] NOT NULL,
	[ds_risco] [varchar](20) NOT NULL,
	[id_perfil] [tinyint] NOT NULL,
	[vl_classificacao_liquidez] [tinyint] NOT NULL,
 CONSTRAINT [PK_tb_produto] PRIMARY KEY CLUSTERED 
(
	[id_produto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_simulacao]    Script Date: 19/11/2025 21:03:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_simulacao](
	[id_simulacao] [bigint] IDENTITY(1,1) NOT NULL,
	[id_cliente] [bigint] NOT NULL,
	[id_produto] [int] NOT NULL,
	[vl_investido] [money] NOT NULL,
	[vl_final] [money] NOT NULL,
	[qt_prazo] [tinyint] NOT NULL,
	[dt_simulacao] [datetime] NOT NULL,
 CONSTRAINT [PK_tb_simulacao] PRIMARY KEY CLUSTERED 
(
	[id_simulacao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tb_telemetria]    Script Date: 19/11/2025 21:03:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tb_telemetria](
	[id_chamada] [bigint] IDENTITY(1,1) NOT NULL,
	[no_servico] [varchar](250) NOT NULL,
	[ms_resposta_chamada] [bigint] NOT NULL,
	[dt_chamada] [datetime] NOT NULL,
 CONSTRAINT [PK_tb_telemetria] PRIMARY KEY CLUSTERED 
(
	[id_chamada] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tb_investimento]  WITH CHECK ADD  CONSTRAINT [FK_tb_investimento_tb_cliente] FOREIGN KEY([id_cliente])
REFERENCES [dbo].[tb_cliente] ([id_cliente])
GO
ALTER TABLE [dbo].[tb_investimento] CHECK CONSTRAINT [FK_tb_investimento_tb_cliente]
GO
ALTER TABLE [dbo].[tb_investimento]  WITH CHECK ADD  CONSTRAINT [FK_tb_investimento_tb_produto] FOREIGN KEY([id_produto])
REFERENCES [dbo].[tb_produto] ([id_produto])
GO
ALTER TABLE [dbo].[tb_investimento] CHECK CONSTRAINT [FK_tb_investimento_tb_produto]
GO
ALTER TABLE [dbo].[tb_produto]  WITH CHECK ADD  CONSTRAINT [FK_tb_perfil_tb_produto] FOREIGN KEY([id_perfil])
REFERENCES [dbo].[tb_perfil] ([id_perfil])
GO
ALTER TABLE [dbo].[tb_produto] CHECK CONSTRAINT [FK_tb_perfil_tb_produto]
GO
ALTER TABLE [dbo].[tb_simulacao]  WITH CHECK ADD  CONSTRAINT [FK_tb_simulacao_tb_cliente] FOREIGN KEY([id_cliente])
REFERENCES [dbo].[tb_cliente] ([id_cliente])
GO
ALTER TABLE [dbo].[tb_simulacao] CHECK CONSTRAINT [FK_tb_simulacao_tb_cliente]
GO
ALTER TABLE [dbo].[tb_simulacao]  WITH CHECK ADD  CONSTRAINT [FK_tb_simulacao_tb_produto] FOREIGN KEY([id_produto])
REFERENCES [dbo].[tb_produto] ([id_produto])
GO
ALTER TABLE [dbo].[tb_simulacao] CHECK CONSTRAINT [FK_tb_simulacao_tb_produto]
GO