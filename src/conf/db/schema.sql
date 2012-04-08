DROP SCHEMA IF EXISTS advert_manager_schema;
CREATE  SCHEMA IF NOT EXISTS advert_manager_schema;
use advert_manager_schema;
SET storage_engine=innodb;

------------------ application tables --------------
drop table if exists affiliate_to_product   CASCADE;
drop table if exists purchase_order         CASCADE;
drop table if exists access_log             CASCADE;
drop table if exists product                CASCADE;
drop table if exists author                 CASCADE;
drop table if exists product_group          CASCADE;
drop table if exists affiliate              CASCADE;
drop table if exists partner                CASCADE;
drop table if exists access_source          CASCADE;
------------------ application tables --------------

---------- spring security tables ------------------
drop table if exists authorities        CASCADE;
drop table if exists groups             CASCADE;
drop table if exists group_authorities  CASCADE;
drop table if exists group_members      CASCADE;
---------- spring security tables ------------------


create table author (
    id INT NOT NULL AUTO_INCREMENT,
    authorName varchar(256) not null ,
    email      varchar(256) not null ,
    CONSTRAINT AUTHOR_PK PRIMARY KEY (id));

create table affiliate (
    id INT NOT NULL AUTO_INCREMENT,
    affiliateName varchar(256)  not null ,
    email         varchar(256)  not null ,
    password      varchar(50)   not null,
    enabled       boolean       not null,
    CONSTRAINT AFFILIATE_PK         PRIMARY KEY (id),
    CONSTRAINT AFFILIATE_NAME_UQ    UNIQUE affNameIdx  (affiliateName(255)),
    CONSTRAINT AFFILIATE_EMAIL_UQ   UNIQUE affEmailIdx (email(255)) );

create table product_group (
    id INT NOT NULL AUTO_INCREMENT,
    affiliate_id int not null ,
    group_name  varchar(256),
    description varchar(256),
    CONSTRAINT PRODUCT_GROUP_PK PRIMARY KEY (id),
    CONSTRAINT PRODUCT_GROUP_AFFILIATE_FK FOREIGN KEY (affiliate_id) REFERENCES affiliate(id) ON DELETE CASCADE);

create table product (
    id INT NOT NULL AUTO_INCREMENT,
    product_group_id  int not null,
    author_id int not null,
    name varchar(256) not null,
    description varchar(256),
    price int not null ,
    commision int not null ,
    sync_status  int not null,
    product_link varchar(256) not null,
    redirect_link varchar(256) not null,
    CONSTRAINT PRODUCT_PK PRIMARY KEY (id),
    CONSTRAINT PRODUCT_PRODUCT_GROUP_FK FOREIGN KEY (product_group_id)  REFERENCES product_group(id) ON DELETE CASCADE,
    CONSTRAINT PRODUCT_AUTHOR_GROUP_FK FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE);

create table affiliate_to_product (
    id INT NOT NULL AUTO_INCREMENT,
    affiliate_id int not null ,
    product_id   int  not null ,
    PRIMARY KEY (id),
    FOREIGN KEY (affiliate_id) REFERENCES affiliate(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE);

create table access_source (
    id INT NOT NULL AUTO_INCREMENT,
    access_source_domain varchar(256) not null ,
    description  varchar(256) not null ,
    PRIMARY KEY (id));


create table access_log (
    id INT NOT NULL AUTO_INCREMENT,
    product_id  int not null ,
    access_time TIMESTAMP , --vremya perehoda
    ip_address varchar(256) , --client ip address
    location  varchar(256)  , --client geo location
    source_domain_id int not null ,
    url varchar(256)  ,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id) on delete cascade,
    FOREIGN KEY (source_domain_id) REFERENCES access_source(id) ON DELETE CASCADE);


create table purchase_order (
    id          int not null auto_increment ,
    product_id  int not null ,
    access_id   int not null,--ukazatel tablica perehodov
    status      int  not null,--order status
    discount    int not null, --skidka
    ordertime   TIMESTAMP , --vremya zakaza
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id) on delete cascade,
    FOREIGN KEY (access_id)  REFERENCES access_log(id) on delete cascade);

create table partner (
    id                      int         not null auto_increment,
    payment_transfer_time   TIMESTAMP , --vremya zakaza
    payment_amount          int         not null ,
    email         varchar(256) not null ,
    PRIMARY KEY (id));


--------------------------------------------------- Spring Security tables ---------------------------------------------------
create table authorities (
    user_id     int not null,
    authority   varchar(50) not null,
    constraint fk_authorities_users foreign key(user_id) references affiliate(id));

create unique index ix_auth_username on authorities (user_id,authority);

create table groups (
    id int not null auto_increment,
    group_name varchar(50) not null,
    CONSTRAINT group_PK PRIMARY KEY (id));

create table group_authorities (
    group_id int not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
    id int not null auto_increment,
    username varchar(50) not null,
    group_id int not null,
    CONSTRAINT group_members_PK PRIMARY KEY (id),
    constraint fk_group_members_group foreign key(group_id) references groups(id));
------------------------------------------------------------------------------------------------------------------------------
-----------------------------------default data-------------------------------------------------------------------------------
insert into affiliate (affiliateName , email,password,enabled) values ('ilya','ilya@mail.com','ilya',1);
insert into affiliate (affiliateName , email,password,enabled) values ('nina','nina@mail.com','nina',1);
insert into affiliate (affiliateName , email,password,enabled) values ('misha','misha@mail.com','misha',1);
insert into affiliate (affiliateName , email,password,enabled) values ('root','root@mail.com','admin',1);
insert into affiliate (affiliateName , email,password,enabled) values ('vasya','vasya@mail.com','vasya',1);
insert into affiliate (affiliateName , email,password,enabled) values ('hacker','hacker@mail.com','hacker',0);

insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='ilya';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='nina';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='misha';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='root';
insert into authorities  select id ,'ROLE_USER' from affiliate where affiliateName='vasya';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='hacker';

insert into product_group (affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='ilya' ;
insert into product_group (affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='nina' ;
insert into product_group (affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='misha' ;
insert into product_group (affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='root' ;
insert into product_group (affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='vasya' ;


commit;
-----------------------------------default data-------------------------------------------------------------------------------