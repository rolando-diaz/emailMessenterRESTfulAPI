
SET FOREIGN_KEY_CHECKS=0;
#SET FOREIGN_KEY_CHECKS=1; 
DROP TABLE IF EXISTS `addressHistory`;
DROP TABLE IF EXISTS `message`;
DROP TABLE IF EXISTS `visits`;


CREATE TABLE addressHistory (
    email CHAR(50)			NOT NULL,
	name VARCHAR(50) 		NOT NULL,
    totalCount BIGINT       default 0,
    todaysCount BIGINT      default 0,
    lastVisited BIGINT               ,
    PRIMARY KEY (email)
);

CREATE TABLE message (
	email CHAR(50)			NOT NULL,
	name VARCHAR(50) 		NOT NULL,
	body VARCHAR(200)       NOT NULL,
	timeUUID CHAR(36)            ,
	frontEndBackDoorKey  VARCHAR(50),           
	remoteAddr VARCHAR(50) 		    ,
	remoteHost VARCHAR(50) 		    ,
	PRIMARY KEY (email, body, timeUUID),
    FOREIGN KEY (email) REFERENCES addressHistory (email) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE visits (
	remoteIpAddr CHAR(50)  NOT NULL ,
	newIpAddr VARCHAR(50) 		    ,
	remoteHost VARCHAR(50)          ,
	numVisits BIGINT   default     0,
	todaysVisits BIGINT  default      0,
	firstVisit  BIGINT  default   0,
	lastVisited  BIGINT  default   0,
	admin BIGINT                      ,
	PRIMARY KEY (remoteIpAddr)
);
