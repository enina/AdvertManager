/*
download geoipcsv from http://geolite.maxmind.com/download/geoip/database/GeoIPCountryCSV.zip

*/

DROP SCHEMA IF EXISTS geoip cascade;
create SCHEMA geoip ;
SET search_path TO geoip;

CREATE TABLE csv (
  start_ip CHAR(15) NOT NULL,
  end_ip CHAR(15) NOT NULL,
  startx bigint  NOT NULL,
  endx bigint NOT NULL,
  cc CHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE cc (
  ci serial NOT NULL PRIMARY KEY ,
  cc VARCHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE ip (
  startx bigint  NOT NULL,
  endx bigint NOT NULL,
  ci INT NOT NULL
);

-- Usually postgress is started when the current folder is the data directory.
--So path to the csv file must be provided relatively to the data directory
-- in this example csv.csv file is stored just inside the data directory
copy geoip.csv FROM 'csv.csv'  csv;
INSERT INTO geoip.cc (cc,cn) SELECT DISTINCT cc,cn FROM geoip.csv;
INSERT INTO geoip.ip SELECT startx,endx,ci FROM geoip.csv NATURAL JOIN geoip.cc;
--DROP TABLE geoip.csv;