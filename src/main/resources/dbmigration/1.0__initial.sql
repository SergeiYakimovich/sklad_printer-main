-- apply changes
create table bank_account (
  id                            uuid not null,
  owner_id                      uuid not null,
  name                          varchar(255) not null,
  bank                          varchar(255) not null,
  bik                           varchar(255) not null,
  correspondent_acc             varchar(255) not null,
  account_number                varchar(255) not null,
  constraint uq_bank_account_owner_id_name unique (owner_id,name),
  constraint pk_bank_account primary key (id)
);

create table cell (
  id                            uuid not null,
  owner_id                      uuid not null,
  code                          varchar(255) not null,
  depth                         decimal(16,3),
  width                         decimal(16,3),
  height                        decimal(16,3),
  max_weight                    decimal(16,3),
  max_volume                    decimal(16,3),
  constraint uq_cell_owner_id_code unique (owner_id,code),
  constraint pk_cell primary key (id)
);

create table contract (
  id                            uuid not null,
  owner_id                      uuid not null,
  kind                          integer not null,
  code                          varchar(255) not null,
  name                          varchar(255),
  date                          timestamptz not null,
  expiration                    timestamptz,
  deferment                     integer,
  constraint ck_contract_kind check ( kind in (1,2)),
  constraint uq_contract_owner_id_code unique (owner_id,code),
  constraint uq_contract_owner_id_kind_code unique (owner_id,kind,code),
  constraint pk_contract primary key (id)
);

create table counterparty (
  id                            uuid not null,
  name                          varchar(255) not null,
  full_name                     varchar(255),
  legal_form                    varchar(255) not null,
  inn                           varchar(12),
  kpp                           varchar(9),
  okpo                          varchar(14),
  ogrn                          varchar(13),
  registration                  timestamptz,
  address_legal                 varchar(255),
  address_actual                varchar(255),
  email                         varchar(255),
  email_for_doc                 varchar(255),
  site                          varchar(255),
  phone                         varchar(255),
  director_general              varchar(255),
  accountant_general            varchar(255),
  constraint pk_counterparty primary key (id)
);

create table document (
  id                            uuid not null,
  date                          timestamptz default current_timestamp not null,
  type                          varchar(9) not null,
  code                          varchar(255) not null,
  active                        boolean default false not null,
  constraint ck_document_type check ( type in ('Incoming','Layouting','Ordering')),
  constraint pk_document primary key (id)
);

create table auth_documents (
  id                            uuid not null,
  owner_id                      uuid not null,
  entity                        varchar(9) not null,
  allow_create                  boolean,
  allow_read                    boolean,
  allow_update                  boolean,
  allow_activate                boolean,
  allow_delete                  boolean,
  allow_list                    boolean,
  constraint ck_auth_documents_entity check ( entity in ('Incoming','Layouting','Ordering')),
  constraint uq_auth_documents_owner_id_entity unique (owner_id,entity),
  constraint pk_auth_documents primary key (id)
);

create table incoming (
  id                            uuid not null,
  date                          timestamptz default current_timestamp not null,
  code                          varchar(255) not null,
  active                        boolean default false not null,
  counterparty_id               uuid not null,
  contract_id                   uuid not null,
  currency                      varchar(3),
  store_id                      uuid,
  constraint pk_incoming primary key (id)
);

create table incoming_item (
  owner_id                      uuid not null,
  order_no                      integer not null,
  oem_code                      varchar(255),
  product_id                    uuid not null,
  quantity                      decimal(16,3) default 0 not null,
  price                         decimal(16,3) default 0 not null,
  weight                        decimal(16,3) default 0 not null,
  cost                          decimal(16,3) default 0 not null,
  lot_id                        uuid,
  constraint pk_incoming_item primary key (owner_id,order_no)
);

create table layouting (
  id                            uuid not null,
  date                          timestamptz default current_timestamp not null,
  code                          varchar(255) not null,
  active                        boolean default false not null,
  incoming_id                   uuid not null,
  store_id                      uuid not null,
  constraint pk_layouting primary key (id)
);

create table layouting_item (
  owner_id                      uuid not null,
  order_no                      integer not null,
  from_id                       uuid,
  to_id                         uuid,
  product_id                    uuid not null,
  lot_id                        uuid,
  quantity                      decimal(16,3) not null,
  constraint pk_layouting_item primary key (owner_id,order_no)
);

create table lot (
  id                            uuid not null,
  owner_id                      uuid not null,
  date                          timestamptz not null,
  document_id                   uuid not null,
  oem_code                      varchar(255),
  barcode                       varchar(255),
  incoming_quantity             decimal(16,3) not null,
  price                         decimal(16,3) not null,
  price_currency                varchar(3),
  weight                        decimal(16,3),
  cost                          decimal(16,3),
  cost_currency                 varchar(3),
  constraint uq_lot_owner_id_barcode unique (owner_id,barcode),
  constraint pk_lot primary key (id)
);

