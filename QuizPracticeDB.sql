CREATE DATABASE [Quiz_DB4] -- New Version 

USE [Quiz_DB4]
GO
/****** Object:  Table [dbo].[AnswerQuestion]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AnswerQuestion](
	[AnswerID] [int] IDENTITY(1,1) NOT NULL,
	[IsCorrect] [bit] NOT NULL,
	[AnswerContent] [nvarchar](255) NOT NULL,
	[QuestionID] [int] NOT NULL,
 CONSTRAINT [PK_AnswerQuestion] PRIMARY KEY CLUSTERED 
(
	[AnswerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Answers]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Answers](
	[AnswerID] [int] IDENTITY(1,1) NOT NULL,
	[AnswerDetail] [nvarchar](1000) NULL,
	[IsAnswer] [int] NOT NULL,
	[option1] [varchar](50) NOT NULL,
	[option2] [varchar](50) NOT NULL,
	[option3] [varchar](50) NOT NULL,
	[option4] [varchar](50) NOT NULL,
	[QuestionID] [int] NOT NULL,
 CONSTRAINT [Answers_PK] PRIMARY KEY CLUSTERED 
(
	[AnswerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[CategoryID] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](255) NULL,
 CONSTRAINT [Category_PK] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LesMooc]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LesMooc](
	[LesMoocID] [int] IDENTITY(1,1) NOT NULL,
	[LessonName] [nvarchar](250) NOT NULL,
	[VideoLink] [nvarchar](500) NULL,
	[CreatedAt] [date] NULL,
	[created_by] [int] NULL,
	[MoocID] [int] NULL,
	[status] [nvarchar](200) NULL,
	[Content] [nvarchar](max) NULL,
	[duration] [int] NULL,
 CONSTRAINT [LesMooc_PK] PRIMARY KEY CLUSTERED 
(
	[LesMoocID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Lessons]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Lessons](
	[LessonId] [int] IDENTITY(1,1) NOT NULL,
	[LessonName] [nvarchar](250) NOT NULL,
	[VideoLink] [nvarchar](500) NULL,
	[CreatedAt] [date] NULL,
	[created_by] [int] NULL,
	[SubjectID] [int] NULL,
	[Content] [nvarchar](max) NULL,
	[status] [nvarchar](200) NULL,
 CONSTRAINT [Lessons_PK] PRIMARY KEY CLUSTERED 
(
	[LessonId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mooc]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mooc](
	[MoocID] [int] IDENTITY(1,1) NOT NULL,
	[MoocName] [nvarchar](400) NOT NULL,
	[SubjectID] [int] NOT NULL,
	[Status] [bit] NULL,
 CONSTRAINT [Mooc_PK] PRIMARY KEY CLUSTERED 
(
	[MoocID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Packages]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Packages](
	[PackageID] [int] IDENTITY(1,1) NOT NULL,
	[package_name] [varchar](255) NOT NULL,
	[description] [nvarchar](250) NULL,
	[listPrice] [float] NULL,
	[salePrice] [float] NULL,
	[duration] [int] NOT NULL,
	[status] [nvarchar](200) NULL,
 CONSTRAINT [Package_PK] PRIMARY KEY CLUSTERED 
(
	[PackageID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Progress]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Progress](
	[ProgressID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NULL,
	[QuizID] [int] NULL,
	[SubjectID] [int] NULL,
	[State] [int] NULL,
	[CreatedAt] [date] NULL,
 CONSTRAINT [Progress_PK] PRIMARY KEY CLUSTERED 
(
	[ProgressID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Questions]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Questions](
	[QuestionID] [int] IDENTITY(1,1) NOT NULL,
	[QuestionDetail] [nvarchar](500) NULL,
	[QuizID] [int] NOT NULL,
	[Type] [int] NULL,
 CONSTRAINT [Questions_PK] PRIMARY KEY CLUSTERED 
(
	[QuestionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Quizzes]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Quizzes](
	[QuizID] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](255) NOT NULL,
	[Image] [image] NULL,
	[description] [nvarchar](250) NULL,
	[Level] [int] NULL,
	[SubjectID] [int] NOT NULL,
	[CategoryID] [int] NULL,
	[created_by] [int] NULL,
	[created_at] [datetime] NULL,
	[duration] [int] NULL,
	[type] [int] NULL,
	[Status] [bit] NULL,
 CONSTRAINT [Quizzes_PK] PRIMARY KEY CLUSTERED 
(
	[QuizID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE Quizzes
ADD [Status] bit NULL;
Update Quizzes SET [Status] = 1 

/****** Object:  Table [dbo].[RatingReactions]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RatingReactions](
	[ReactionID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[RatingID] [int] NOT NULL,
	[Status] [bit] NOT NULL,
 CONSTRAINT [RatingReactions_PK] PRIMARY KEY CLUSTERED 
(
	[ReactionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ratings]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ratings](
	[RatingID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[LesMoocID] [int] NULL,
	[rating] [int] NULL,
	[comment] [nvarchar](max) NULL,
	[created_at] [datetime] NULL,
	[Like] [varchar](100) NULL,
	[IsReply] [bit] NULL,
	[Status] [bit] NULL,
 CONSTRAINT [Ratings_PK] PRIMARY KEY CLUSTERED 
(
	[RatingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Registrations]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Registrations](
	[RegisterID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NULL,
	[SubjectID] [int] NULL,
	[PackageID] [int] NULL,
	[total_cost] [decimal](10, 2) NULL,
	[status] [int] NULL,
	[valid_from] [date] NULL,
	[valid_to] [date] NULL,
	[created_at] [datetime] NULL,
 CONSTRAINT [Registrations_PK] PRIMARY KEY CLUSTERED 
(
	[RegisterID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reply]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reply](
	[ReplyID] [int] IDENTITY(1,1) NOT NULL,
	[RatingID] [int] NOT NULL,
	[UserID] [int] NOT NULL,
	[Content] [nvarchar](500) NULL,
	[DateReply] [datetime] NULL,
	[Status] [bit] NULL,
	[Like] [varchar](100) NULL,
 CONSTRAINT [Reply_PK] PRIMARY KEY CLUSTERED 
(
	[ReplyID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [varchar](50) NOT NULL,
	[description] [nvarchar](250) NULL,
 CONSTRAINT [Roles_PK] PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Subjects]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Subjects](
	[SubjectID] [int] IDENTITY(1,1) NOT NULL,
	[Subject_Name] [nvarchar](200) NULL,
	[Description] [nvarchar](500) NULL,
	[Image] [nvarchar](200) NULL,
	[Status] [bit] NULL,
	[PackageId] [int] NULL,
	[CategoryId] [int] NULL,
	[created_by] [int] NULL,
	[Created_at] [date] NULL,
 CONSTRAINT [Subject_PK] PRIMARY KEY CLUSTERED 
(
	[SubjectID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Teacher]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Teacher](
	[TeacherID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[Position] [nvarchar](200) NULL,
	[Department] [nvarchar](200) NULL,
	[Qualifications] [nvarchar](500) NULL,
	[DateWork] [date] NULL,
	[About] [nvarchar](max) NULL,
	[Slogan] [nvarchar](250) NULL,
 CONSTRAINT [Teacher_PK] PRIMARY KEY CLUSTERED 
(
	[TeacherID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User_Answers]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_Answers](
	[USAnswerID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[QuestionID] [int] NOT NULL,
	[AnswerID] [int] NOT NULL,
	[answered_at] [datetime] NOT NULL,
 CONSTRAINT [PK_User_Answers] PRIMARY KEY CLUSTERED 
(
	[USAnswerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User_Quiz_Results]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_Quiz_Results](
	[ResultID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[QuizID] [int] NOT NULL,
	[score] [float] NOT NULL,
	[completed_at] [datetime] NOT NULL,
	[status] [bit] NULL,
 CONSTRAINT [User_Quiz_Results_PK] PRIMARY KEY CLUSTERED 
(
	[ResultID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserQuizChoices]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserQuizChoices](
	[choiceId] [int] IDENTITY(1,1) NOT NULL,
	[userId] [int] NULL,
	[QuizID] [int] NULL,
	[questionId] [int] NULL,
	[selectedAnswerId] [int] NULL,
	[isCorrect] [bit] NULL,
	[startTime] [datetime] NULL,
	[endTime] [datetime] NULL,
	[resultID] [int] NULL,
 CONSTRAINT [UserQuizChoices_PK] PRIMARY KEY CLUSTERED 
(
	[choiceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserReaction]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserReaction](
	[UserID] [int] NOT NULL,
	[RatingID] [int] NOT NULL,
	[isLike] [bit] NULL,
 CONSTRAINT [UserReaction_PK] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC,
	[RatingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 7/26/2024 1:58:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[FullName] [nvarchar](200) NULL,
	[UserName] [nvarchar](250) NULL,
	[DateOfBirth] [date] NULL,
	[Email] [nvarchar](200) NULL,
	[Password] [nvarchar](250) NULL,
	[Phone] [varchar](50) NULL,
	[Address] [nvarchar](300) NULL,
	[Gender] [bit] NULL,
	[RoleID] [int] NULL,
	[Avatar] [nvarchar](400) NULL,
	[Create_at] [date] NULL,
 CONSTRAINT [Users_PK] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[AnswerQuestion] ON 
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (1, 0, N'Tra loi 1', 49)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (2, 1, N'Tra lời 2 ', 49)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (3, 0, N'Tra loi 3 ', 49)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (4, 0, N' TRa loi 4', 49)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (5, 1, N'Đáp án đúng', 50)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (6, 0, N'đúng rồi', 50)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (7, 0, N'test3', 50)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (8, 0, N'test4', 50)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (9, 0, N'question 1', 52)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (10, 1, N'question2', 52)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (11, 0, N'question3', 52)
INSERT [dbo].[AnswerQuestion] ([AnswerID], [IsCorrect], [AnswerContent], [QuestionID]) VALUES (12, 0, N'question4', 52)
SET IDENTITY_INSERT [dbo].[AnswerQuestion] OFF
GO
SET IDENTITY_INSERT [dbo].[Categories] ON 

INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (1, N'Science')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (2, N'Mathematics')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (3, N'History')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (4, N'Literature')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (5, N'Geography')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (6, N'Information Technology')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (7, N'Digital Marketing')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (8, N'Economics')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (9, N'Business Administration')
INSERT [dbo].[Categories] ([CategoryID], [category_name]) VALUES (10, N'Language Studies')
SET IDENTITY_INSERT [dbo].[Categories] OFF
GO
SET IDENTITY_INSERT [dbo].[LesMooc] ON;

INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (1, N'Introduction to Physics', N'https://www.youtube.com/embed/Y-NQOJ3Uz4w', '2024-05-16', 8, 1, 'Active', N'Physics is the branch of science which deals with matter and its relation to energy.
It involves study of physical and natural phenomena around us. Examples of these phenomena are formation of rainbow, occurrence eclipse, the fall of things from up to down, the cause of sunset and sunrise, formation of shadow and many more.', 2.35 );
INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (2, N'Linear Motion: Vector Resolution and Forces', N'https://www.youtube.com/embed/WaPoAb4MALo', '2024-05-16', 8, 1, 'Active', 'Vector Resolution is splitting a vector into its components along different coordinate axes. When a vector is expressed in terms of its components, it becomes easier to analyze its effects in different directions. This process is particularly useful when dealing with vector quantities such as forces, velocities etc.
Vector resolution is a powerful tool in physics and engineering, that enables the analysis and prediction of complex physical phenomena by simplifying vectors into manageable components.', 37.32);
INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (3, N'Newton Law of Motion - First, Second & Third', N'https://www.youtube.com/embed/g550H4e5FCY', '2024-05-16', 8, 1, 'Active', 'Newton’s laws of motion, three statements describing the relations between the forces acting on a body and the motion of the body, first formulated by English physicist and mathematician Isaac Newton, which are the foundation of classical mechanics.', 38.23);
INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (4, N'Introduction to temperature and heat', N'https://www.youtube.com/embed/XrtrzRXoHHo', '2024-05-16', 8, 2, 'Active', 'In thermodynamics, heat and temperature are closely related concepts with precise definitions. Heat, ‍ , is thermal energy transferred from a hotter system to a cooler system that are in contact. Temperature is a measure of the average kinetic energy of the atoms or molecules in the system.', 12.52);
INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (5, N'Thermodynamic Processes (Animation)', N'https://www.youtube.com/embed/3QMfZZs-Vm0', '2024-05-16', 8, 2, 'Active', 'The second law of thermodynamics is a complex topic that requires intensive study in the field of thermodynamics to truly understand. However, for the purpose of this article, only one small aspect needs to be understood and that is the fact that heat will always flow spontaneously from hotter substances to colder ones. This simple statement explains why an ice cube doesnt form outside on a hot day or why it melts when dropped in a bowl of warm water.', 9.18);
INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (6, N'An introduction to statistical physics', N'https://www.youtube.com/embed/0LdL1LUrE0I', '2024-05-16', 10, 3, 'Active', 'Introduction to Statistical Physics introduces the concepts and formalism at the foundations of statistical physics. By the end of the course, students should understand qualitative and quantitative definitions of entropy, the implications of the laws of thermodynamics, and why the Boltzmann distribution is important in modeling systems at finite temperature. In terms of skills, students should have increased their familiarity with mathematical methods in the physical science, learned how to write short programs to simulate random events, and become more adept at articulating their understanding of physics.', 7.33);
INSERT INTO [LesMooc] (LesMoocID, LessonName, VideoLink, CreatedAt, created_by, MoocID, [status], [content], duration) VALUES (7, N'Applications to solids and liquids', N'https://www.youtube.com/embed/yPsXj7bAdMo', '2024-05-16', 10, 3, 'Active', 'Many of our experiments are easier than you might think. We’ve made sure that there are clear instructions so that children don’t have to struggle over any of the steps, with or without a helping hand from parents. They also use items that you’re likely to have lying around the house, so there’s no hassle in finding the ingredients you need to make science fun.', 4.04);


SET IDENTITY_INSERT [dbo].[LesMooc] OFF;
GO
SET IDENTITY_INSERT [dbo].[Lessons] ON 

INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (1, N'Introduction to Physics', N'http://example.com/physics_intro', CAST(N'2024-05-16' AS Date), 1, 1, N'', N'Inactive')
INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (2, N'Advanced Calculus', N'http://example.com/calculus_advanced', CAST(N'2024-05-17' AS Date), 2, 2, N'', N'Inactive')
INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (3, N'Domain & Range Chapter 1 | Tập xác định, tập giá trị', N'https://youtu.be/LN3jjG70Ouk?si=EbjKG4EvWkrcOlra', CAST(N'2024-05-16' AS Date), 3, 6, N'', N'Active')
INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (4, N'Lập trình C 1 Slide 1 Cài đặt IDE First Code Kiểu dữ liệu', N'https://youtu.be/6dWImZU12SI?list=PLdQa3urTtNk0EsCrAjpzhGny6bvlpVdRC', CAST(N'2024-05-25' AS Date), 3, 7, N'', N'Active')
INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (5, N'CEA Chapter1', N'https://youtu.be/iJwC7qKyiqA?si=52kQzIWCENMJoj4t', CAST(N'2024-05-30' AS Date), 3, 8, N'', N'Active')
INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (6, N'CSI104 - Slot 2 - Introduction to Computer Science - Giới thiệu về...', N'https://youtu.be/ajJEIBcs5A4?si=FfQ_ZuJy5PWDG2kk', CAST(N'2024-06-01' AS Date), 3, 9, N'', N'Active')
INSERT [dbo].[Lessons] ([LessonId], [LessonName], [VideoLink], [CreatedAt], [created_by], [SubjectID], [Content], [status]) VALUES (7, N'SSL101-FPTU-SLOT1', N'https://youtu.be/gNCT2Nwukag?si=Iug9kMApMvX-yQGp', CAST(N'2024-06-16' AS Date), 3, 10, N'', N'Active')
SET IDENTITY_INSERT [dbo].[Lessons] OFF
GO
SET IDENTITY_INSERT [dbo].[Mooc] ON 

INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (1, N'Introduction to Mechanics', 1, 1)
INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (2, N'Applications of Thermodynamics', 1, 1)
INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (3, N'Electromagnetism', 1, 1)
INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (4, N'Introduction to Calculus', 2, 1)
INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (5, N'Concepts and Properties of Derivatives', 2, 1)
INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (6, N'Concepts and Properties of Integrals', 2, 1)
INSERT [dbo].[Mooc] ([MoocID], [MoocName], [SubjectID], [Status]) VALUES (7, N'First-Order Differential Equations', 2, 1)
SET IDENTITY_INSERT [dbo].[Mooc] OFF
GO
SET IDENTITY_INSERT [dbo].[Packages] ON 

INSERT [dbo].[Packages] ([PackageID], [package_name], [description], [listPrice], [salePrice], [duration], [status]) VALUES (1, N'Free', N'Basic access for 7 days', 0, 0, 7, N'Active')
INSERT [dbo].[Packages] ([PackageID], [package_name], [description], [listPrice], [salePrice], [duration], [status]) VALUES (2, N'Basic Package', N'Basic access for 60 days', 200, 150, 60, N'Active')
INSERT [dbo].[Packages] ([PackageID], [package_name], [description], [listPrice], [salePrice], [duration], [status]) VALUES (3, N'Medium Package', N'Medium access for 90 days', 350, 250, 90, N'Inactive')
INSERT [dbo].[Packages] ([PackageID], [package_name], [description], [listPrice], [salePrice], [duration], [status]) VALUES (4, N'Premium Package', N'Premium access for 365 days', 800, 650, 365, N'Active')
SET IDENTITY_INSERT [dbo].[Packages] OFF
GO
SET IDENTITY_INSERT [dbo].[Questions] ON 

-- Easy
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (1, N'Nhiệt độ sôi của nước là bao nhiêu?', 4, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (2, N'Ký hiệu hóa học của nước là gì?', 4, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (3, N'Hành tinh nào được gọi là Hành tinh Đỏ?', 1, 2);

-- Medium --
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (4, N'Khí nào phổ biến nhất trong khí quyển Trái Đất?', 4, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (5, N'Công thức tính tốc độ là gì?', 4, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (6, N'Quá trình nào mà thực vật tạo ra thức ăn?', 4, 2);

---- Hard --
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (7, N'Cơ quan năng lượng của tế bào là gì?', 4, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (8, N'Số nguyên tử của carbon là bao nhiêu?', 4, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (9, N'Định luật bảo toàn khối lượng là gì?', 4, 2);


-- Easy
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (10, N'(-11) - (11) là bao nhiêu?', 5, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (11, N'Trong một bữa tiệc trẻ em, 10 đứa trẻ uống nước ép, 8 đứa trẻ ăn bánh, và 6 đứa trẻ uống nước ép & ăn bánh. Có bao nhiêu đứa trẻ ở bữa tiệc?', 5, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (12, N'Nếu tổng của hai số chẵn liên tiếp là 166, thì số nhỏ hơn là bao nhiêu?', 5, 2);

-- Medium
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (13, N'Một chai đắt hơn một cái nút chai một đô la. Cả hai cùng có giá 110 cent. Chai có giá bao nhiêu?', 5, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (14, N'Số tiếp theo trong dãy là gì? 7,645, 5,764, 4,576, ?', 5, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (15, N'Nếu tổng của hai số là 51 và hiệu của chúng là 13, hãy tìm số nhỏ nhất.', 5, 2);

-- Hard
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (16, N'Chín cây được trồng thành hàng cách nhau ba feet. Khoảng cách từ cây đầu tiên đến cây cuối cùng là bao nhiêu?', 5, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (17, N'Ken làm việc 8 giờ mỗi tuần và kiếm được 6 đô la mỗi giờ. Carl làm việc 4 giờ mỗi tuần và kiếm được 8 đô la mỗi giờ. Ai kiếm được nhiều tiền hơn mỗi tuần, Ken hay Carl?', 5, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (18, N'12 nhân 15 là bao nhiêu?', 5, 2);


-- Easy
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (19, N'Tên nước đầu tiên của Việt Nam là gì?', 6, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (20, N'Vị anh hùng nào đã nghĩ ra kế dùng cọc gỗ để chặn giặc trên sông Bạch Đằng?', 6, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (21, N'Nhân vật lịch sử nào đã giúp khôi phục hòa bình trong thời loạn lạc bằng cách dẹp loạn 12 sứ quân?', 6, 2);

---- Medium
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (22, N'Vị vua có nhiều vợ nhất nhưng lại không có người con nào?', 6, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (23, N'Ai là nữ vương đầu tiên trong lịch sử Việt Nam?', 6, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (24, N'Những thành tựu độc đáo của phong trào Âu Lạc là gì?', 6, 2);

-- Hard
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (25, N'Cuộc khởi nghĩa Hai Bà Trưng diễn ra vào năm nào?', 6, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (26, N'Ai là người lãnh đạo nhân dân ta chống lại quân Nam Hán?', 6, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (27, N'Lê Hoàn lên ngôi vua lấy tên là gì?', 6, 2);



-- Easy
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (28, N'Tác phẩm nào của Nguyễn Du được coi là "tác phẩm quốc dân"?', 7, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (29, N'Cuốn sách nào của Nam Cao nổi tiếng với câu chuyện về Tình Mẹ Con?', 7, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (30, N'Tác giả nào viết tiểu thuyết "Số Đỏ"?', 7, 2);

-- Medium
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (31, N'Ý kiến nào nói đúng nhất nguyên nhân sâu xa khiến lão Hạc phải lựa chọn cái chết?', 7, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (32, N'Nhận xét nào sau đây không đúng với đoạn trích Tức nước vỡ bờ?', 7, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (33, N'Trong tác phẩm "Tắt đèn", tên thật của Chị Dậu là gì?', 7, 2);
				  
-- Hard			  
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (34, N'Bài học đường đường đời đầu tiên được trích từ?', 7, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (35, N'Văn hào nào được biết đến với bút danh "Tản Đà"?', 7, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (36, N'Văn bản được coi là tác phẩm đầu tiên về Hà Nội?', 7, 2);
				  
				  
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (37, N'Tổng diện tích đất liền của Việt Nam là?', 8, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (38, N'Điểm cực Bắc nước ta nằm ở tỉnh nào?', 8, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (39, N'Điểm cực Tây nước ta nằm ở tỉnh nào?', 8, 2);
				  
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (40, N'Tài nguyên giữ vị trí quan trọng nhất Việt Nam hiện nay là?', 8, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (41, N'Tài nguyên rừng của Việt Nam bị suy thoái nghiêm trọng thể hiện ở?', 8, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (42, N'Gia tăng dân số tự nhiên là?', 8, 2);
				  
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (43, N'Vùng chuyên môn hóa về lương thực thực phẩm lớn nhất nước ta là?', 8, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (44, N'Kết quả quan trọng nhất của cuộc đổi mới nền kinh tế nước ta là?', 8, 2);
INSERT INTO [dbo].[Questions] (QuestionID, QuestionDetail, QuizId, Type) VALUES (45, N'Diện tích rừng ở Việt Nam năm 1990 là?', 8, 2);

------------------------------------------------------------------ Question Of Quiz ---------------------------------------------------------------------------------

INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], Type) VALUES (46, N'What is the biggest country in the world ?', 1, 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], Type) VALUES (47, N'Question test', 1, 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], Type) VALUES (48, N'Question test', 1, 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], Type) VALUES (49, N'Câu hỏi test', 1, 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], Type) VALUES (50, N'Câu hỏi 1 ', 3, 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], [Type]) VALUES (51, N'Question tests', 2, 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [QuestionDetail], [QuizID], [Type]) VALUES (52, N'test question', 3, 1)

SET IDENTITY_INSERT [dbo].[Questions] OFF
GO

/* -------------------------------------------------------------- INSERT INTO TABLE [Answers] -------------------------------------------------------------------------*/
SET IDENTITY_INSERT [dbo].[Answers] ON;

