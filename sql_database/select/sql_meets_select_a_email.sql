-- ---------------------------------------------
-- GET MAIL of a SPECIFIC MEMBER
-- ---------------------------------------------
SET @memberID = 5;

SELECT concat(member.mailContact, '@', emaildomain.domainName) AS 'mailAddress'
FROM member
INNER JOIN emaildomain 	ON member.mailDomain = emaildomain.idDomain
WHERE member.idMember = @memberID;