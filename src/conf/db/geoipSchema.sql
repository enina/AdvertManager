DROP SCHEMA IF EXISTS geoip;
create SCHEMA IF NOT EXISTS geoip DEFAULT CHARACTER SET 'utf8' COLLATE default;

USE geoip;
CREATE TABLE csv (
  start_ip CHAR(15) NOT NULL,
  end_ip CHAR(15) NOT NULL,
  start INT UNSIGNED NOT NULL,
  end INT UNSIGNED NOT NULL,
  cc CHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE cc (
  ci TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  cc CHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE ip (
  start INT UNSIGNED NOT NULL,
  end INT UNSIGNED NOT NULL,
  ci TINYINT UNSIGNED NOT NULL
);
--download geoipcsv from http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip
--mysqlimport csv into db
--execute only after sql import
--INSERT INTO cc SELECT DISTINCT NULL,cc,cn FROM csv;
--INSERT INTO ip SELECT start,end,ci FROM csv NATURAL JOIN cc;
--DROP TABLE csv;