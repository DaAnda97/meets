
<<<<<<< HEAD
create table if not exists meets.emaildomain
(
    idDomain     int not null unique AUTO_INCREMENT,
    domainName   varchar(64) not null unique,
    primary key (idDomain)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'EMailContact'
-- ---------------------------------

create table if not exists meets.emailcontact
(
	idAddress	 int not null unique AUTO_INCREMENT,
    idDomain     int not null,
    contact		 varchar(255) not null,
    primary key  (idAddress),
    foreign key  (idDomain) references meets.emaildomain(idDomain)
)ENGINE=InnoDB;
=======
DROP TABLE IF EXISTS meets.Meet;
DROP TABLE IF EXISTS meets.Meeting;
DROP TABLE IF EXISTS meets.Member;
DROP TABLE IF EXISTS meets.Category;
DROP TABLE IF EXISTS meets.Location;
>>>>>>> develop

-- ---------------------------------
-- Table 'Meets'.'Location'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Location
(
<<<<<<< HEAD
    idLoc   	 int not null unique AUTO_INCREMENT,
    postalCode	 int not null,
    locName		 varchar(45) not null,
	primary key  (idLoc)
)ENGINE=InnoDB;
=======
    locationID 	 INT NOT NULL AUTO_INCREMENT,
	city		 VARCHAR(320) NOT NULL UNIQUE,
    longitude	 DOUBLE NOT NULL,
    latitude	 DOUBLE NOT NULL,
	PRIMARY KEY  (locationID)
)ENGINE=INNODB;
>>>>>>> develop

-- ---------------------------------
-- Table 'Meets'.'Category'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Category
(
<<<<<<< HEAD
    idCategory   int not null unique AUTO_INCREMENT,
	categoryName varchar(45) not null unique,
	primary key  (idCategory)
)ENGINE=InnoDB;
=======
    categoryID 	 INT NOT NULL AUTO_INCREMENT,
	title		 VARCHAR(45) NOT NULL UNIQUE,
	PRIMARY KEY  (categoryID)
)ENGINE=INNODB;
>>>>>>> develop

-- ---------------------------------
-- Table 'Meets'.'Member'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Member
(
<<<<<<< HEAD
    idMember     int not null unique AUTO_INCREMENT,
    username     varchar(30) not null unique,
	preName      varchar(45),
	surName      varchar(45),
    memberPW     binary(32) not null,
    memberMAIL   int not null unique,
    primary key  (idMember),
    foreign key  (memberMAIL) references meets.emailcontact(idAddress)
)ENGINE=InnoDB;
=======
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
>>>>>>> develop

-- ---------------------------------
-- Table 'Meets'.'Meeting'
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Meeting
(
<<<<<<< HEAD
    idMeeting    	int not null unique AUTO_INCREMENT,
    meetingName  	varchar(30) not null,
	meetingDesc  	varchar(4000),
	category     	int not null,
	meetingDate  	date not null,
    meetingTime  	time not null,
    meetingCreated	time not null,
	locMeeting	 	int,
	locCreated	 	int,
	primary key 	(idMeeting),
	foreign key 	(category) references meets.category(idCategory),
	foreign key 	(locMeeting) references meets.location(idLoc),
	foreign key 	(locCreated) references meets.location(idLoc)
)ENGINE=InnoDB;
=======
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
>>>>>>> develop

-- ---------------------------------
-- Table 'Meets'.'Meet' | Member-Meeting
-- ---------------------------------

CREATE TABLE IF NOT EXISTS meets.Meet
(
<<<<<<< HEAD
    idMeet       int not null unique AUTO_INCREMENT,
    idMember	 int not null,
    idMeeting	 int not null,
	primary key  (idMeet),
	foreign key  (idMember) references meets.member(idMember),
	foreign key  (idMeeting) references meets.meeting(idMeeting)
)ENGINE=InnoDB;
=======
    meetID       	INT NOT NULL AUTO_INCREMENT,
    memberID	 	INT NOT NULL,
    meetingID 		INT NOT NULL,
	PRIMARY KEY (meetID),
	FOREIGN KEY (memberID) REFERENCES meets.Member(memberID),
	FOREIGN KEY (meetingID) REFERENCES meets.Meeting(meetingID)
)ENGINE=INNODB;
>>>>>>> develop

