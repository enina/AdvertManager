use adman

---------- spring security tables ------------------
drop table authorities       ;
go
drop table group_members     ;
go
drop table group_authorities ;
go
drop table groups            ;
go

---------- spring security tables ------------------


-------------------------------------------------------------------------------------------------------------------

drop table access_log     ;
go

drop table purchase_order  ;
go

drop table affprogram_partner        ;
go

drop table partner        ;
go

drop table behavior_stats ;
go

drop table access_source  ;
go

drop table search_query_stat ;
go

drop table aff_program    ;
go
drop table affprog_group  ;
go

drop table affiliate      ;
go


drop table selectable_data_item  ;
go
drop table billing_data_spec     ;
go
drop table billing_project_spec  ;
go


------------------ application tables --------------



create table affiliate (
    id		  int 	not null identity,
    affiliatename nvarchar(256)  not null ,
    email         nvarchar(256)  not null ,
    password      nvarchar(50)   not null,
    enabled       bit	        not null,
    constraint affiliate_pk         primary key (id),
    constraint affiliate_name_uq    unique (affiliatename),
    constraint affiliate_email_uq   unique (email) );
go
create table affprog_group(
    id		    int not null identity,
    affiliate_id    int not null ,
    group_name	    nvarchar(256),
    description	    nvarchar(256),
    constraint program_group_pk primary key (id),
    constraint affprggroup_uq   unique (affiliate_id,group_name),
    constraint program_group_affiliate_fk foreign key (affiliate_id) references affiliate(id) on delete cascade);
go
create table aff_program (
    id					int not null identity,
    program_group_id	int not null,
    name nvarchar(256)	not null,
    description			nvarchar(256),
    username            nvarchar(256),
    password            nvarchar(256),
    sync_status			int not null,
    affprogram_link		nvarchar(256) not null,
    redirect_link		nvarchar(256) null,
    constraint program_pk primary key (id),
    constraint affprg_uq   unique  (program_group_id,name),
    constraint program_program_group_fk foreign key (program_group_id)  references affprog_group(id) on delete cascade);
    
go

create table access_source (
    id										int not null identity,
    access_source_domain					nvarchar(256) not null ,
    description								nvarchar(256) not null ,
    constraint accesssource_domain_uq		unique (access_source_domain),
    constraint accesssource_pk				primary key (id));

go

create table partner (
    id          int					not null identity,
    name        nvarchar(64)			not null ,
    email       nvarchar(256)		not null ,
    constraint partner_pk			primary key (id),
    constraint partner_email_uq		unique (email));

go
create table purchase_order (
    id					int not null  identity,
    affprogram_id	    int not null ,
    partner_id		    int ,
    status				nvarchar(256)  ,--order status
    original_order_id	nvarchar(256),
    tracking_id		    nvarchar(256),
    ip_address		    nvarchar(256),
    ordertime		    datetime , --vremya zakaza
    po_sum				float,
    commision		    float,
    currency		    nvarchar(16),
    country				nvarchar(256),
    city				nvarchar(256),
    cn  				nvarchar(256),
    cc  				nvarchar(2),
    access_amount       int,
    constraint purchaseorder_pk primary key (id),
    constraint purchaseorder_affprogram_fk foreign key (affprogram_id)  references aff_program(id) on delete cascade,
    constraint purchaseorder_partner_fk foreign key (partner_id)  references partner(id) on delete cascade);

go

create index poAffProgIdx on purchase_order(affprogram_id);
go

create index poIpIdx      on purchase_order(ip_address);
go

create index poTimeIdx    on purchase_order(ordertime);
go

create index poCNIdx      on purchase_order(cn); 
go

create table access_log (
    id					int not null identity,
    affprogram_id	    int not null    ,
    source_domain_id	int		    ,
    po_id       	    int		    ,
    access_time		    datetime	    , --vremya perehoda
    ip_address		    nvarchar(256)    , --client ip address
    country_name	    nvarchar(384)   , --client country by ip address
    target_url		    nvarchar(256)    ,
    referer_url		    nvarchar(384)    ,
    query				nvarchar(256)    ,
    constraint accesslog_affprogram_fk      foreign key (affprogram_id)     references aff_program(id)      on delete cascade,
    constraint accesslog_sourcedomain_fk    foreign key (source_domain_id)  references access_source(id)    on delete cascade,
    constraint accesslog_po_fk              foreign key (po_id)             references purchase_order(id)   ON DELETE NO ACTION,
    constraint accesslog_pk                 primary key (id)
);
go

