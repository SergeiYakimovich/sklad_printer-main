-- apply changes
create table print_job (
  host_id                       uuid not null,
  printer_id                    uuid not null,
  init                          timestamptz not null,
  done                          timestamptz,
  data                          varchar(255) not null,
  constraint uq_print_job_host_id_printer_id_init unique (host_id,printer_id,init)
);

-- foreign keys and indices
create index ix_print_job_host_id on print_job (host_id);
alter table print_job add constraint fk_print_job_host_id foreign key (host_id) references host (id) on delete restrict on update restrict;

create index ix_print_job_printer_id on print_job (printer_id);
alter table print_job add constraint fk_print_job_printer_id foreign key (printer_id) references printer (id) on delete restrict on update restrict;

create index if not exists ix_print_job_init on print_job (init);
create index if not exists ix_print_job_host_id_printer_id_done on print_job (host_id,printer_id,done);
create index if not exists ix_print_job_done on print_job (done);
