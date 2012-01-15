DROP SCHEMA IF EXISTS advert_manager_schema;
CREATE  SCHEMA IF NOT EXISTS advert_manager_schema;
use advert_manager_schema;
SET storage_engine=innodb;
drop table if exists affiliate_to_product  CASCADE;
drop table if exists purchase_order CASCADE;
drop table if exists access_log  CASCADE;
drop table if exists product  CASCADE;
drop table if exists author CASCADE;
drop table if exists product_group  CASCADE;
drop table if exists affiliate  CASCADE;
drop table if exists partner  CASCADE;
drop table if exists access_source  CASCADE;

create table author (
    id INT NOT NULL AUTO_INCREMENT,
    authorName varchar(256) not null ,
    email      varchar(256) not null ,
    CONSTRAINT AUTHOR_PK PRIMARY KEY (id));

create table affiliate (
    id INT NOT NULL AUTO_INCREMENT,
    affiliateName varchar(256) not null ,
    email         varchar(256) not null ,
    CONSTRAINT AFFILIATE_PK PRIMARY KEY (id));

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
    description varchar(256),
    price int not null ,
    commision int not null ,
    sync_status  int not null,
    product_link varchar(256) not null,
    redirect_link varchar(256) not null,
    CONSTRAINT PRODUCT_PK PRIMARY KEY (id),
    FOREIGN KEY (product_group_id)  REFERENCES product_group(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE);

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

