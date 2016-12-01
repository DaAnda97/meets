-- ---------------------------------------
-- EXISTS a SPECIFIC USERNAME of a MEMBER?
-- ---------------------------------------
SET @username = 'jbell3';

SELECT DISTINCT idMember
FROM member
WHERE username = @username;