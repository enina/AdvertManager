load data  infile 
    'C:\dev\pgDB\data\csv.csv'  
into table 
    geoip.csv 
fields 
    terminated by "," 
    optionally enclosed by '"' 
( start_ip, end_ip, startx, endx,cc,cn )