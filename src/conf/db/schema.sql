DROP SCHEMA IF EXISTS advert_manager_schema;
CREATE  SCHEMA IF NOT EXISTS advert_manager_schema DEFAULT CHARACTER SET 'utf8' COLLATE default;
use advert_manager_schema;
SET storage_engine=innodb;

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
    id		  int		not null auto_increment,
    affiliatename varchar(256)  not null ,
    email         varchar(256)  not null ,
    password      varchar(50)   not null,
    enabled       boolean       not null,
    constraint affiliate_pk         primary key (id),
    constraint affiliate_name_uq    unique affNameIdx  (affiliatename(255)),
    constraint affiliate_email_uq   unique affEmailIdx (email(255)) );

create table affprog_group(
    id		    int not null auto_increment,
    affiliate_id    int not null ,
    group_name	    varchar(256),
    description	    varchar(256),
    constraint program_group_pk primary key (id),
    constraint affprggroup_uq   unique affProgGroupUQIdx (affiliate_id,group_name(255)),
    constraint program_group_affiliate_fk foreign key (affiliate_id) references affiliate(id) on delete cascade);

create table aff_program (
    id			int not null auto_increment,
    program_group_id	int not null,
    name varchar(256)	not null,
    description		varchar(256),
    username            varchar(256),
    password            varchar(256),
    sync_status		int not null,
    affprogram_link	varchar(256) not null,
    redirect_link	varchar(256) null,
    constraint program_pk primary key (id),
    constraint affprg_uq   unique affProgNameUQIdx (program_group_id,name(255)),
    constraint program_program_group_fk foreign key (program_group_id)  references affprog_group(id) on delete cascade);
    


create table access_source (
    id			 int not null auto_increment,
    access_source_domain varchar(256) not null ,
    description		 varchar(256) not null ,
    constraint accesssource_domain_uq   unique accessSourceDomainUQIdx (access_source_domain(255)),
    constraint accesssource_pk primary key (id));


create table access_log (
    id			    int not null auto_increment,
    affprogram_id	    int not null    ,
    source_domain_id	    int		    ,
    access_time		    timestamp	    , --vremya perehoda
    ip_address		    varchar(256)    , --client ip address
    countryname		    varchar(2048)   , --client country by ip address
    target_url		    varchar(256)    ,
    referer_url		    varchar(384)    ,
    query		    varchar(256)    ,
    index		    accessAffProgIdx(affprogram_id),
    index		    accessSourceDomainIdx(affprogram_id),
    index		    accessIpIdx(ip_address),
    index		    accessTimeIdx(access_time),
    index		    accessCNIdx(countryname),
    constraint accesslog_pk primary key (id),
    constraint accesslog_affprogram_fk foreign key (affprogram_id) references aff_program(id) on delete cascade,
    constraint accesslog_sourcedomain_fk foreign key (source_domain_id) references access_source(id) on delete cascade);


create table partner (
    id          int          not null auto_increment,
    name        varchar(64)  not null ,
    email       varchar(256) not null ,
    constraint partner_pk primary key (id),
    constraint partner_email_uq   unique partnerEmailUQIdx (email(255)));

create table purchase_order (
    id			    int not null auto_increment ,
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
    index		    poAffProgIdx(affprogram_id),
    index		    poIpIdx(ip_address),
    index		    poTimeIdx(ordertime),
    index		    poCNIdx(cn),
    constraint purchaseorder_pk primary key (id),
    constraint purchaseorder_affprogram_fk foreign key (affprogram_id)  references aff_program(id) on delete cascade,
    constraint purchaseorder_partner_fk foreign key (partner_id)  references partner(id) on delete cascade);

CREATE TABLE behavior_stats (
    id               INT NOT NULL AUTO_INCREMENT,
    affprogram_id    INT, 
    domain_id        INT,
    stat_type        VARCHAR(32),
    cn		     VARCHAR(255),
    cc		     VARCHAR(4),
    access_amount    BIGINT,
    purchase_amount  BIGINT,
    total_commision  DOUBLE, 

    index           fbsStatTypeIdx(stat_type),
    index           fbsProgIdIdx(affprogram_id),
    index           fbsDomainIdx(domain_id),
    index           fbsCountryNameIdx(cn),

    constraint behavior_stats_pk            PRIMARY KEY (id),
    constraint behaviorstats_affprogram_fk  foreign key (affprogram_id) references aff_program(id) on delete cascade,
    constraint behaviorstats_domain_fk      foreign key (domain_id) references access_source(id) on delete cascade);


CREATE TABLE search_query_stat(
    id               INT NOT NULL AUTO_INCREMENT,
    affprogram_id    INT, 
    rating           BIGINT,
    query            VARCHAR(255),
    constraint search_query_stat_pk            PRIMARY KEY (id),
    constraint search_query_stat_affprogram_fk  foreign key (affprogram_id) references aff_program(id) on delete cascade
);

CREATE TABLE billing_project_spec (
  id		    int		    not null auto_increment,
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
   id		    int(11)	    not null auto_increment,
   project_id	    int(11)	    default null,
   data_url	    varchar(512)    not null,
   method	    varchar(8)	    not null,
   name		    varchar(255)    default null,
   num_pages	    int(11)	    default null,
   page_param	    varchar(512)    default null,
   index	    dsprojidx	    (project_id),
   constraint blngdataspec_pk		primary key (id),
   constraint blngdataspec_project_fk	foreign key (project_id) references billing_project_spec (id)  on delete cascade) ;

CREATE TABLE selectable_data_item (
  id		    int(11) not null auto_increment,
  dataspec_id	    int(11) default null,
  name		    varchar(256) not null,
  selector	    varchar(256) not null,
  index		    sidsidx (dataspec_id),
  constraint blngdselectableitem_pk primary key (id),
  constraint blngdselectableitem_dataspec_fk foreign key (dataspec_id) references billing_data_spec (id)  on delete cascade);

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
insert into authorities  select id ,'ROLE_USER'	 from affiliate where affiliateName='vasya';
insert into authorities  select id ,'ROLE_ADMIN' from affiliate where affiliateName='hacker';

insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='ilya' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='nina' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='misha' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='root' ;
insert into affprog_group(affiliate_id,group_name,description) select id,'Default','Default Group' from affiliate where affiliateName='vasya' ;
-----------------------------------default data-------------------------------------------------------------------------------

commit;