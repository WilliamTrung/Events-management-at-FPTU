--DROP TABLE
DROP TABLE tblHistoryLogs;
DROP TABLE tblFriendInvitation;

DROP TABLE tblEventInvitation;
DROP TABLE tblUsers;
DROP TABLE tblStatusUser;
DROP TABLE tblRoles;
DROP TABLE tblEvents;
DROP TABLE tblStatusEvent;
DROP TABLE tblStatusFI;
DROP TABLE tblStatusI;


--CREATE TABLE
CREATE TABLE tblRoles(
	roleId NVARCHAR(5) PRIMARY KEY,
	roleName NVARCHAR(50) NOT NULL
);
CREATE TABLE tblStatusUser(
	statusId NVARCHAR(5) PRIMARY KEY,
	statusName NVARCHAR(50) NOT NULL
);
CREATE TABLE tblUsers(
	userId NVARCHAR(50)  PRIMARY KEY,
	email NVARCHAR(50) NOT NULL UNIQUE,
	username NVARCHAR(50) NOT NULL,
	statusId NVARCHAR(5) REFERENCES tblStatusUser,
	roleId NVARCHAR(5) REFERENCES tblRoles
);
CREATE TABLE tblStatusEvent(
	statusId NVARCHAR(5) PRIMARY KEY,
	statusName NVARCHAR(50) NOT NULL
);

CREATE TABLE tblEvents(
	eventId INT IDENTITY(0,1) PRIMARY KEY,
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	title NVARCHAR(50) NOT NULL,
	description NVARCHAR(130) NOT NULL,
	locationId INT REFERENCES tblLocations NOT NULL,
	createDatetime DATETIME NOT NULL,
	startDatetime DATETIME NOT NULL,
	endDatetime DATETIME NOT NULL,
	statusId NVARCHAR(5) REFERENCES tblStatusEvent,
	picture NVARCHAR(100) NOT NULL
);

CREATE TABLE tblParticipants(
	eventId INT REFERENCES tblEvents NOT NULL,
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	seatNumber INT NOT NULL,
	
	PRIMARY KEY(eventId, userId)
)
CREATE TABLE tblLocations(
	locationId NVARCHAR(5) PRIMARY KEY,
	seat INT NOT NULL,--maximum seats can be took
	locationName NVARCHAR(100) NOT NULL,
)
--demo untested
CREATE TABLE tblContents(
	contentId INT IDENTITY(0,1) PRIMARY KEY,
	content NVARCHAR(300),
	video NVARCHAR(100),
	createDate DATETIME NOT NULL,
)
CREATE TABLE tblComments(
	commentId INT IDENTITY(0,1) PRIMARY KEY,
	eventId INT REFERENCES tblEvents,
	contentId INT REFERENCES tblContents,
	commentDate DATETIME NOT NULL,
	content NVARCHAR(100) NOT NULL,
	--replyTo should have the value of commentId
	--replyTo nullable because user does not reply anytime
	replyTo INT,
)
CREATE TABLE tblFollowing(
	userId NVARCHAR(50) REFERENCES tblUsers,
	eventId INT REFERENCES tblEvents,
	follow BIT NOT NULL,
	PRIMARY KEY (userId,eventId),
)
CREATE TABLE tblVotes(
	userId NVARCHAR(50) REFERENCES tblUsers,
	eventId INT REFERENCES tblEvents,
	vote BIT NOT NULL,
	PRIMARY KEY (userId,eventId),
)
CREATE TABLE tblChats(
	charId INT IDENTITY(0,1) PRIMARY KEY,
	userId NVARCHAR(50) REFERENCES tblUsers,
)
CREATE TABLE tblChatTo(
	chatId INT REFERENCES tblChats,
	userId NVARCHAR(50) REFERENCES tblUsers,
)
CREATE TABLE tblMessages(
	chatId INT REFERENCES tblChats,
	time DATETIME PRIMARY KEY,


)

--INSERT DATA
--basic
INSERT INTO tblRoles(roleId, roleName) VALUES ('US','Basic User')
INSERT INTO tblRoles(roleId, roleName) VALUES ('EM','Event Manager')
INSERT INTO tblRoles(roleId, roleName) VALUES ('MT','Mentor/Lecturer')
INSERT INTO tblRoles(roleId, roleName) VALUES ('AD','Admin')
INSERT INTO tblStatusUser(statusId, statusName) VALUES ('A','Active')
INSERT INTO tblStatusUser(statusId, statusName) VALUES ('D','Deactive')
INSERT INTO tblLocations(seat, locationName) VALUES(50,'LocationName')
--insert users
INSERT [dbo].[tblUsers] ([userId], [email], [username], [statusId], [roleId]) VALUES (N'102340646113497938153', N'anhtnse151264@fpt.edu.vn', N'anhtnse151264', N'A', N'US')
INSERT [dbo].[tblUsers] ([userId], [email], [username], [statusId], [roleId]) VALUES (N'100244481500661777938', N'trungntse151134@fpt.edu.vn', N'trungntse151134', N'A', N'US')
--insert status event
INSERT [dbo].[tblStatusEvent] ([statusId], [statusName]) VALUES (N'AP', N'Approve')
--insert locations
INSERT [dbo].[tblLocations] ([locationName], [seat]) VALUES (N'Thư viện tầng 1',50)
INSERT [dbo].[tblLocations] ([locationName], [seat]) VALUES (N'Thư viện tầng 2',50)
INSERT [dbo].[tblLocations] ([locationName], [seat]) VALUES (N'Thư viện tầng 3',50)
--insert events
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [location], [seat], [createDatetime], [startDatetime], [endDatetime], [statusId]) VALUES ( N'102340646113497938153', N'test', N'test', N'FPT', 1, CAST(N'2021-09-21T00:00:00.000' AS DateTime), CAST(N'2021-09-21T00:00:00.000' AS DateTime), CAST(N'2021-09-21T00:00:00.000' AS DateTime), N'AP')
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [location], [seat], [createDatetime], [startDatetime], [endDatetime], [statusId]) VALUES ( N'102340646113497938153', N'test1', N'test1', N'FPT', 1, CAST(N'2021-09-21T00:00:00.000' AS DateTime), CAST(N'2021-09-21T00:00:00.000' AS DateTime), CAST(N'2021-09-21T00:00:00.000' AS DateTime), N'AP')
--insert locations