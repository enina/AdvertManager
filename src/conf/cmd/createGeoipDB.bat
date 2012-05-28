@echo off
SETLOCAL
SET MYSQL.HOME=D:\prv\dev\MySQL55\
SET GEOIP.CSV="C:\tmp\csv.csv"
SET IMPORT_OPTS=
SET IMPORT_OPTS=%IMPORT_OPTS% --fields-terminated-by=","
SET IMPORT_OPTS=%IMPORT_OPTS% --fields-optionally-enclosed-by="\"" 
SET IMPORT_OPTS=%IMPORT_OPTS% --lines-terminated-by="\n"
SET IMPORT_OPTS=%IMPORT_OPTS% --host=nina-pc
SET IMPORT_OPTS=%IMPORT_OPTS% --user=root 
SET IMPORT_OPTS=%IMPORT_OPTS% --password=admin 
SET IMPORT_OPTS=%IMPORT_OPTS% geoip %GEOIP.CSV% 

%MYSQL.HOME%\bin\mysqlimport.exe %IMPORT_OPTS%
pause
ENDLOCAL