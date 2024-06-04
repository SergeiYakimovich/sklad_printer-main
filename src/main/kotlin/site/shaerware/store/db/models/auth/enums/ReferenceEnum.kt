package site.shaerware.store.db.models.auth.enums

import site.shaerware.store.api.auth.RoleAPI
import site.shaerware.store.api.auth.UserAPI
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.api.print.PrintAPI.Id.PrinterAPI
import site.shaerware.store.api.print.PrintAPI.PrintJobsAPI
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.BankAccountAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.ContractAPI
import site.shaerware.store.api.references.ProductAPI
import site.shaerware.store.api.references.ProductAPI.Id.LotAPI
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.api.references.StoreAPI.Id.CellAPI
import site.shaerware.store.api.references.VendorAPI
import kotlin.reflect.KClass

enum class ReferenceEnum(val cls: KClass<*>, val title: String) {
    Vendor(VendorAPI::class, "Производитель"),
    Product(ProductAPI::class, "Товар"),
    Lot(LotAPI::class, "Партия"),
    Counterparty(CounterpartyAPI::class, "Контрагент"),
    Contract(ContractAPI::class, "Договор"),
    BankAccount(BankAccountAPI::class, "Банковский счет"),
    Store(StoreAPI::class, "Склад"),
    Cell(CellAPI::class, "Ячейка"),

    User(UserAPI::class, "Пользователь"),
    Role(RoleAPI::class, "Группа (набор прав)"),

    Host(PrintAPI::class, "Принт-сервис"),
    Printer(PrinterAPI::class, "Принтер"),
    PrintJobs(PrintJobsAPI::class, "Задачи печати"),
}