/*
download geoipcsv from http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip
mysqlimport csv into db
*/

DROP SCHEMA IF EXISTS geoip cascade;
create SCHEMA geoip ;
DEFAULT CHARACTER SET 'utf8' COLLATE default;

USE geoip;
CREATE TABLE csv (
  start_ip CHAR(15) NOT NULL,
  end_ip CHAR(15) NOT NULL,
  startx bigint  NOT NULL,
  endx bigint NOT NULL,
  cc CHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE cc (
  ci INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  cc VARCHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE ip (
  startx bigint  NOT NULL,
  endx bigint NOT NULL,
  ci INT NOT NULL
);
