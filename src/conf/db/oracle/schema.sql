drop user root cascade;
drop user geoip cascade;

CREATE USER root IDENTIFIED BY admin DEFAULT TABLESPACE users;
CREATE USER geoip IDENTIFIED BY geoip  DEFAULT TABLESPACE users;
    
grant all privileges to root;
grant all privileges to geoip;