create table ordering (
  id                            uuid not null,
  date                          timestamptz default current_timestamp not null,
  code                          varchar(255) not null,
  active                        boolean default false not null,
  counterparty_id               uuid not null,
  contract_id                   uuid not null,
  currency                      varchar(3) not null,
  store_id                      uuid not null,
  constraint pk_ordering primary key (id)
);

create table ordering_item (
  owner_id                      uuid not null,
  order_no                      integer not null,
  product_id                    uuid not null,
  quantity                      decimal(16,3) not null,
  price                         decimal(16,3) not null,
  summa                         decimal(16,3) not null,
  cell_id                       uuid,
  lot_id                        uuid,
  constraint uq_ordering_item_owner_id_order_no unique (owner_id,order_no),
  constraint pk_ordering_item primary key (owner_id,order_no)
);

create table product (
  id                            uuid not null,
  name                          varchar(255) not null,
  vendor_id                     uuid not null,
  oem_code                      varchar(255),
  sku                           varchar(255),
  barcode                       varchar(255),
  constraint pk_product primary key (id)
);

create table product_image (
  id                            uuid not null,
  owner_id                      uuid not null,
  file                          bytea not null,
  type                          varchar(255),
  mime_type                     varchar(255) default 'application/octet-stream' not null,
  constraint pk_product_image primary key (id)
);

create table auth_references (
  id                            uuid not null,
  owner_id                      uuid not null,
  entity                        varchar(12) not null,
  allow_create                  boolean,
  allow_read                    boolean,
  allow_update                  boolean,
  allow_delete                  boolean,
  allow_list                    boolean,
  constraint ck_auth_references_entity check ( entity in ('Vendor','Product','Lot','Counterparty','Contract','BankAccount','Store','Cell','User','Role')),
  constraint uq_auth_references_owner_id_entity unique (owner_id,entity),
  constraint pk_auth_references primary key (id)
);

create table auth_reports (
  id                            uuid not null,
  owner_id                      uuid not null,
  report                        varchar(7) not null,
  allow_use                     boolean,
  constraint ck_auth_reports_report check ( report in ('Version')),
  constraint uq_auth_reports_owner_id_report unique (owner_id,report),
  constraint pk_auth_reports primary key (id)
);

create table auth_roles (
  id                            uuid not null,
  name                          varchar(255) not null,
  constraint uq_auth_roles_name unique (name),
  constraint pk_auth_roles primary key (id)
);

create table store (
  id                            uuid not null,
  name                          varchar(255) not null,
  constraint uq_store_name unique (name),
  constraint pk_store primary key (id)
);

create table auth_users (
  id                            uuid not null,
  name                          varchar(255) not null,
  password_hash                 varchar(255) not null,
  constraint uq_auth_users_name unique (name),
  constraint pk_auth_users primary key (id)
);

create table auth_user_roles (
  owner_id                      uuid not null,
  role_id                       uuid not null,
  constraint uq_auth_user_roles_owner_id_role_id unique (owner_id,role_id)
);

create table vendor (
  id                            uuid not null,
  name                          varchar(255) not null,
  constraint uq_vendor_name unique (name),
  constraint pk_vendor primary key (id)
);

-- foreign keys and indices
create index ix_bank_account_owner_id on bank_account (owner_id);
alter table bank_account add constraint fk_bank_account_owner_id foreign key (owner_id) references counterparty (id) on delete restrict on update restrict;

create index ix_cell_owner_id on cell (owner_id);
alter table cell add constraint fk_cell_owner_id foreign key (owner_id) references store (id) on delete restrict on update restrict;

create index ix_contract_owner_id on contract (owner_id);
alter table contract add constraint fk_contract_owner_id foreign key (owner_id) references counterparty (id) on delete restrict on update restrict;

create index ix_auth_documents_owner_id on auth_documents (owner_id);
alter table auth_documents add constraint fk_auth_documents_owner_id foreign key (owner_id) references auth_roles (id) on delete restrict on update restrict;

create index ix_incoming_counterparty_id on incoming (counterparty_id);
alter table incoming add constraint fk_incoming_counterparty_id foreign key (counterparty_id) references counterparty (id) on delete restrict on update restrict;

create index ix_incoming_contract_id on incoming (contract_id);
alter table incoming add constraint fk_incoming_contract_id foreign key (contract_id) references contract (id) on delete restrict on update restrict;

create index ix_incoming_store_id on incoming (store_id);
alter table incoming add constraint fk_incoming_store_id foreign key (store_id) references store (id) on delete restrict on update restrict;

create index ix_incoming_item_owner_id on incoming_item (owner_id);
alter table incoming_item add constraint fk_incoming_item_owner_id foreign key (owner_id) references incoming (id) on delete restrict on update restrict;

create index ix_incoming_item_product_id on incoming_item (product_id);
alter table incoming_item add constraint fk_incoming_item_product_id foreign key (product_id) references product (id) on delete restrict on update restrict;

create index ix_incoming_item_lot_id on incoming_item (lot_id);
alter table incoming_item add constraint fk_incoming_item_lot_id foreign key (lot_id) references lot (id) on delete restrict on update restrict;