create    index		    accessAffProgIdx on access_log(affprogram_id);
go

create    index		    accessSourceDomainIdx on access_log(source_domain_id);
go

create    index		    accessIpIdx on access_log(ip_address);
go

create    index		    accessTimeIdx on access_log(access_time);
go

create    index		    accessCNIdx on access_log(country_name);
go

CREATE TABLE behavior_stats (
    id               int NOT NULL identity,
    affprogram_id    INT, 
    domain_id        INT,
    stat_type        nvarchar(32),
    cn				 nvarchar(255),
    cc			     nvarchar(4),
    access_amount    int,
    purchase_amount  int,
    total_commision  float, 

	constraint behavior_stats_pk            PRIMARY KEY (id),
	constraint behaviorstats_affprogram_fk  foreign key (affprogram_id) references aff_program(id) on delete cascade,
	constraint behaviorstats_domain_fk      foreign key (domain_id) references access_source(id) on delete cascade
);
go

create    index           fbsStatTypeIdx on behavior_stats(stat_type);
go

create    index           fbsProgIdIdx on behavior_stats(affprogram_id);
go

create    index           fbsDomainIdx on behavior_stats(domain_id);
go

create    index           fbsCountryNameIdx on behavior_stats(cn);
go


CREATE TABLE search_query_stat(
    id               int NOT NULL identity,
    affprogram_id    int, 
    rating           int,
    query            nvarchar(255),
    constraint query_stat_pk            PRIMARY KEY (id),
    constraint query_stat_affprogram_fk  foreign key (affprogram_id) references aff_program(id) on delete cascade
);
go

CREATE TABLE billing_project_spec (
  id				int				not null identity,
  base_url			nvarchar(1024)   not null,
  cookie_name		nvarchar(32)	    not null,
  home_page			nvarchar(256)    not null,
  login_url			nvarchar(256)    not null,
  logout_url		nvarchar(256)    not null,
  method			nvarchar(8)	    not null,
  name				nvarchar(256)    not null,
  password_field    nvarchar(16)	    not null,
  selector			nvarchar(256)    not null,
  user_field	    nvarchar(64)	    not null,
  constraint blngprojectds_pk primary key (id)
);
go

CREATE TABLE billing_data_spec (
   id				int				not null identity,
   project_id	    int 			default null,
   data_url			nvarchar(512)    not null,
   method			nvarchar(8)	    not null,
   name				nvarchar(255)    default null,
   num_pages	    int 			default null,
   page_param	    nvarchar(512)    default null,
   constraint blngdataspec_pk		primary key (id),
   constraint blngdataspec_project_fk	foreign key (project_id) references billing_project_spec (id)  on delete cascade
);
go

create   index	    dsprojidx	    on billing_data_spec(project_id);
go

CREATE TABLE selectable_data_item (
  id				int			 not null identity,
  dataspec_id	    int          default null,
  name				nvarchar(256) not null,
  selector			nvarchar(256) not null,
  constraint blng_si_pk primary key (id),
  constraint blng_si_ds_fk foreign key (dataspec_id) references billing_data_spec (id)  on delete cascade
);
go

create index	    sidsidx on selectable_data_item(dataspec_id);
go

--------------------------------------------------- Spring Security tables ---------------------------------------------------
create table authorities (
    user_id     int not null,
    authority   nvarchar(50) not null,
    constraint fk_authorities_users foreign key(user_id) references affiliate(id)
);
go

create unique index ix_auth_username on authorities (user_id,authority);
go

create table groups (
    id			int not null identity,
    group_name	nvarchar(50) not null,
    CONSTRAINT group_PK PRIMARY KEY (id)
);
go

create table group_authorities (
    group_id int		  not null,
    authority nvarchar(50) not null,
    constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);
go

create table group_members (
    id int not null identity,
    username nvarchar(50) not null,
    group_id int not null,
    CONSTRAINT group_members_PK PRIMARY KEY (id),
    constraint fk_group_members_group foreign key(group_id) references groups(id)
);
go

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

go