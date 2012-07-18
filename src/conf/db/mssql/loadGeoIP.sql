use geoip
--BULK INSERT CSV  FROM 'c:\csvtest.txt' WITH (FIELDTERMINATOR = ',',ROWTERMINATOR = '\n')
GO

INSERT INTO cc (cc,cn) select distinct  cc,cn FROM csv;
INSERT INTO ip SELECT ci,startx,endx FROM csv csv inner JOIN cc cc on cc.cc=csv.cc
go

--DROP TABLE csv;
go
