-- ---------------------------------------------
-- GET ALL FIELDS of a SPECIFIC MEMBER --
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT *,
	convert(memberPW, character) AS 'memberPW',  
    concat(member.mailContact, '@', emaildomain.domainName) AS 'memberMAIL'
FROM member
INNER JOIN emaildomain 	ON member.mailDomain = emaildomain.idDomain
WHERE member.idMember = @memberID;