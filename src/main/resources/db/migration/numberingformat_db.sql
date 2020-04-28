create sequence hibernate_sequence start with 1 increment by 1
create sequence sq_nfm_numbering_format start with 1 increment by 1
create sequence sq_nfm_reserved_number_interval start with 1 increment by 1
create table revinfo
(
    rev      number(10, 0) not null,
    revtstmp number(19, 0),
    primary key (rev)
)
create table tb_nfm_numbering_format
(
    numbering_format_id   number(19, 0)      not null,
    created_by            varchar2(255 char) not null,
    created_date          timestamp          not null,
    last_modified_by      varchar2(255 char) not null,
    last_modified_date    timestamp          not null,
    revision_comment      varchar2(255 char),
    revision_number       number(19, 0),
    transaction_id        varchar2(255 char) not null,
    last_allocated_serial number(19, 0),
    numbering_format      varchar2(400 char) not null,
    numbering_usage       varchar2(50 char)  not null,
    start_at              number(19, 0)      not null check (start_at >= 1),
    primary key (numbering_format_id)
)
create table tb_nfm_numbering_format_aud
(
    numbering_format_id   number(19, 0) not null,
    rev                   number(10, 0) not null,
    revtype               number(3, 0),
    created_by            varchar2(255 char),
    created_date          timestamp,
    last_modified_by      varchar2(255 char),
    last_modified_date    timestamp,
    revision_comment      varchar2(255 char),
    transaction_id        varchar2(255 char),
    last_allocated_serial number(19, 0),
    numbering_format      varchar2(255 char),
    numbering_usage       varchar2(255 char),
    start_at              number(19, 0),
    primary key (numbering_format_id, rev)
)
create table tb_nfm_reserved_number_interval
(
    id                  number(19, 0)      not null,
    created_by          varchar2(255 char) not null,
    created_date        timestamp          not null,
    last_modified_by    varchar2(255 char) not null,
    last_modified_date  timestamp          not null,
    revision_comment    varchar2(255 char),
    revision_number     number(19, 0),
    transaction_id      varchar2(255 char) not null,
    reserved_end        number(19, 0),
    reserved_start      number(19, 0) check (reserved_start >= 1),
    numbering_format_id number(19, 0)      not null,
    primary key (id)
)
create table tb_nfm_reserved_number_interval_aud
(
    id                  number(19, 0) not null,
    rev                 number(10, 0) not null,
    revtype             number(3, 0),
    created_by          varchar2(255 char),
    created_date        timestamp,
    last_modified_by    varchar2(255 char),
    last_modified_date  timestamp,
    revision_comment    varchar2(255 char),
    transaction_id      varchar2(255 char),
    reserved_end        number(19, 0),
    reserved_start      number(19, 0),
    numbering_format_id number(19, 0),
    primary key (id, rev)
)
alter table tb_nfm_numbering_format
    add constraint UKa4jmm8iofpcfqhogngun7wa3u unique (numbering_usage, numbering_format)
alter table tb_nfm_numbering_format_aud
    add constraint FKrybqh1yhcrv9ubtianuxs0dnu foreign key (rev) references revinfo
alter table tb_nfm_reserved_number_interval
    add constraint FK2r4pp1f1pqisyia6gryo9pfjl foreign key (numbering_format_id) references tb_nfm_numbering_format
alter table tb_nfm_reserved_number_interval_aud
    add constraint FK7l7oryj8qdrv73djvc9gf3me4 foreign key (rev) references revinfo
