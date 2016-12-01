-- ---------------------------------------------
-- GET ALL EMAIL-ADDRESSES of ALL MEMBERS
-- ---------------------------------------------

SELECT DISTINCT idMember, concat(member.mailContact, '@', emaildomain.domainName) AS 'mail'
FROM member
INNER JOIN emaildomain ON member.mailDomain = emaildomain.idDomain;