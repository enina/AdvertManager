use geoip;

drop table csv ;
go

drop table cc ;
go

drop table ip ;
go

CREATE TABLE csv (
  start_ip      NVARCHAR(15) NOT NULL,
  end_ip        NVARCHAR(15) NOT NULL,
  startx        bigint          NOT NULL,
  endx          bigint          NOT NULL
  cc            NVARCHAR(2)  NOT NULL,
  cn            NVARCHAR(50) NOT NULL,
);
go

CREATE TABLE cc (
  ci                int NOT NULL PRIMARY KEY IDENTITY,
  cc NVARCHAR(2)    NOT NULL,
  cn NVARCHAR(50)   NOT NULL
);
go

CREATE TABLE ip (
  ci        bigint     NOT NULL,
  startx    bigint     NOT NULL,
  endx      bigint     NOT NULL,
  
);
go
