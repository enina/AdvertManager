@echo off
SETLOCAL
pushd %~dp0

SET DB.HOME=C:\dev\oracleXE\app\oracle\product\11.2.0\server\bin
SET GEOIP.CSV="C:\dev\pgDB\data\csv.csv"

%DB.HOME%\sqlplus.exe   geoip/geoip@ilyae-dt:1521/xe    @..\db\oracle\geoipTables.sql
%DB.HOME%\sqlldr.exe    geoip/geoip@ilyae-dt:1521/xe    control=..\db\oracle\geoip.ctl log=c:\adman.geoip.log SILENT=(HEADER, FEEDBACK)
%DB.HOME%\sqlplus.exe   geoip/geoip@ilyae-dt:1521/xe    @..\db\oracle\loadGeoIP.sql

popd

ENDLOCAL