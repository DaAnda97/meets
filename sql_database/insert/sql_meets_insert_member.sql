
-- ----------------------
-- INSERT a NEW MEMBER --
-- ----------------------
SET @username = 'newUsername';
SET @preName = 'Vorname';
SET @surNAme = 'Nachname';
SET @memberPW = '123';
SET @mailContact = 'mail.contact';
SET @mailDomain = 'mayANewDomain.new';

INSERT IGNORE INTO emailDomain (domainName) VALUES (@mailDomain);

SET @get_ID := (SELECT idDomain FROM emailDomain WHERE domainName = @mailDomain);

INSERT INTO member (username, preName, surName, memberPW, mailContact, mailDomain) 
	VALUES (@username, @preName, @surName, @memberPW, @mailContact, @get_ID);