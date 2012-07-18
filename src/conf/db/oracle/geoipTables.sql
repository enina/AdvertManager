drop table csv cascade constraints;
drop table cc cascade constraints;
drop table ip cascade constraints;

CREATE TABLE csv (
  start_ip VARCHAR(15) NOT NULL,
  end_ip VARCHAR(15) NOT NULL,
  startx NUMBER  NOT NULL,
  endx NUMBER NOT NULL,
  cc VARCHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE cc (
  ci NUMBER NOT NULL PRIMARY KEY ,
  cc VARCHAR(2) NOT NULL,
  cn VARCHAR(50) NOT NULL
);
CREATE TABLE ip (
  startx    NUMBER  NOT NULL,
  endx      NUMBER NOT NULL,
  ci        NUMBER NOT NULL
);
commit;
exit;