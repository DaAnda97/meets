-- ---------------------------------------------
-- GET MAIL and PASSWORD of a SPECIFIC MEMBER --
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT concat(member.mailContact, '@', emaildomain.domainName) AS 'memberMAIL', convert(memberPW, character) AS 'memberPW'
FROM member
INNER JOIN emaildomain 	ON member.mailDomain = emaildomain.idDomain
WHERE member.idMember = @memberID;