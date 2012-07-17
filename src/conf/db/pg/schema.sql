
--CREATE DATABASE adman ENCODING 'UTF8';


------------------ application tables --------------
drop table if exists purchase_order         CASCADE;
drop table if exists access_log             CASCADE;
drop table if exists aff_program            CASCADE;
drop table if exists affprog_group          CASCADE;
drop table if exists affiliate              CASCADE;
drop table if exists partner                CASCADE;
drop table if exists access_source          CASCADE;
drop table if exists selectable_data_item   cascade;
drop table if exists billing_data_spec      cascade;
drop table if exists billing_project_spec   cascade;
------------------ application tables --------------

---------- spring security tables ------------------
drop table if exists authorities        CASCADE;
drop table if exists groups             CASCADE;
drop table if exists group_authorities  CASCADE;
drop table if exists group_members      CASCADE;
---------- spring security tables ------------------


create table affiliate (
    id		  serial 	not null ,
    affiliatename varchar(256)  not null ,
    email         varchar(256)  not null ,
    password      varchar(50)   not null,
    enabled       boolean       not null,
    constraint affiliate_pk         primary key (id),
    constraint affiliate_name_uq    unique (affiliatename),
    constraint affiliate_email_uq   unique (email) );

create table affprog_group(
    id		    serial not null ,
    affiliate_id    int not null ,
    group_name	    varchar(256),
    description	    varchar(256),
    constraint program_group_pk primary key (id),
    constraint affprggroup_uq   unique (affiliate_id,group_name),
    constraint program_group_affiliate_fk foreign key (affiliate_id) references affiliate(id) on delete cascade);

create table aff_program (
    id			serial not null ,
    program_group_id	int not null,
    name varchar(256)	not null,
    description		varchar(256),
    username            varchar(256),
    password            varchar(256),
    sync_status		int not null,
    affprogram_link	varchar(256) not null,
    redirect_link	varchar(256) null,
    constraint program_pk primary key (id),
    constraint affprg_uq   unique  (program_group_id,name),
    constraint program_program_group_fk foreign key (program_group_id)  references affprog_group(id) on delete cascade);
    


create table access_source (
    id			 serial not null ,
    access_source_domain varchar(256) not null ,
    description		 varchar(256) not null ,
    constraint accesssource_domain_uq   unique (access_source_domain),
    constraint accesssource_pk primary key (id));



create table partner (
    id          serial       not null ,
    name        varchar(64)  not null ,
    email       varchar(256) not null ,
    constraint partner_pk primary key (id),
    constraint partner_email_uq   unique (email));

create table purchase_order (
    id			    serial not null  ,
    affprogram_id	    int not null ,
    partner_id		    int ,
    status		    varchar(256)  ,--order status
    original_order_id	    varchar(256),
    tracking_id		    varchar(256),
    ip_address		    varchar(256),
    ordertime		    timestamp , --vremya zakaza
    po_sum		    float,
    commision		    float,
    currency		    varchar(16),
    country		    varchar(256),
    city		    varchar(256),
    cn  		    varchar(256),
    cc  		    varchar(2),
    access_amount           int,
    constraint purchaseorder_pk primary key (id),
    constraint purchaseorder_affprogram_fk foreign key (affprogram_id)  references aff_program(id) on delete cascade,
    constraint purchaseorder_partner_fk foreign key (partner_id)  references partner(id) on delete cascade);

create index poAffProgIdx on purchase_order(affprogram_id);
create index poIpIdx      on purchase_order(ip_address);
create index poTimeIdx    on purchase_order(ordertime);
create index poCNIdx      on purchase_order(cn); 

create table access_log (
    id			    serial not null ,
    affprogram_id	    int not null    ,
    source_domain_id	    int		    ,
    po_id       	    int		    ,
    access_time		    timestamp	    , --vremya perehoda
    ip_address		    varchar(256)    , --client ip address
    country_name	    varchar(2048)   , --client country by ip address
    target_url		    varchar(256)    ,
    referer_url		    varchar(384)    ,
    query		    varchar(256)    ,
    constraint accesslog_affprogram_fk      foreign key (affprogram_id)     references aff_program(id)      on delete cascade,
    constraint accesslog_sourcedomain_fk    foreign key (source_domain_id)  references access_source(id)    on delete cascade,
    constraint accesslog_po_fk              foreign key (po_id)             references purchase_order(id)   on delete cascade,
    constraint accesslog_pk                 primary key (id)
);

create    index		    accessAffProgIdx on access_log(affprogram_id);
create    index		    accessSourceDomainIdx on access_log(source_domain_id);
create    index		    accessIpIdx on access_log(ip_address);
create    index		    accessTimeIdx on access_log(access_time);
create    index		    accessCNIdx on access_log(country_name);

