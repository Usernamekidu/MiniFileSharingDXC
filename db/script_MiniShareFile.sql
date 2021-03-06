USE [MiniShareFile]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 05/18/2018 12:07:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Categories](
	[categoryID] [int] IDENTITY(1,1) NOT NULL,
	[categoryName] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Categories] ON
INSERT [dbo].[Categories] ([categoryID], [categoryName]) VALUES (1, N'Book')
INSERT [dbo].[Categories] ([categoryID], [categoryName]) VALUES (2, N'Image')
INSERT [dbo].[Categories] ([categoryID], [categoryName]) VALUES (3, N'Document')
SET IDENTITY_INSERT [dbo].[Categories] OFF
/****** Object:  Table [dbo].[Users]    Script Date: 05/18/2018 12:07:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Users](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[changeRankDate] [datetime] NULL,
	[createDate] [datetime] NULL,
	[deleteDate] [datetime] NULL,
	[email] [varchar](255) NULL,
	[isAdmin] [varchar](255) NULL,
	[lastModifyDate] [datetime] NULL,
	[userActive] [varchar](255) NULL,
	[userName] [varchar](255) NULL,
	[userPassword] [varchar](255) NULL,
	[userRank] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_ncoa9bfasrql0x4nhmh1plxxy] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON
INSERT [dbo].[Users] ([userID], [changeRankDate], [createDate], [deleteDate], [email], [isAdmin], [lastModifyDate], [userActive], [userName], [userPassword], [userRank]) VALUES (1, NULL, CAST(0x0000A8E300E10991 AS DateTime), NULL, N'abc@gmail.com', N'admin', CAST(0x0000A8E300E10991 AS DateTime), N'Active', N'abc', N'123', N'Bronze')
INSERT [dbo].[Users] ([userID], [changeRankDate], [createDate], [deleteDate], [email], [isAdmin], [lastModifyDate], [userActive], [userName], [userPassword], [userRank]) VALUES (2, NULL, CAST(0x0000A8E40090A6A1 AS DateTime), NULL, N'dung@gmail.com', N'user', CAST(0x0000A8E40090A6A1 AS DateTime), N'Active', N'dung@gmail.com', N'123123', N'Bronze')
INSERT [dbo].[Users] ([userID], [changeRankDate], [createDate], [deleteDate], [email], [isAdmin], [lastModifyDate], [userActive], [userName], [userPassword], [userRank]) VALUES (3, NULL, NULL, NULL, N'admin@gmail.com', N'admin', NULL, N'Active', N'admin', N'123', NULL)
SET IDENTITY_INSERT [dbo].[Users] OFF
/****** Object:  Table [dbo].[Files]    Script Date: 05/18/2018 12:07:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Files](
	[fileID] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](255) NULL,
	[fileName] [varchar](255) NULL,
	[fileSize] [float] NULL,
	[rootPath] [varchar](255) NULL,
	[uploadDate] [datetime] NULL,
	[categoryID] [int] NOT NULL,
	[userID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[fileID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Files] ON
INSERT [dbo].[Files] ([fileID], [description], [fileName], [fileSize], [rootPath], [uploadDate], [categoryID], [userID]) VALUES (1, N'123123', NULL, 894344, N'18052018084718_notes.exe', CAST(0x0000A8E40090D413 AS DateTime), 1, 2)
SET IDENTITY_INSERT [dbo].[Files] OFF
/****** Object:  Table [dbo].[Downloads]    Script Date: 05/18/2018 12:07:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Downloads](
	[downloadID] [int] IDENTITY(1,1) NOT NULL,
	[downloadDate] [datetime] NULL,
	[fileID] [int] NOT NULL,
	[userID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[downloadID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  ForeignKey [FK_7twg4a3bnooj06rd2hm5i7bva]    Script Date: 05/18/2018 12:07:00 ******/
ALTER TABLE [dbo].[Downloads]  WITH CHECK ADD  CONSTRAINT [FK_7twg4a3bnooj06rd2hm5i7bva] FOREIGN KEY([userID])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[Downloads] CHECK CONSTRAINT [FK_7twg4a3bnooj06rd2hm5i7bva]
GO
/****** Object:  ForeignKey [FK_jbkahdwj6sgwl1iv2rcnrf83b]    Script Date: 05/18/2018 12:07:00 ******/
ALTER TABLE [dbo].[Downloads]  WITH CHECK ADD  CONSTRAINT [FK_jbkahdwj6sgwl1iv2rcnrf83b] FOREIGN KEY([fileID])
REFERENCES [dbo].[Files] ([fileID])
GO
ALTER TABLE [dbo].[Downloads] CHECK CONSTRAINT [FK_jbkahdwj6sgwl1iv2rcnrf83b]
GO
/****** Object:  ForeignKey [FK_7avyq6o26mp2qyum6smuv4m3r]    Script Date: 05/18/2018 12:07:00 ******/
ALTER TABLE [dbo].[Files]  WITH CHECK ADD  CONSTRAINT [FK_7avyq6o26mp2qyum6smuv4m3r] FOREIGN KEY([userID])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[Files] CHECK CONSTRAINT [FK_7avyq6o26mp2qyum6smuv4m3r]
GO
/****** Object:  ForeignKey [FK_rasv7jxhl5ipcrjvotrde5bv4]    Script Date: 05/18/2018 12:07:00 ******/
ALTER TABLE [dbo].[Files]  WITH CHECK ADD  CONSTRAINT [FK_rasv7jxhl5ipcrjvotrde5bv4] FOREIGN KEY([categoryID])
REFERENCES [dbo].[Categories] ([categoryID])
GO
ALTER TABLE [dbo].[Files] CHECK CONSTRAINT [FK_rasv7jxhl5ipcrjvotrde5bv4]
GO
