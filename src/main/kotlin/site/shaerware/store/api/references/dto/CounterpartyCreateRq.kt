package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.db.models.refs.Counterparty
import java.util.Date

data class CounterpartyCreateRq(
    val name : String,
    val fullName: String?,
    val legalForm: String,
    val inn: String?,
    val kpp: String?,
    val okpo: String?,
    val ogrn: String?,
    val registration: Date?,
    val addressLegal: String?,
    val addressActual: String?,
    val email: String?,
    val emailForDoc: String?,
    val site: String?,
    val phone: String?,
    val directorGeneral: String?,
    val accountantGeneral: String?,
    val contracts: List<ContractRq>?,
    val bankAccounts: List<BankAccountRq>?,
):RQ<CounterpartyAPI>,CreateDTO<Counterparty> {
    override fun create(): Counterparty {
        val dbo = Counterparty(
            name,
            fullName,
            legalForm,
            inn,
            kpp,
            okpo,
            ogrn,
            registration,
            addressLegal,
            addressActual,
            email,
            emailForDoc,
            site,
            phone,
            directorGeneral,
            accountantGeneral
        )
        if (contracts != null)
            dbo.contracts = contracts.map { c -> c.create(dbo) }
        if (bankAccounts != null)
            dbo.bankAccounts = bankAccounts.map { a -> a.create(dbo) }
        return dbo
    }
}