-- Easy
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(1, N'100°C', 3, N'90°C', N'80°C', N'100°C', N'110°C', 1),
(2, N'H2O', 2, N'O2', N'H2O', N'CO2', N'N2O', 2),
(3, N'Mars', 3, N'Venus', N'Mars', N'Mercury', N'Jupiter', 3);

-- Medium --
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(4, N'Nitrogen', 3, N'Oxygen', N'Carbon Dioxide', N'Nitrogen', N'Argon', 4),
(5, N'V = Δx / Δt', 2, N'V = Δx * Δt', N'V = Δx / Δt', N'V = Δx * Δt^2', N'V = Δt / Δx', 5),
(6, N'Photosynthesis', 2, N'Respiration', N'Photosynthesis', N'Water Absorption', N'Hydrolysis', 6);

-- Hard --
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(7, N'Mitochondria', 2, N'Lysosome', N'Mitochondria', N'Golgi Apparatus', N'Nucleus', 7),
(8, N'6', 3, N'4', N'5', N'6', N'7', 8),
(9, N'Mass cannot be created or destroyed, it can only change forms.', 3, N'Mass is constant in a closed system.', N'Mass can be created from energy.', N'Mass cannot be created or destroyed', N'Mass is constant in an open system.', 9);


---- Easy
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(10, N'-22', 2, N'-20', N'-22', N'-24', N'-26', 10),
(11, N'12 đứa trẻ. 6 đứa trẻ có nước trái cây và bánh, còn lại 2 đứa ăn bánh không có nước trái cây. Vì có 10 máy ép trái cây', 1, N'12', N'14', N'18', N'24', 11),
(12, N'82 + 84 = 166 và 80 + 86 = 166, Vì 82 & 84 là các số chẵn liên tiếp nên 82 là số nhỏ nhất', 2, N'80', N'82', N'84', N'86', 12);

