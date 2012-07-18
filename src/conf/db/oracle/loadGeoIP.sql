INSERT INTO geoip.cc (ci,cc,cn) select root.adman_seq.nextval ,cc,cn from (select distinct  cc,cn FROM geoip.csv);
INSERT INTO geoip.ip SELECT startx,endx,ci FROM geoip.csv NATURAL JOIN geoip.cc;
DROP TABLE geoip.csv;
commit;
exit;