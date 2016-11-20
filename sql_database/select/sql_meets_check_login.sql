-- ---------------------
-- Is the LOGIN CORRECT?
-- ---------------------
SET @contact = 'Flexidy';
SET @domin = 'comcast.net';
SET @password = cast( 'EqMGPEwp4' as binary(32));

SELECT DISTINCT idMember
FROM member
INNER JOIN emaildomain 	ON member.mailDomain = emaildomain.idDomain
WHERE emaildomain.domainName = @domin
AND member.mailContact = @contact
AND member.memberPW = @password;