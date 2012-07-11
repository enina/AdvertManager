-- execute only after sql import
USE geoip;
INSERT INTO geoip.cc (cc,cn) SELECT DISTINCT cc,cn FROM geoip.csv;
INSERT INTO geoip.ip SELECT startx,endx,ci FROM geoip.csv NATURAL JOIN geoip.cc;
DROP TABLE geoip.csv;