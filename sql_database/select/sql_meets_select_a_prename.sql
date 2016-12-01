-- ---------------------------------------------
-- GET PRENAME of a SPECIFIC MEMBER
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT preName
FROM member
WHERE member.idMember = @memberID;