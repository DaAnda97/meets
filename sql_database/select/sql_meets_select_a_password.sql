-- ---------------------------------------------
-- GET PASSWORD of a SPECIFIC MEMBER
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT memberPW
FROM member
WHERE member.idMember = @memberID;