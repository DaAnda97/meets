-- ---------------------------------
-- Author: Sebastian Luther
-- Date:   20-11-2016
-- ---------------------------------

DROP TABLE IF EXISTS meets.meet;
DROP TABLE IF EXISTS meets.meeting;
DROP TABLE IF EXISTS meets.member;
DROP TABLE IF EXISTS meets.emaildomain;
DROP TABLE IF EXISTS meets.category;
DROP TABLE IF EXISTS meets.location;

-- ---------------------------------
-- Table 'Meets'.'EMailDomain'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.emaildomain
(
    idDomain     INT NOT NULL AUTO_INCREMENT,
    domainName   VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (idDomain)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Location'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.location
(
    idLoc   	 INT NOT NULL AUTO_INCREMENT,
    postalCode	 INT NOT NULL,
    locName		 VARCHAR(45) NOT NULL,
	PRIMARY KEY  (idLoc)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Category'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.category
(
    idCategory   INT NOT NULL AUTO_INCREMENT,
	categoryName VARCHAR(45) NOT NULL UNIQUE,
	PRIMARY KEY  (idCategory)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Member'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.member
(
    idMember     INT NOT NULL AUTO_INCREMENT,
    username     VARCHAR(30) NOT NULL UNIQUE,
	preName      VARCHAR(45),
	surName      VARCHAR(45),
    memberPW     BINARY(32) NOT NULL,
	mailContact	 VARCHAR(255) NOT NULL,
    mailDomain   INT NOT NULL,
	created		 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY  (idMember),
    FOREIGN KEY  (mailDomain) REFERENCES meets.emaildomain(idDomain)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Meeting'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.meeting
(
    idMeeting    	INT NOT NULL AUTO_INCREMENT,
    meetingName  	VARCHAR(30) NOT NULL,
	meetingDesc  	VARCHAR(255),
	category     	INT NOT NULL,
	maxMembers		INT NOT NULL,
	meetingDateTime	DATETIME NOT NULL,
    meetingCreated	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	locMeeting	 	INT,
	locCreated	 	INT,
	meetingOwner 	INT NOT NULL,
	PRIMARY KEY 	(idMeeting),
	FOREIGN KEY 	(category) REFERENCES meets.category(idCategory),
	FOREIGN KEY 	(locMeeting) REFERENCES meets.location(idLoc),
	FOREIGN KEY 	(locCreated) REFERENCES meets.location(idLoc),
	FOREIGN KEY 	(meetingOwner) REFERENCES meets.member(idMember)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Meet' | Member-Meeting
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.meet
(
    idMeet       INT NOT NULL AUTO_INCREMENT,
    idMember	 INT NOT NULL,
    idMeeting	 INT NOT NULL,
	PRIMARY KEY  (idMeet),
	FOREIGN KEY  (idMember) REFERENCES meets.member(idMember),
	FOREIGN KEY  (idMeeting) REFERENCES meets.meeting(idMeeting)
)ENGINE=INNODB;

