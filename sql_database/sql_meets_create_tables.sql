
DROP TABLE IF EXISTS meets.Meet;
DROP TABLE IF EXISTS meets.Meeting;
DROP TABLE IF EXISTS meets.Member;
DROP TABLE IF EXISTS meets.Category;
DROP TABLE IF EXISTS meets.Location;

-- ---------------------------------
-- Table 'Meets'.'Location'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Location
(
    locationID 	 INT NOT NULL AUTO_INCREMENT,
	city		 VARCHAR(320) NOT NULL UNIQUE,
    longitude	 DOUBLE NOT NULL,
    latitude	 DOUBLE NOT NULL,
	PRIMARY KEY  (locationID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Category'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Category
(
    categoryID 	 INT NOT NULL AUTO_INCREMENT,
	title		 VARCHAR(45) NOT NULL UNIQUE,
	PRIMARY KEY  (categoryID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Member'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Member
(
    memberID	   INT NOT NULL AUTO_INCREMENT,
    username       VARCHAR(30) NOT NULL UNIQUE,
	firstName      VARCHAR(45),
	lastName       VARCHAR(45),
	password	   VARCHAR(260) NOT NULL,
	email		   VARCHAR(320) NOT NULL UNIQUE,
    position	   INT NOT NULL,
	created		   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY    (memberID),
	FOREIGN KEY	   (position) REFERENCES meets.Location(locationID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Meeting'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Meeting
(
    meetingID    	INT NOT NULL AUTO_INCREMENT,
    title		  	VARCHAR(50) NOT NULL,
	description  	VARCHAR(500),
	category 		INT NOT NULL,
	date			DATE NOT NULL,
    time			TIME NOT NULL,
    location		INT,
	creator		 	INT NOT NULL,
    scope			INT NOT NULL,
    createdTime		TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	createdLocation	INT NOT NULL,
	PRIMARY KEY 	(meetingID),
	FOREIGN KEY 	(category) REFERENCES meets.Category(categoryID),
	FOREIGN KEY 	(location) REFERENCES meets.Location(locationID),
	FOREIGN KEY 	(createdLocation) REFERENCES meets.Location(locationID),
	FOREIGN KEY 	(creator) REFERENCES meets.Member(memberID)
)ENGINE=INNODB;

-- ---------------------------------
-- Table 'Meets'.'Meet' | Member-Meeting
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Meet
(
    meetID       	INT NOT NULL AUTO_INCREMENT,
    memberID	 	INT NOT NULL,
    meetingID 		INT NOT NULL,
	PRIMARY KEY (meetID),
	FOREIGN KEY (memberID) REFERENCES meets.Member(memberID),
	FOREIGN KEY (meetingID) REFERENCES meets.Meeting(meetingID)
)ENGINE=INNODB;

