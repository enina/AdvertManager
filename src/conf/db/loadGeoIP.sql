--execute only after sql import
USE geoip;
INSERT INTO cc SELECT DISTINCT NULL,cc,cn FROM csv;
INSERT INTO ip SELECT start,end,ci FROM csv NATURAL JOIN cc;
DROP TABLE csv;