create index ix_layouting_incoming_id on layouting (incoming_id);
alter table layouting add constraint fk_layouting_incoming_id foreign key (incoming_id) references incoming (id) on delete restrict on update restrict;

create index ix_layouting_store_id on layouting (store_id);
alter table layouting add constraint fk_layouting_store_id foreign key (store_id) references store (id) on delete restrict on update restrict;

create index ix_layouting_item_owner_id on layouting_item (owner_id);
alter table layouting_item add constraint fk_layouting_item_owner_id foreign key (owner_id) references layouting (id) on delete restrict on update restrict;

create index ix_layouting_item_from_id on layouting_item (from_id);
alter table layouting_item add constraint fk_layouting_item_from_id foreign key (from_id) references cell (id) on delete restrict on update restrict;

create index ix_layouting_item_to_id on layouting_item (to_id);
alter table layouting_item add constraint fk_layouting_item_to_id foreign key (to_id) references cell (id) on delete restrict on update restrict;

create index ix_layouting_item_product_id on layouting_item (product_id);
alter table layouting_item add constraint fk_layouting_item_product_id foreign key (product_id) references product (id) on delete restrict on update restrict;

create index ix_layouting_item_lot_id on layouting_item (lot_id);
alter table layouting_item add constraint fk_layouting_item_lot_id foreign key (lot_id) references lot (id) on delete restrict on update restrict;

create index ix_lot_owner_id on lot (owner_id);
alter table lot add constraint fk_lot_owner_id foreign key (owner_id) references product (id) on delete restrict on update restrict;

create index ix_lot_document_id on lot (document_id);
alter table lot add constraint fk_lot_document_id foreign key (document_id) references document (id) on delete restrict on update restrict;

create index ix_ordering_counterparty_id on ordering (counterparty_id);
alter table ordering add constraint fk_ordering_counterparty_id foreign key (counterparty_id) references counterparty (id) on delete restrict on update restrict;

create index ix_ordering_contract_id on ordering (contract_id);
alter table ordering add constraint fk_ordering_contract_id foreign key (contract_id) references contract (id) on delete restrict on update restrict;

create index ix_ordering_store_id on ordering (store_id);
alter table ordering add constraint fk_ordering_store_id foreign key (store_id) references store (id) on delete restrict on update restrict;

create index ix_ordering_item_owner_id on ordering_item (owner_id);
alter table ordering_item add constraint fk_ordering_item_owner_id foreign key (owner_id) references ordering (id) on delete restrict on update restrict;

create index ix_ordering_item_product_id on ordering_item (product_id);
alter table ordering_item add constraint fk_ordering_item_product_id foreign key (product_id) references product (id) on delete restrict on update restrict;

create index ix_ordering_item_cell_id on ordering_item (cell_id);
alter table ordering_item add constraint fk_ordering_item_cell_id foreign key (cell_id) references cell (id) on delete restrict on update restrict;

create index ix_ordering_item_lot_id on ordering_item (lot_id);
alter table ordering_item add constraint fk_ordering_item_lot_id foreign key (lot_id) references lot (id) on delete restrict on update restrict;

create index ix_product_vendor_id on product (vendor_id);
alter table product add constraint fk_product_vendor_id foreign key (vendor_id) references vendor (id) on delete restrict on update restrict;

create index ix_product_image_owner_id on product_image (owner_id);
alter table product_image add constraint fk_product_image_owner_id foreign key (owner_id) references product (id) on delete restrict on update restrict;

create index ix_auth_references_owner_id on auth_references (owner_id);
alter table auth_references add constraint fk_auth_references_owner_id foreign key (owner_id) references auth_roles (id) on delete restrict on update restrict;

create index ix_auth_reports_owner_id on auth_reports (owner_id);
alter table auth_reports add constraint fk_auth_reports_owner_id foreign key (owner_id) references auth_roles (id) on delete restrict on update restrict;

create index ix_auth_user_roles_owner_id on auth_user_roles (owner_id);
alter table auth_user_roles add constraint fk_auth_user_roles_owner_id foreign key (owner_id) references auth_users (id) on delete restrict on update restrict;

create index ix_auth_user_roles_role_id on auth_user_roles (role_id);
alter table auth_user_roles add constraint fk_auth_user_roles_role_id foreign key (role_id) references auth_roles (id) on delete restrict on update restrict;

create index if not exists ix_bank_account_owner_id_bank on bank_account (owner_id,bank);
create index if not exists ix_bank_account_bik on bank_account (bik);
create index if not exists ix_bank_account_account_number on bank_account (account_number);
create index if not exists ix_lot_date on lot (date);
create index if not exists ix_lot_oem_code on lot (oem_code);
create index if not exists ix_product_barcode on product (barcode);
create index if not exists ix_product_oem_code on product (oem_code);
create index if not exists ix_product_sku on product (sku);
create index if not exists ix_product_vendor_id_oem_code on product (vendor_id,oem_code);
