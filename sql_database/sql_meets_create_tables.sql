-- ---------------------------------
-- Author: Sebastian Luther
-- Date:   17-11-2016
-- ---------------------------------


drop table if exists meets.meet;
drop table if exists meets.meeting;
drop table if exists meets.member;
drop table if exists meets.emailcontact;
drop table if exists meets.emaildomain;
drop table if exists meets.category;
drop table if exists meets.location;

-- ---------------------------------
-- Table 'Meets'.'EMailDomain'
-- ---------------------------------

create table if not exists meets.emaildomain
(
    idDomain     int not null AUTO_INCREMENT,
    domainName   varchar(64) not null unique,
    primary key (idDomain)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'EMailContact'
-- ---------------------------------

create table if not exists meets.emailcontact
(
	idAddress	 int not null AUTO_INCREMENT,
    idDomain     int not null,
    contact		 varchar(255) not null,
    primary key  (idAddress),
    foreign key  (idDomain) references meets.emaildomain(idDomain)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'Location'
-- ---------------------------------

create table if not exists meets.location
(
    idLoc   	 int not null AUTO_INCREMENT,
    postalCode	 int not null,
    locName		 varchar(45) not null,
	primary key  (idLoc)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'Category'
-- ---------------------------------

create table if not exists meets.category
(
    idCategory   int not null AUTO_INCREMENT,
	categoryName varchar(45) not null,
	primary key  (idCategory)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'Member'
-- ---------------------------------

create table if not exists meets.member
(
    idMember     int not null AUTO_INCREMENT,
    username     varchar(30) not null unique,
	preName      varchar(45),
	surName      varchar(45),
    memberPW     binary(32) not null,
    memberMAIL   int not null,
	created		 timestamp default current_timestamp,
    primary key  (idMember),
    foreign key  (memberMAIL) references meets.emailcontact(idAddress)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'Meeting'
-- ---------------------------------

create table if not exists meets.meeting
(
    idMeeting    	int not null AUTO_INCREMENT,
    meetingName  	varchar(30) not null,
	meetingDesc  	varchar(255),
	category     	int not null,
	maxMembers		int not null,
	meetingDateTime	datetime not null,
    meetingCreated	timestamp default current_timestamp,
	locMeeting	 	int,
	locCreated	 	int,
	meetingOwner 	int not null,
	primary key 	(idMeeting),
	foreign key 	(category) references meets.category(idCategory),
	foreign key 	(locMeeting) references meets.location(idLoc),
	foreign key 	(locCreated) references meets.location(idLoc),
	foreign key 	(meetingOwner) references meets.member(idMember)
)ENGINE=InnoDB;

-- ---------------------------------
-- Table 'Meets'.'Meet' | Member-Meeting
-- ---------------------------------

create table if not exists meets.meet
(
    idMeet       int not null AUTO_INCREMENT,
    idMember	 int not null,
    idMeeting	 int not null,
	primary key  (idMeet),
	foreign key  (idMember) references meets.member(idMember),
	foreign key  (idMeeting) references meets.meeting(idMeeting)
)ENGINE=InnoDB;

