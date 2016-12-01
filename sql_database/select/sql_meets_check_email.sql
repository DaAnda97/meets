-- ------------------------------------
-- EXISTS a SPECIFIC EMAIL of a MEMBER?
-- ------------------------------------
SET @mailContact = 'Flexidy';
SET @mailDomain = 'comcast.net';

SELECT DISTINCT idMember
FROM member
INNER JOIN emaildomain 	ON member.mailDomain = emaildomain.idDomain
WHERE member.mailContact = @mailContact
AND emaildomain.domainName = @mailDomain;