/*
download geoipcsv from http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip
mysqlimport csv into db
*/

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