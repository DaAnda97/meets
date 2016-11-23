-- ---------------------------------
-- Author: Sebastian Luther
-- Date:   21-11-2016
-- ---------------------------------

DROP TABLE IF EXISTS meets.meet;
DROP TABLE IF EXISTS meets.meeting;
DROP TABLE IF EXISTS meets.member;
DROP TABLE IF EXISTS meets.category;
DROP TABLE IF EXISTS meets.location;

-- ---------------------------------
-- Table 'Meets'.'Location'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.location
(
    locationID 	 INT NOT NULL AUTO_INCREMENT,
    postalCode	 INT NOT NULL,
    name		 VARCHAR(45) NOT NULL,
	PRIMARY KEY  (locationID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Category'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.category
(
    categoryID 	 INT NOT NULL AUTO_INCREMENT,
	name		 VARCHAR(45) NOT NULL UNIQUE,
	PRIMARY KEY  (categoryID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Member'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.member
(
    memberID	   INT NOT NULL AUTO_INCREMENT,
    username       VARCHAR(30) NOT NULL UNIQUE,
	firstName      VARCHAR(45),
	lastName       VARCHAR(45),
	password	   VARCHAR(256) NOT NULL,
	email		   VARCHAR(320) NOT NULL UNIQUE,
    memberLocation INT NOT NULL,
	created		   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY    (memberID),
	FOREIGN KEY	   (memberLocation) REFERENCES meets.location(locationID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Meeting'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.meeting
(
    meetingID    	INT NOT NULL AUTO_INCREMENT,
    name		  	VARCHAR(30) NOT NULL,
	description  	VARCHAR(255),
	meetingCategory INT NOT NULL,
	meetingDate		DATE NOT NULL,
    meetingTime		TIME NOT NULL,
    meetingLocation	INT,
	meetingOwner 	INT NOT NULL,
    maxMembers		INT NOT NULL,
    createdTime		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	createdLocation	INT,
	PRIMARY KEY 	(meetingID),
	FOREIGN KEY 	(meetingCategory) REFERENCES meets.category(categoryID),
	FOREIGN KEY 	(meetingLocation) REFERENCES meets.location(locationID),
	FOREIGN KEY 	(createdLocation) REFERENCES meets.location(locationID),
	FOREIGN KEY 	(meetingOwner) REFERENCES meets.member(memberID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Meet' | Member-Meeting
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.meet
(
    meetID       	INT NOT NULL AUTO_INCREMENT,
    memberID	 	INT NOT NULL,
    meetingID 	INT NOT NULL,
	PRIMARY KEY (meetID),
	FOREIGN KEY (memberID) REFERENCES meets.member(memberID),
	FOREIGN KEY (meetingID) REFERENCES meets.meeting(meetingID)
)ENGINE=INNODB;

