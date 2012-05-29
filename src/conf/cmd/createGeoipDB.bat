@echo off
SETLOCAL
pushd %~dp0

SET MYSQL.HOME=D:\prv\dev\MySQL55\
SET GEOIP.CSV="\\ilyae-dt\c$\Temp\geoIP\csv.csv"
SET DB_NAME=geoip

SET SCRIPT_OPTS=
SET SCRIPT_OPTS=%SCRIPT_OPTS% --host=ilyae-dt
SET SCRIPT_OPTS=%SCRIPT_OPTS% --user=root 
SET SCRIPT_OPTS=%SCRIPT_OPTS% --password=admin 


SET IMPORT_OPTS=%SCRIPT_OPTS%
SET IMPORT_OPTS=%IMPORT_OPTS% --fields-terminated-by=","
SET IMPORT_OPTS=%IMPORT_OPTS% --fields-optionally-enclosed-by="\"" 
SET IMPORT_OPTS=%IMPORT_OPTS% --lines-terminated-by="\n"
SET IMPORT_OPTS=%IMPORT_OPTS% %DB_NAME% %GEOIP.CSV% 

%MYSQL.HOME%\bin\mysql.exe %SCRIPT_OPTS% %DB_NAME% < ..\db\geoipSchema.sql
%MYSQL.HOME%\bin\mysqlimport.exe %IMPORT_OPTS%
%MYSQL.HOME%\bin\mysql.exe %SCRIPT_OPTS% %DB_NAME% < ..\db\loadGeoIP.sql

popd
pause
ENDLOCAL