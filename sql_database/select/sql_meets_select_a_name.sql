-- ---------------------------------------------
-- GET NAME of a SPECIFIC MEMBER
-- ---------------------------------------------
SET @memberID = 5;

SELECT DISTINCT concat(preName, ' ', surName) AS 'name'
FROM member
WHERE member.idMember = @memberID;