--DROP TABLE
DROP TABLE tblHistoryLogs;
DROP TABLE tblFriendInvitation;
DROP TABLE tblEvents;
DROP TABLE tblEventInvitation;
DROP TABLE tblUsers;
DROP TABLE tblStatusUser;
DROP TABLE tblRoles;
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
	roleId NVARCHAR(5) REFERENCES tblRoles,
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
	location NVARCHAR(100) NOT NULL,
	createDatetime DATETIME NOT NULL,
	startDatetime DATETIME NOT NULL,
	endDatetime DATETIME NOT NULL,
);
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
INSERT INTO tblRoles(roleId, roleName) VALUES ('US','Basic User')
INSERT INTO tblRoles(roleId, roleName) VALUES ('EM','Event Manager')
INSERT INTO tblRoles(roleId, roleName) VALUES ('MT','Mentor/Lecturer')
INSERT INTO tblRoles(roleId, roleName) VALUES ('AD','Admin')
INSERT INTO tblStatusUser(statusId, statusName) VALUES ('A','Active')
INSERT INTO tblStatusUser(statusId, statusName) VALUES ('D','Deactive')
