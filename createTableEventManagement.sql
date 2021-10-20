exec sp_help tblPosts
--query
  WITH tblEventPage AS (SELECT (ROW_NUMBER() over (order by ABS( DATEDIFF(DAY, startDate, GETDATE())), statusName)) AS RowNum,
  eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, s.statusName AS status
  FROM tblEvents e, tblStatusEvent s
 WHERE  e.statusId = s.statusId)

SELECT eventId, userId, title, description, locationId, createDatetime, startDate, startSlot, endSlot, status
FROM tblEventPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?
--testing
DROP TABLE tblTest
CREATE TABLE tblTest(
	eventId INT IDENTITY(0,1) PRIMARY KEY,
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	title NVARCHAR(50) NOT NULL,
	description NVARCHAR(MAX) NOT NULL,
	locationId INT REFERENCES tblLocations NOT NULL,
	createDatetime DATETIME NOT NULL,
	startDate DATE NOT NULL ,
	startSlot NVARCHAR(1) REFERENCES tblSlots(SlotId) NOT NULL,
	endSlot NVARCHAR(1) REFERENCES tblSlots(SlotId) NOT NULL,
	statusId NVARCHAR(5) REFERENCES tblStatusEvent(statusId) on update cascade);
	as 
    CASE 
		WHEN startDate <= GETDATE() THEN 'Expire'
		WHEN startDate = GETDATE() THEN 'On-going'
		ELSE 'Pending' 
	END
);
--DROP TABLE
DROP TABLE tblHistoryLogs;
DROP TABLE tblFriendInvitation;

DROP TABLE tblEventInvitation;
DROP TABLE tblUsers;
DROP TABLE tblStatusUser;
DROP TABLE tblRoles;
DROP TABLE tblEvents;
DROP TABLE tblStatusEvent;
DROP TABLE tblSlots;
DROP TABLE tblStatusFI;
DROP TABLE tblStatusI;
DROP TABLE tblFollowedEvent;
DROP TABLE tblPosts;
DROP TABLE tblComments

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
	description NVARCHAR(MAX) NOT NULL,
	locationId INT REFERENCES tblLocations NOT NULL,
	createDatetime DATETIME NOT NULL,
	startDate DATE NOT NULL ,
	startSlot NVARCHAR(1) REFERENCES tblSlots(SlotId) NOT NULL,
	endSlot NVARCHAR(1) REFERENCES tblSlots(SlotId) NOT NULL,
	status as 
    CASE 
		WHEN startDate <= GETDATE() THEN 'Expire'
		WHEN startDate = GETDATE() THEN 'On-going'
		ELSE 'Pending' 
	END
);
CREATE TABLE tblFollowedEvent(
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	eventId INT REFERENCES tblEvents NOT NULL,
	follow INT NOT NULL,
	PRIMARY KEY (userId,eventId),
);
CREATE TABLE [dbo].[tblPosts](
	[ID] [int] IDENTITY(0,1) NOT NULL,
	[postId]  AS ('P'+right(CONVERT([varchar](9),[ID]),(9))) PERSISTED NOT NULL PRIMARY KEY,
	[userId] [nvarchar](50) NULL,
	[title] [nvarchar](50) NULL,
	[content] [nvarchar](MAX) NULL,
	[video] [nvarchar](MAX) NULL,
	[createDate] [datetime] NOT NULL,
	[statusId] [nvarchar](5) NULL
)
CREATE TABLE tblParticipants(
	eventId INT REFERENCES tblEvents NOT NULL,
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	seatNumber INT NOT NULL,
	
	PRIMARY KEY(eventId, userId)
)
CREATE TABLE tblLocations(
	locationId INT IDENTITY(0,1) PRIMARY KEY,
	seat INT NOT NULL,--maximum seats can be took
	locationName NVARCHAR(100) NOT NULL,
)
CREATE TABLE tblSlots(
	slotId NVARCHAR(1) PRIMARY KEY,
	startTime TIME NOT NULL,
	endTime TIME NOT NULL
)

