-- ---------------------------------------------
-- GET USERNAME of a SPECIFIC MEMBER
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT username
FROM member
WHERE member.idMember = @memberID;