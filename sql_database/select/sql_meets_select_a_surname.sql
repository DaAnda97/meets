-- ---------------------------------------------
-- GET SURNAME of a SPECIFIC MEMBER
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT surName
FROM member
WHERE member.idMember = @memberID;