CREATE TABLE tblComments(
	commentId INT IDENTITY(0,1) PRIMARY KEY,
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	postId VARCHAR(10) REFERENCES tblPosts,
	eventId INT REFERENCES tblEvents,
	commentDatetime DATETIME NOT NULL,
	content NVARCHAR(100) NOT NULL
)
--demo untested
CREATE TABLE tblUserPicture(
	userId NVARCHAR(50) PRIMARY KEY REFERENCES tblUsers(userId),
	picture NVARCHAR(MAX)
)
CREATE TABLE tblContents(
	contentId INT IDENTITY(0,1) PRIMARY KEY,
	content NVARCHAR(300),
	video NVARCHAR(100),
	createDate DATETIME NOT NULL,
)


CREATE TABLE tblCommentPost(
	userId NVARCHAR(50) REFERENCES tblUsers NOT NULL,
	postId INT REFERENCES tblPosts,
	commentDatetime DATETIME NOT NULL,
	content NVARCHAR(100) NOT NULL,
	PRIMARY KEY(commentDatetime)
)

CREATE TABLE tblVotes(
	userId NVARCHAR(50) REFERENCES tblUsers,
	eventId INT REFERENCES tblEvents,
	vote INT NOT NULL,
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
INSERT [dbo].[tblStatusEvent] ([statusId], [statusName]) VALUES (N'P', N'Pending')
INSERT [dbo].[tblStatusEvent] ([statusId], [statusName]) VALUES (N'OG', N'On-going')
INSERT [dbo].[tblStatusEvent] ([statusId], [statusName]) VALUES (N'E', N'Expire')
--insert locations
INSERT [dbo].[tblLocations] ([locationName], [seat]) VALUES (N'Thư viện tầng 1',50)
INSERT [dbo].[tblLocations] ([locationName], [seat]) VALUES (N'Thư viện tầng 2',50)
INSERT [dbo].[tblLocations] ([locationName], [seat]) VALUES (N'Thư viện tầng 3',50)
--insert events

INSERT [dbo].[tblEvents] ( [userId], [title], [description], [locationId], [createDatetime], [startDate], [startSlot], [endSlot], [statusId]) VALUES ( N'100244481500661777938', N'firstEvent', N'wasd', 1, CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-10' AS Date), N'1', N'2', 'P')
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [locationId], [createDatetime], [startDate], [startSlot], [endSlot], [statusId]) VALUES ( N'100244481500661777938', N'secondEvent', N'wasd', 1, CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-10' AS Date), N'1', N'2', N'P')
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [locationId], [createDatetime], [startDate], [startSlot], [endSlot], [statusId]) VALUES ( N'100244481500661777938', N'3rdEvent', N'wasd', 1, CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-10' AS Date), N'1', N'2', N'P')
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [locationId], [createDatetime], [startDate], [startSlot], [endSlot], [statusId]) VALUES ( N'100244481500661777938', N'4thEvent', N'wasd', 1, CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-10' AS Date), N'1', N'2', N'P')
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [locationId], [createDatetime], [startDate], [startSlot], [endSlot], [statusId]) VALUES ( N'100244481500661777938', N'5thEvent', N'wasd', 1, CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-10' AS Date), N'1', N'2', N'P')
INSERT [dbo].[tblEvents] ( [userId], [title], [description], [locationId], [createDatetime], [startDate], [startSlot], [endSlot], [statusId]) VALUES ( N'100244481500661777938', N'firstEvent', N'wasd', 1, CAST(N'2021-02-10T00:00:00.000' AS DateTime), CAST(N'2021-02-10' AS Date), N'1', N'2', N'P')

INSERT INTO tblEvents (userId, title, description, locationId, startDate , createDatetime, slotId, statusId)
VALUES ('100244481500661777938','firstEvent','wasd',0,  CAST(N'2021-02-10' AS Date) , CAST(N'2021-02-10T00:00:00.000' AS DateTime),1, (SELECT statusId FROM tblStatusEvent WHERE statusName = 'Pending'))
--insert slot-time
INSERT INTO tblSlots(slotId, startTime, endTime) VALUES ('1', '07:00', '08:30')
INSERT INTO tblSlots(slotId, startTime, endTime) VALUES ('2', '08:45', '10:15')
INSERT INTO tblSlots(slotId, startTime, endTime) VALUES ('3', '10:30', '12:00')
INSERT INTO tblSlots(slotId, startTime, endTime) VALUES ('4', '12:30', '14:00')
INSERT INTO tblSlots(slotId, startTime, endTime) VALUES ('5', '14:15', '15:45')
INSERT INTO tblSlots(slotId, startTime, endTime) VALUES ('6', '16:00', '05:30')