CREATE TABLE behavior_stats (
    id               serial NOT NULL ,
    affprogram_id    INT, 
    domain_id        INT,
    stat_type        VARCHAR(32),
    cn		     VARCHAR(255),
    cc		     VARCHAR(4),
    access_amount    BIGINT,
    purchase_amount  BIGINT,
    total_commision  real, 


    constraint behavior_stats_pk            PRIMARY KEY (id),
    constraint behaviorstats_affprogram_fk  foreign key (affprogram_id) references aff_program(id) on delete cascade,
    constraint behaviorstats_domain_fk      foreign key (domain_id) references access_source(id) on delete cascade);

create    index           fbsStatTypeIdx on behavior_stats(stat_type);
create    index           fbsProgIdIdx on behavior_stats(affprogram_id);
create    index           fbsDomainIdx on behavior_stats(domain_id);
create    index           fbsCountryNameIdx on behavior_stats(cn);


CREATE TABLE search_query_stat(
    id               serial NOT NULL ,
    affprogram_id    INT, 
    rating           BIGINT,
    query            VARCHAR(255),
    constraint search_query_stat_pk            PRIMARY KEY (id),
    constraint search_query_stat_affprogram_fk  foreign key (affprogram_id) references aff_program(id) on delete cascade
);

CREATE TABLE billing_project_spec (
  id		    serial	    not null,
  base_url	    varchar(1024)   not null,
  cookie_name	    varchar(32)	    not null,
  home_page	    varchar(256)    not null,
  login_url	    varchar(256)    not null,
  logout_url	    varchar(256)    not null,
  method	    varchar(8)	    not null,
  name		    varchar(256)    not null,
  password_field    varchar(16)	    not null,
  selector	    varchar(256)    not null,
  user_field	    varchar(64)	    not null,
  constraint blngprojectds_pk primary key (id));

CREATE TABLE billing_data_spec (
   id		    serial	    not null ,
   project_id	    int 	    default null,
   data_url	    varchar(512)    not null,
   method	    varchar(8)	    not null,
   name		    varchar(255)    default null,
   num_pages	    int 	    default null,
   page_param	    varchar(512)    default null,
   constraint blngdataspec_pk		primary key (id),
   constraint blngdataspec_project_fk	foreign key (project_id) references billing_project_spec (id)  on delete cascade) ;

create   index	    dsprojidx	    on billing_data_spec(project_id);

CREATE TABLE selectable_data_item (
  id		    serial       not null ,
  dataspec_id	    int          default null,
  name		    varchar(256) not null,
  selector	    varchar(256) not null,
  constraint blngdselectableitem_pk primary key (id),
  constraint blngdselectableitem_dataspec_fk foreign key (dataspec_id) references billing_data_spec (id)  on delete cascade);

create index	    sidsidx on selectable_data_item(dataspec_id);

--------------------------------------------------- Spring Security tables ---------------------------------------------------
create table authorities (
    user_id     int not null,
    authority   varchar(50) not null,
    constraint fk_authorities_users foreign key(user_id) references affiliate(id));

create unique index ix_auth_username on authorities (user_id,authority);

create table groups (
    id serial not null ,
    group_name varchar(50) not null,
    CONSTRAINT group_PK PRIMARY KEY (id));

create table group_authorities (
    group_id int not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
    id serial not null ,
    username varchar(50) not null,
    group_id int not null,
    CONSTRAINT group_members_PK PRIMARY KEY (id),
    constraint fk_group_members_group foreign key(group_id) references groups(id));
------------------------------------------------------------------------------------------------------------------------------
-----------------------------------default data-------------------------------------------------------------------------------
insert into affiliate (affiliateName , email,password,enabled) values ('ilya','ilya@mail.com','ilya',true);
insert into affiliate (affiliateName , email,password,enabled) values ('nina','nina@mail.com','nina',true);
insert into affiliate (affiliateName , email,password,enabled) values ('misha','misha@mail.com','misha',true);
insert into affiliate (affiliateName , email,password,enabled) values ('root','root@mail.com','admin',true);
insert into affiliate (affiliateName , email,password,enabled) values ('vasya','vasya@mail.com','vasya',true);
insert into affiliate (affiliateName , email,password,enabled) values ('hacker','hacker@mail.com','hacker',false);

insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='ilya';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='nina';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='misha';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='root';
insert into authorities  select id ,'ROLE_USER'	 from affiliate where affiliateName='vasya';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='hacker';

insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='ilya' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='nina' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='misha' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='root' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='vasya' ;
-----------------------------------default data-------------------------------------------------------------------------------

commit;