-- Medium
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(13, N'Cái chai có giá 105 xu và cái nút chai có giá 5 xu', 2, N'100', N'105', N'115', N'90', 13),
(14, N'Chữ số cuối cùng được chuyển lên phía trước để tạo thành số tiếp theo', 2, N'5,764', N'6,457', N'6,745', N'7,645', 14),
(15, N'32 + 19 = 51 và 32 - 19 = 13 nên số nhỏ nhất là 19', 4, N'16', N'32', N'35', N'19', 15);

-- Hard
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(16, N'Vì có 8 khoảng cách nên khoảng cách giữa khoảng đầu tiên và khoảng cuối cùng là 8 x 3 = 24 feet', 2, N'24 feet', N'27 feet', N'270 feet', N'240 feet', 16),
(17, N'Ken kiếm được nhiều tiền hơn trong một tuần so với Carl vì anh ấy làm việc nhiều giờ hơn. Ken làm việc 8 giờ một tuần và kiếm được 6 đô la một giờ, vì vậy tổng thu nhập mỗi tuần của anh ấy sẽ là 8 x 6 đô la = 48 đô la. Mặt khác, Carl làm việc 4 giờ một tuần và kiếm được 8 đô la một giờ, 
vì vậy tổng thu nhập mỗi tuần của anh ấy sẽ là 4 x 8 đô la = 32 đô la. Vì vậy, Ken kiếm được nhiều tiền hơn Carl trong một tuần.', 1, N'Ken', N'Carl', N'Họ làm việc như nhau', N'Không đáp án nào đúng', 17),
(18, N'12 nhân 15 bằng 180. Điều này có thể được tính bằng cách nhân 12 với 15, cho ta kết quả là 180', 2, N'150', N'180', N'125', N'165', 18);


---- Easy
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(19, N' Văn Lang (chữ Hán: 文郎) được coi là quốc hiệu đầu tiên của Việt Nam', 2, N'Đại Việt', N'Văn Lang', N'Âu Lạc', N'Đinh Bộ Lĩnh', 19),
(20, N'Trần Quốc Tuấn đã theo kế của Ngô Quyền, muốn ngăn quân địch tiến vào bằng cọc nhọn ở Bạch Đằng', 1, N'Ngô Quyền', N'Nguyễn Huệ', N'Lê Lợi', N'Trần Hưng Đạo', 20),
(21, N'Đinh Bộ Lĩnh quyết định tiếnđánh các sứ quân. Bằng các biện pháp chính trị mềm dẻo - liên kết, hàng phục kết hợp với biện pháp quân sự cứng rắn - chinh phạt, 
Đinh Bộ Lĩnh đã lần lượt dẹp yên các sứ quân, chấm dứt cuộc "nội loạn" ở giữa thế kỷ X, thu non sông về một mối vào cuối năm 967', 4, N'Lý Thái Tổ', N'Lý Công Uẩn', N'Lê Hoàn', N'Đinh Bộ Lĩnh', 21);

-- Medium
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(22, N'Ông có đến 103 người vợ nhưng lại không có một người con ruột nào. 
Nguyên nhân là bởi từ nhỏ Tự Đức đã bị bệnh đậu mùa, ốm đau triền miên nên sức khỏe không tốt.', 3, N'Hàm Nghi', N'Lê Thần Tông', N'Tự Đức', N'Lạc Long Quân', 22),
(23, N'Hai Bà lập nên vương triều mới. Trưng Trắc xưng hiệu là Trưng Vương. Hai Bà đã thắp lên ngọn lửa chống ngoại xâm của dân tộc Việt Nam.', 2, N'Âu Cơ', N'Hai Bà Trưng', N'Lê Chân', N'Lê Thị Hoa', 23),
(24, N'Thành tựu đặc sắc về quốc phòng của người dân Âu Lạc là: - Có nhiều tướng tài như Cao Lỗ, quân đội được tổ chức, trang bị tinh nhuệ. 
- Kĩ thuật chế tạo ra nỏ bắn một lần được nhiều mũi tên. - Xây dựng thành Cổ Loa kiên cố, theo kiến trúc hình xoáy ốc.', 3, N'Thành Cổ Loa', N'Nền Văn Hóa Đông Sơn', N'Cả 2 ý trên', N'Không có ý nào đúng', 24);

-- Hard
INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(25, N'Hai Bà Trưng và cuộc khởi nghĩa của Hai Bà (năm 40-43 sau Công nguyên) đã đi vào lịch sử chống giặc ngoại xâm của dân tộc', 1, N'40 - 43 SCN', N'41 - 44 TCN', N'42 - 45 SCN', N'43 - 46 TCN', 25),
(26, N'năm 938 là một trận đánh giữa quân dân Tĩnh Hải quân (vào thời đó, Việt Nam chưa có quốc hiệu chính thức) do Ngô Quyền lãnh đạo đánh với quân Nam Hán trên sông Bạch Đằng', 3, N'Lý Phật Tử', N'Lê Hoàn', N'Ngô Quyền', N'Lý Công Uẩn', 26),
(27, N'Dương Thái hậu đã sai người lấy áo long cổn khoác lên cho ông, Lê Hoàn lên ngôi (tức vua Lê Đại Hành), 
nhà Tiền Lê thành lập. Ông đặt niên hiệu là Thiên Phúc, quốc hiệu vẫn là Đại Cồ Việt, đóng đô tại Hoa Lư', 4, N'Lý Phật Tử', N'Lê Hoàn', N'Thái Tổ', N'Lê Đại Hành', 27);


INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(28, N'"Tác phẩm quốc dân" là cách mà người ta gọi truyện Kiều của Nguyễn Du. Điều này không chỉ về tầm ảnh hưởng văn học mà còn liên quan đến vai trò lịch sử và văn hóa mà tác phẩm này đã đóng góp cho xã hội Việt Nam. 
Truyện Kiều không chỉ là một tác phẩm văn học lớn mà còn được coi là biểu tượng của nền văn hóa, tri thức Việt Nam.', 1, N'Truyện Kiều', N'Văn chiêu hồn', N'Văn tế Trường Lưu nhị nữ', N'Nam trung tạp ngâm', 28),
(29, N'"Xin đừng làm mẹ khóc" là những câu chuyện giản dị mà đầy ý nghĩa về tình mẹ con thiêng liêng, cao quý.', 3, N'Chí Phèo', N'Lão Hạc', N'Xin Đừng Làm Mẹ Khóc', N'Những Cánh Hoa Tàn', 29),
(30, N'Số đỏ là một tiểu thuyết văn học của nhà văn Vũ Trọng Phụng (ông vua phóng sự đất Bắc) đăng ở Hà Nội báo từ số 40 ngày 7 tháng 10 năm 1936 và được in thành sách lần đầu vào năm 1938.', 4, N'Nguyễn Hồng', N'Nam Cao', N'Đoàn Giỏi', N'Vũ Trọng Phụng', 30);

INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(31, N'Nguyên nhân sâu xa khiến lão Hạc phải lựa chọn cái chết cũng vì thương con và không muốn tiêu số tiền đã dành cho con', 3, N'Lão Hạc ăn phải bả chó', N'Lão Hạc ân hận vì chót lừa cậu Vàng', N'Lão Hạc rất thương con', N'Lão Hạc không muốn làm liên lụy đến mọi người', 31),
(32, N'Mang giá trị châm biếm sâu sắc', 1, N'Mang giá trị châm biếm sâu sắc', N'Là đoạn trích có kịch tính rất cao', N'Thể hiện tài năng xây dựng nhân vật của Ngô Tất Tố', N'Có giá trị nhân đạo và hiện thực lớn', 32),
(33, N'Chị Dậu -là nhân vật chính trong tác phẩm. Tuy nhiên, tên thật của của chị Dậu là Lê Thị Đào. Lê Thị Đào lấy chồng tên Nguyễn Văn Dậu nên người ta gọi là chị Dậu.', 4, N'Chị Dậu', N'Nguyễn Thị Dậu', N'Lê Thị Mây', N'Lê Thị Đào', 33);

INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(34, N'Dế Mèn phiêu lưu ký là tác phẩm văn xuôi đặc sắc và nổi tiếng nhất của nhà văn Tô Hoài viết về loài vật, dành cho lứa tuổi thiếu nhi', 3, N'Đất rừng phương Nam', N'Quê ngoại', N'Dế Mèn phiêu lưu kí', N'Tuyển tập Tô Hoài', 34),
(35, N'Tản Đà tên thật là Nguyễn Khắc Hiếu, bút danh được ghép giữa núi Tản Viên và sông Đà', 2, N'Tản Viên', N'Nguyễn Khắc Hiếu', N'Đà Viên', N'Lê Trí Nhân', 35),
(36, N'“Chiếu dời đô” của Lý Công Uẩn', 3, N'Hà Nội 36 phố phường', N'Chuyện cũ Hà Nội', N'Chiếu dời đô', N'Thành Hoa Lưu', 36);



INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(37, N'Việt Nam có diện tích rộng khoảng 331.698km2, 
xếp thứ 4 khu vực Đông Nam Á và đứng thứ 66 trên thế giới. Phần diện tích đất liền khoảng 327.480km', 3, N'331.211 Km2', N'331.213 Km2', N'327.480km2', N'331.212 Km2', 37),
(38, N'Điểm cực Bắc của Việt Nam thuộc khu vực xã Lũng Cú, tỉnh Hà Giang', 2, N'Điện biên', N'Hà Giang', N'Cà Mau', N'Khánh Hòa', 38),
(39, N'Cực Tây Việt Nam nằm tại A Pa Chải, xã Sín Thầu, huyện Mường Nhé, tỉnh Điện Biên', 1, N'Điện biên', N'Hà Giang', N'Cà Mau', N'Khánh Hòa', 39);

INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(40, N'Tài nguyên giữ vị trí quan trọng nhất của Việt Nam hiện nay là tài nguyên đất.
Vì nước ta là một nước nông nghiệp, con người đã gắn bó với đất đai từ nghàn đời nay', 1, N'Tài nguyên đất', N'Tài nguyên sinh vật', N'Tài nguyên nước', N'Tài nguyên khoáng sản', 40),
(41, N'Nạn lâm tặc, khai thác rừng bừa bãi, Cháy rừng, Các hoạt động xây dựng cơ sở hạ tầng, các công trình cơ bản, phát triển thủy điện..', 4, N'Độ che phủ rừng giảm', N'Diện tích đồi núi trọc tăng lên', N'Mất dần nhiều động thực vật quý hiếm', N'Diện tích rừng suy giảm, chất lượng rừng suy thoái', 41),
(42, N'Tỷ lệ tăng dân số tự nhiên là mức chênh lệch giữa số sinh và số chết so với dân số trong thời kỳ nghiên cứu', 2, N'Hiệu số của người nhập cư và người xuất cư', N'Hiệu số của tỉ suất sinh và tỉ suất tử', N'Tỷ lệ cao', N'Tuổi thọ trung bình cao', 42);

INSERT INTO [dbo].[Answers] (AnswerID, AnswerDetail, IsAnswer, option1, option2, option3, option4, QuestionID) 
VALUES 
(43, N'Đồng bằng sông Cửu Long', 4, N'Đồng bằng sông Hồng', N'Đông Nam Bộ', N'Tây Nguyên', N'Đồng bằng sông Cửu Long', 43),
(44, N'Ở cấp cơ sở, các cơ sở kinh tế, nhất là trong công nghiệp và giao thông vận tải, 
trong khi tìm cách giải quyết khó khăn cho cơ sở mình đã tìm 9,5 triệu ha liên kết với các cơ sở bạn để tìm nguyên liệu và tìm cách tiêu thụ đầu ra', 1, N'Xây dựng nền kinh tế tự chủ', N'Cơ cấu điểu chỉnh phù hợp nguồn lực', N'Cơ cấu lãnh thổ có sự chuyển biến', N'Đẩy lùi được nạn đói', 44),
(45, N'Năm 2020, Việt Nam có 14.677.215 ha đất có rừng', 3, N'14 triệu ha', N'10 triệu ha', N'9 triệu ha', N'9,5 triệu ha', 45);


---------------------------------------------------------------------------------------- AnswerQuestion -----------------------------------------------------------


SET IDENTITY_INSERT [dbo].[Answers] OFF;
Go
SET IDENTITY_INSERT [dbo].[Quizzes] ON 

INSERT [dbo].[Quizzes] ([QuizID], [title], [Image], [description], [Level], [SubjectID], [CategoryID], [created_by], [created_at], [duration], [type], [Status]) 
VALUES (1, N'Test quiz', 'https://viralsolutions.net/wp-content/uploads/2019/06/shutterstock_749036344.jpg', N'tesst', 2, 2, 3, 8, CAST(N'2024-07-26T12:49:25.000' AS DateTime), NULL, 1, 1)
INSERT [dbo].[Quizzes] ([QuizID], [title], [Image], [description], [Level], [SubjectID], [CategoryID], [created_by], [created_at], [duration], [type], [Status]) 
VALUES (2, N'level test', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaHk1LBkHGGvGKXcp0BDycELDj89Mv_d5LTBCGd8RVE2Ntk8uzgVO7CGNMFMRgQujG_XQ&usqp=CAU', N'tesst', 2, 2, 3, 8, CAST(N'2024-07-26T12:49:25.000' AS DateTime), NULL, 1, 1)
INSERT [dbo].[Quizzes] ([QuizID], [title], [Image], [description], [Level], [SubjectID], [CategoryID], [created_by], [created_at], [duration], [type], [Status]) 
VALUES (3, N'Test update title', 'https://img.freepik.com/premium-photo/quiz-quizz-word-inscription-test-education-concept_361816-4203.jpg', N'tesst', 2, 2, 3, 1, CAST(N'2024-07-26T12:49:25.000' AS DateTime), NULL, 1, 1)
---------------------------- Exam  --------------------------------------------------------
INSERT INTO [dbo].[Quizzes] (QuizID, title, [Image], Description, Level, SubjectID, CategoryID, created_by, Created_at, [duration], [type], [Status]) 
VALUES (4, N'Science Quiz', 'quiz1.png', N'This is a science quiz.', 1,  1, 1, 8, '2024-05-12', 19, 2, 1);
INSERT INTO [dbo].[Quizzes] (QuizID, title,[Image], Description, Level, SubjectID, CategoryID, created_by, Created_at, [duration], [type], [Status]) 
VALUES (5, N'Math Quiz', 'quiz2.png', N'This is a math quiz.',3,  2, 2, 8, '2024-05-13',19 , 2, 1);
INSERT INTO [dbo].[Quizzes] (QuizID, title, [Image], Description, Level, SubjectID, CategoryID,created_by, Created_at, [duration], [type], [Status]) 
VALUES (6, N'History Quiz', 'quiz3.png', N'This is a history quiz.',2, 3, 3, 8, '2024-05-14', 19 , 2, 1);
INSERT INTO [dbo].[Quizzes] (QuizID, title, [Image], Description, Level, SubjectID, CategoryID, created_by, Created_at, [duration], [type], [Status]) 
VALUES (7, N'Literature Quiz', 'quiz4.png', N'This is a literature quiz.',2, 4, 4, 8, '2024-05-15', 19, 2, 1);
INSERT INTO [dbo].[Quizzes] (QuizID, title, [Image], Description, Level, SubjectID, CategoryID, created_by, Created_at, [duration], [type], [Status]) 
VALUES (8, N'Geography Quiz', 'quiz5.png', N'This is a geography quiz.',1, 5, 5, 8, '2024-05-16', 19 , 2, 1);

SET IDENTITY_INSERT [dbo].[Quizzes] OFF
GO
SET IDENTITY_INSERT [dbo].[RatingReactions] ON 

INSERT [dbo].[RatingReactions] ([ReactionID], [UserID], [RatingID], [Status]) VALUES (1, 1, 1, 1)
INSERT [dbo].[RatingReactions] ([ReactionID], [UserID], [RatingID], [Status]) VALUES (2, 2, 2, 1)
INSERT [dbo].[RatingReactions] ([ReactionID], [UserID], [RatingID], [Status]) VALUES (3, 3, 2, 1)
INSERT [dbo].[RatingReactions] ([ReactionID], [UserID], [RatingID], [Status]) VALUES (4, 4, 1, 0)
SET IDENTITY_INSERT [dbo].[RatingReactions] OFF
GO
SET IDENTITY_INSERT [dbo].[Ratings] ON 

INSERT [dbo].[Ratings] ([RatingID], [UserID], [LesMoocID], [rating], [comment], [created_at], [Like], [IsReply], [Status]) VALUES (1, 1, 1, 4, N'Great subject!', CAST(N'2024-07-26T10:33:43.667' AS DateTime), 1, 1, 1)
INSERT [dbo].[Ratings] ([RatingID], [UserID], [LesMoocID], [rating], [comment], [created_at], [Like], [IsReply], [Status]) VALUES (2, 2, 1, 5, N'Excellent lesson!', CAST(N'2024-07-26T10:33:43.667' AS DateTime), 1, 1, 1)
INSERT [dbo].[Ratings] ([RatingID], [UserID], [LesMoocID], [rating], [comment], [created_at], [Like], [IsReply], [Status]) VALUES (3, 2, 2, 3, N'Good quiz, but could be improved', CAST(N'2024-07-26T10:33:43.667' AS DateTime), 1, 1, 1)
INSERT [dbo].[Ratings] ([RatingID], [UserID], [LesMoocID], [rating], [comment], [created_at], [Like], [IsReply], [Status]) VALUES (4, 1, 2, 2, N'Not very informative', CAST(N'2024-07-26T10:33:43.667' AS DateTime), 1, 0, 0)
SET IDENTITY_INSERT [dbo].[Ratings] OFF
GO
SET IDENTITY_INSERT [dbo].[Registrations] ON 

INSERT [dbo].[Registrations] ([RegisterID], [UserID], [SubjectID], [PackageID], [total_cost], [status], [valid_from], [valid_to], [created_at]) VALUES (1, 1, 1, 1, CAST(0.00 AS Decimal(10, 2)), 2, CAST(N'2024-05-25' AS Date), CAST(N'2024-05-31' AS Date), CAST(N'2024-05-25T00:00:00.000' AS DateTime))
INSERT [dbo].[Registrations] ([RegisterID], [UserID], [SubjectID], [PackageID], [total_cost], [status], [valid_from], [valid_to], [created_at]) VALUES (2, 2, 2, 2, CAST(200.00 AS Decimal(10, 2)), 2, CAST(N'2024-05-25' AS Date), CAST(N'2024-07-25' AS Date), CAST(N'2024-05-25T00:00:00.000' AS DateTime))
INSERT [dbo].[Registrations] ([RegisterID], [UserID], [SubjectID], [PackageID], [total_cost], [status], [valid_from], [valid_to], [created_at]) VALUES (3, 2, 1, 3, CAST(350.00 AS Decimal(10, 2)), 1, CAST(N'2024-05-20' AS Date), CAST(N'2024-08-20' AS Date), CAST(N'2024-05-20T00:00:00.000' AS DateTime))
INSERT [dbo].[Registrations] ([RegisterID], [UserID], [SubjectID], [PackageID], [total_cost], [status], [valid_from], [valid_to], [created_at]) VALUES (4, 1, 2, 4, CAST(800.00 AS Decimal(10, 2)), 1, CAST(N'2024-05-25' AS Date), CAST(N'2025-05-25' AS Date), CAST(N'2024-05-25T00:00:00.000' AS DateTime))
SET IDENTITY_INSERT [dbo].[Registrations] OFF
GO
SET IDENTITY_INSERT [dbo].[Roles] ON 

INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (1, N'Admin', N'The organization leader/manager, who acts as the system administrator')
INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (2, N'Expert', N'Experts who access and prepare the course/test contents as assigned by admin')
INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (3, N'Sale', N'Sale members of the organization')
INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (4, N'Marketing', N'Makerting members of the organization')
INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (5, N'Customer', N'Registered users who are actual or potential customers')
INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (6, N'Guest', N'Unregistered users')
INSERT [dbo].[Roles] ([RoleID], [role_name], [description]) VALUES (7, N'Teacher', N'')
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[Subjects] ON 

INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (1, N'Physics', N'Physics basics', N'physics.png', 1, 1, 1, 3, CAST(N'2024-05-14' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (2, N'Calculus', N'Advanced calculus.', N'calculus.png', 1, 2, 2, 3, CAST(N'2024-05-15' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (3, N'History VN', N'History basics', N'historyvn.png', 1, 1, 3, 3, CAST(N'2024-05-14' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (4, N'Literature VN', N'Advanced Literature', N'literaturevn.png', 0, 2, 4, 3, CAST(N'2024-05-15' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (5, N'Geography VN', N'Geography basics', N'geopraphy.png', 0, 1, 5, 3, CAST(N'2024-05-14' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (6, N'MAE101', N'Mathematics for Engineering', N'mae101.png', 1, 3, 2, 3, CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (7, N'PRF192', N'Programming Fundamentals', N'prf192.png', 1, 3, 3, 3, CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (8, N'CEA201', N'Computer Architecture', N'cea201.png', 1, 3, 3, 3, CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (9, N'CSI104', N'Introduction to computing', N'csi104.png', 1, 3, 3, 3, CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Subjects] ([SubjectID], [Subject_Name], [Description], [Image], [Status], [PackageId], [CategoryId], [created_by], [Created_at]) VALUES (10, N'SSL101c', N'Academic Skills for University Success', N'ssl101c.png', 1, 3, 3, 3, CAST(N'2024-05-20' AS Date))
SET IDENTITY_INSERT [dbo].[Subjects] OFF
GO
SET IDENTITY_INSERT [dbo].[Teacher] ON 

INSERT [dbo].[Teacher] ([TeacherID], [UserID], [Position], [Department], [Qualifications], [DateWork], [About], [Slogan]) VALUES (1, 8, N'Senior Lecturer', N'Mathematics', N'PhD in Mathematics', CAST(N'2011-09-01' AS Date), N'Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum voluptatem.', N'We are proud of child student. We teaching great activities and best program for your kids.')
INSERT [dbo].[Teacher] ([TeacherID], [UserID], [Position], [Department], [Qualifications], [DateWork], [About], [Slogan]) VALUES (2, 9, N'Assistant Professor', N'Physics', N'PhD in Physics', CAST(N'2012-05-15' AS Date), N'Dedicated, resourceful and goal-driven professional educator with a solid commitment to the social and academic growth and development of every child. An accommodating and versatile individual with the talent to develop inspiring hands-on lessons that will capture a child imagination and breed success. Highly motivated, enthusiastic and dedicated educator who wants all children to be successful learners', N'I want to convey and teach you professional knowledge, so that you can have a clearer and deeper understanding.')
INSERT [dbo].[Teacher] ([TeacherID], [UserID], [Position], [Department], [Qualifications], [DateWork], [About], [Slogan]) VALUES (3, 10, N'Professor', N'Chemistry', N'PhD in Chemistry', CAST(N'2009-01-10' AS Date), N'Committed to creating a classroom atmosphere that is stimulating and encouraging to students. Aptitude to remain flexible, ensuring that every child learning styles and abilities are addressed. Superior interpersonal and communication skills to foster meaningful relationships with students, staff and parents. Demonstrated ability to consistently individualize instruction, based on student needs and interests. Exceptional ability to establish cooperative, professional relationships with parents, staff and administration. Professional Educator with diverse experience and strong track record fostering child-centered curriculum and student creativity. Warm and caring teacher who wants all children to be successful learners and works to create a classroom atmosphere that is stimulating, encouraging, and adaptive to the varied needs of students', N'Knowledge in the head and virtue in the heart, time devoted to study or business, instead of show and pleasure, are the way to be useful and consequently happy.')
INSERT [dbo].[Teacher] ([TeacherID], [UserID], [Position], [Department], [Qualifications], [DateWork], [About], [Slogan]) VALUES (4, 11, N'Lecturer', N'History', N'MA in History', CAST(N'2015-03-23' AS Date), N'Committed to professional ethics, standards of practice and the care and education of young children. A self-directed, action-oriented professional with over 10 years experience in education and community service. Proven abilities in problem solving, people management and motivation. A self-starter with high energy enabling maximum and efficient work under pressure. Accustomed to working in a multicultural environment that emphasizes inclusion
Experienced in developing curriculum as well as conducting teach training and parenting programs. Introduces concepts into curriculum related to life and social skills', N'A man is truly ethical only when he obeys the compulsion to help all life which he is able to assist, and shrinks from injuring anything that lives.')
INSERT [dbo].[Teacher] ([TeacherID], [UserID], [Position], [Department], [Qualifications], [DateWork], [About], [Slogan]) VALUES (5, 12, N'Senior Professor', N'Computer Science', N'PhD in Computer Science', CAST(N'2008-11-18' AS Date), N'Thoroughly enjoys working with children and encourages creative expression. Talented teacher of grades K-1, committed to maintaining high standards of education with emphasis on developing readings skills in pupils. Enthusiastic, committed educator with innate ability to understand and motivate children. Strive to build student self-esteem and encourage understanding of cultural diversity, gender differences and physical limitations. Create a cooperative community in the classroom; model for students the importance of mutual respect and cooperation among all community members. Skilled in adapting to students diverse learning styles. Experienced in one-on-one tutoring and group instruction of ESL', N'Knowledge is very important, so we should always try to improve and learn more.')
SET IDENTITY_INSERT [dbo].[Teacher] OFF
GO
SET IDENTITY_INSERT [dbo].[User_Answers] ON 
INSERT [dbo].[User_Answers] ([USAnswerID], [UserID], [QuestionID], [AnswerID], [answered_at]) VALUES (1, 2, 50, 5, CAST(N'2024-07-24T04:39:50.000' AS DateTime))
INSERT [dbo].[User_Answers] ([USAnswerID], [UserID], [QuestionID], [AnswerID], [answered_at]) VALUES (2, 2, 52, 10, CAST(N'2024-07-26T12:58:28.000' AS DateTime))
INSERT [dbo].[User_Answers] ([USAnswerID], [UserID], [QuestionID], [AnswerID], [answered_at]) VALUES (3, 2, 52, 10, CAST(N'2024-07-26T13:47:07.000' AS DateTime))
SET IDENTITY_INSERT [dbo].[User_Answers] OFF
GO
SET IDENTITY_INSERT [dbo].[User_Quiz_Results] ON;

INSERT [dbo].[User_Quiz_Results] ([ResultID], [UserID], [QuizID], [score], [completed_at], [status]) 
VALUES 
(1, 1, 4, 80, '2024-05-12 08:30:00', NULL),
(2, 2, 5, 90, '2024-05-13 13:35:00', NULL),
(3, 2, 6, 75, '2024-05-14 09:00:00', NULL),
(4, 1, 7, 100, '2024-05-15 10:30:00', NULL),
(5, 1, 8, 95, '2024-05-16 12:30:00', NULL),
(6, 2, 3, 10, '2024-07-26 12:58:28', NULL),
(7, 2, 3, 10, '2024-07-26 13:47:07', NULL);

SET IDENTITY_INSERT [dbo].[User_Quiz_Results] OFF;

GO
INSERT [dbo].[UserReaction] ([UserID], [RatingID], [isLike]) VALUES (1, 1, 1)
INSERT [dbo].[UserReaction] ([UserID], [RatingID], [isLike]) VALUES (2, 2, 1)
INSERT [dbo].[UserReaction] ([UserID], [RatingID], [isLike]) VALUES (3, 2, 1)
INSERT [dbo].[UserReaction] ([UserID], [RatingID], [isLike]) VALUES (4, 1, 0)
GO

SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (1, N'John Doe', N'johndoe', CAST(N'1985-06-15' AS Date), N'john.doe@example.com', N'password123', N'1234567890', N'123 Main St', 1, 7, N'avatar1.png', CAST(N'2024-05-10' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (2, N'Jane Smith', N'janesmith', CAST(N'1990-09-21' AS Date), N'jane.smith@example.com', N'password456', N'0987654321', N'456 Elm St', 0, 2, N'avatar2.png', CAST(N'2024-05-11' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (3, N'Pham Xuan Chinh', N'phamchinh', CAST(N'2003-12-18' AS Date), N'chinhpxhe171177@fpt.edu.vn', N'123456', N'0339033192', N'Nam Dinh', 1, 1, N'avatar3.png', CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (4, N'Nguyen Minh Quan', N'nguyenquan', CAST(N'2003-01-01' AS Date), N'quannmhe170463@fpt.edu.vn', N'123@', N'0967186856', N'Ha Noi', 1, 1, N'avatar4.png', CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (5, N'Pham Thanh Trung', N'phamtrung', CAST(N'2003-02-02' AS Date), N'trungpthe170431@fpt.edu.vn', N'123321', N'0989521275', N'Ha Noi', 1, 1, N'avatar5.png', CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (6, N'Nguyen Anh Duc', N'nguyenduc', CAST(N'2003-03-03' AS Date), N'ducnahe170599@fpt.edu.vn', N'666888', N'0865904356', N'Ha Noi', 1, 1, N'avatar6.png', CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (7, N'Tran Huy Minh', N'tranminh', CAST(N'2003-04-04' AS Date), N'minhthhe171134@fpt.edu.vn', N'171134', N'0373350955', N'Ha Noi', 1, 1, N'avatar7.png', CAST(N'2024-05-20' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (8, N'Alice Smith', N'asmith', CAST(N'1985-03-22' AS Date), N'asmith@example.com', N'password1', N'1112223333', N'456 Oak St', 0, 7, N'avatar8.jpg', CAST(N'2024-07-26' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (9, N'Bob Johnson', N'bjohnson', CAST(N'1975-07-11' AS Date), N'bjohnson@example.com', N'password2', N'2223334444', N'789 Pine St', 1, 7, N'avatar9.jpg', CAST(N'2024-07-26' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (10, N'Carol White', N'cwhite', CAST(N'1990-11-30' AS Date), N'cwhite@example.com', N'password3', N'3334445555', N'101 Maple St', 0, 7, N'avatar10.jpg', CAST(N'2024-07-26' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (11, N'David Brown', N'dbrown', CAST(N'1983-02-14' AS Date), N'dbrown@example.com', N'password4', N'4445556666', N'202 Birch St', 1, 7, N'avatar11.jpg', CAST(N'2024-07-26' AS Date))
INSERT [dbo].[Users] ([UserID], [FullName], [UserName], [DateOfBirth], [Email], [Password], [Phone], [Address], [Gender], [RoleID], [Avatar], [Create_at]) VALUES (12, N'Eva Green', N'egreen', CAST(N'1978-08-25' AS Date), N'egreen@example.com', N'password5', N'5556667777', N'303 Cedar St', 0, 7, N'avatar12.jpg', CAST(N'2024-07-26' AS Date))
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Roles__783254B12F1B570B]    Script Date: 7/26/2024 1:58:24 PM ******/
ALTER TABLE [dbo].[Roles] ADD UNIQUE NONCLUSTERED 
(
	[role_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Progress] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Ratings] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[AnswerQuestion]  WITH CHECK ADD  CONSTRAINT [FK_AnswerQuestion_Questions] FOREIGN KEY([QuestionID])
REFERENCES [dbo].[Questions] ([QuestionID])
GO
ALTER TABLE [dbo].[AnswerQuestion] CHECK CONSTRAINT [FK_AnswerQuestion_Questions]
GO
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD FOREIGN KEY([QuestionID])
REFERENCES [dbo].[Questions] ([QuestionID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[LesMooc]  WITH CHECK ADD FOREIGN KEY([created_by])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[LesMooc]  WITH CHECK ADD FOREIGN KEY([MoocID])
REFERENCES [dbo].[Mooc] ([MoocID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Lessons]  WITH CHECK ADD FOREIGN KEY([created_by])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Lessons]  WITH CHECK ADD FOREIGN KEY([SubjectID])
REFERENCES [dbo].[Subjects] ([SubjectID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Mooc]  WITH CHECK ADD FOREIGN KEY([SubjectID])
REFERENCES [dbo].[Subjects] ([SubjectID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD FOREIGN KEY([QuizID])
REFERENCES [dbo].[Quizzes] ([QuizID])
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD FOREIGN KEY([SubjectID])
REFERENCES [dbo].[Subjects] ([SubjectID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Questions]  WITH CHECK ADD FOREIGN KEY([QuizID])
REFERENCES [dbo].[Quizzes] ([QuizID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Quizzes]  WITH CHECK ADD FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Categories] ([CategoryID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Quizzes]  WITH CHECK ADD FOREIGN KEY([created_by])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Quizzes]  WITH CHECK ADD FOREIGN KEY([SubjectID])
REFERENCES [dbo].[Subjects] ([SubjectID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[RatingReactions]  WITH CHECK ADD FOREIGN KEY([RatingID])
REFERENCES [dbo].[Ratings] ([RatingID])
GO
ALTER TABLE [dbo].[RatingReactions]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[RatingReactions]  WITH CHECK ADD  CONSTRAINT [FK_RatingReactions_Ratings] FOREIGN KEY([RatingID])
REFERENCES [dbo].[Ratings] ([RatingID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[RatingReactions] CHECK CONSTRAINT [FK_RatingReactions_Ratings]
GO
ALTER TABLE [dbo].[Ratings]  WITH CHECK ADD FOREIGN KEY([LesMoocID])
REFERENCES [dbo].[LesMooc] ([LesMoocID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ratings]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Registrations]  WITH CHECK ADD FOREIGN KEY([PackageID])
REFERENCES [dbo].[Packages] ([PackageID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Registrations]  WITH CHECK ADD FOREIGN KEY([SubjectID])
REFERENCES [dbo].[Subjects] ([SubjectID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Registrations]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Reply]  WITH CHECK ADD FOREIGN KEY([RatingID])
REFERENCES [dbo].[Ratings] ([RatingID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Reply]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Subjects]  WITH CHECK ADD FOREIGN KEY([created_by])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[Teacher]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[User_Answers]  WITH CHECK ADD FOREIGN KEY([AnswerID])
REFERENCES [dbo].[AnswerQuestion] ([AnswerID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[User_Answers]  WITH CHECK ADD FOREIGN KEY([QuestionID])
REFERENCES [dbo].[Questions] ([QuestionID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[User_Quiz_Results]  WITH CHECK ADD FOREIGN KEY([QuizID])
REFERENCES [dbo].[Quizzes] ([QuizID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[User_Quiz_Results]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[UserQuizChoices]  WITH CHECK ADD FOREIGN KEY([questionId])
REFERENCES [dbo].[Questions] ([QuestionID])
GO
ALTER TABLE [dbo].[UserQuizChoices]  WITH CHECK ADD FOREIGN KEY([QuizID])
REFERENCES [dbo].[Quizzes] ([QuizID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[UserQuizChoices]  WITH CHECK ADD FOREIGN KEY([resultID])
REFERENCES [dbo].[User_Quiz_Results] ([ResultID])
GO
ALTER TABLE [dbo].[UserReaction]  WITH CHECK ADD FOREIGN KEY([RatingID])
REFERENCES [dbo].[Ratings] ([RatingID])
GO
ALTER TABLE [dbo].[UserReaction]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD FOREIGN KEY([RoleID])
REFERENCES [dbo].[Roles] ([RoleID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[LesMooc]  WITH CHECK ADD CHECK  (([status]='Inactive' OR [status]='Active'))
GO
ALTER TABLE [dbo].[Lessons]  WITH CHECK ADD CHECK  (([status]='Inactive' OR [status]='Active'))
GO
ALTER TABLE [dbo].[Packages]  WITH CHECK ADD CHECK  (([status]='Inactive' OR [status]='Active'))
GO
ALTER TABLE [dbo].[Progress]  WITH CHECK ADD CHECK  (([State]=(2) OR [State]=(1)))
GO
ALTER TABLE [dbo].[Quizzes]  WITH CHECK ADD CHECK  (([Level]>=(1) AND [Level]<=(3)))
GO
ALTER TABLE [dbo].[Ratings]  WITH CHECK ADD CHECK  (([rating]>=(1) AND [rating]<=(5)))
GO
ALTER TABLE [dbo].[Registrations]  WITH CHECK ADD CHECK  (([status]>=(1) AND [status]<=(3)))
GO

