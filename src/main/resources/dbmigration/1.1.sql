-- apply changes
create table host (
  id                            uuid not null,
  name                          varchar(255) not null,
  constraint uq_host_name unique (name),
  constraint pk_host primary key (id)
);

create table printer (
  id                            uuid not null,
  owner_id                      uuid not null,
  name                          varchar(255) not null,
  page_width                    float,
  page_height                   float,
  cols                          integer,
  rows                          integer,
  constraint uq_printer_owner_id_name unique (owner_id,name),
  constraint pk_printer primary key (id)
);

-- foreign keys and indices
create index ix_printer_owner_id on printer (owner_id);
alter table printer add constraint fk_printer_owner_id foreign key (owner_id) references host (id) on delete restrict on update restrict;

