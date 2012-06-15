@echo off
SETLOCAL
pushd %~dp0

SET MYSQL.HOME=D:\prv\dev\MySQL55\
SET GEOIP.CSV="\\ilyae-dt\c$\Temp\geoIP\csv.csv"
rem SET GEOIP.CSV="D:\prv\dev\geoip\csv.csv"


SET SCRIPT_OPTS=
SET SCRIPT_OPTS=%SCRIPT_OPTS% --host=ilyae-dt
rem SET SCRIPT_OPTS=%SCRIPT_OPTS% --host=ilyae-lt
SET SCRIPT_OPTS=%SCRIPT_OPTS% --user=root 
SET SCRIPT_OPTS=%SCRIPT_OPTS% --password=admin 


SET IMPORT_OPTS=%SCRIPT_OPTS%
SET IMPORT_OPTS=%IMPORT_OPTS% --fields-terminated-by=","
SET IMPORT_OPTS=%IMPORT_OPTS% --fields-optionally-enclosed-by="\"" 
SET IMPORT_OPTS=%IMPORT_OPTS% --lines-terminated-by="\n"
SET IMPORT_OPTS=%IMPORT_OPTS% %DB_NAME% %GEOIP.CSV% 

%MYSQL.HOME%\bin\mysql.exe 			%SCRIPT_OPTS% mysql < ..\db\geoipSchema.sql
%MYSQL.HOME%\bin\mysqlimport.exe 	%IMPORT_OPTS%
%MYSQL.HOME%\bin\mysql.exe 			%SCRIPT_OPTS% geoip < ..\db\loadGeoIP.sql
popd

ENDLOCAL