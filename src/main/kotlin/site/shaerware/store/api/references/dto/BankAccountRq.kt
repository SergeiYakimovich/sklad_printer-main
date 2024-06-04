package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateSubDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.BankAccountAPI
import site.shaerware.store.db.models.refs.BankAccount
import site.shaerware.store.db.models.refs.Counterparty

data class BankAccountRq(
    val name : String,
    val bank: String,
    val bik: String,
    val correspondentAcc: String,
    val accountNumber :String
):CreateSubDTO<BankAccount, Counterparty>, UpdateDTO<BankAccount>, RQ<BankAccountAPI> {
    override fun create(owner: Counterparty): BankAccount {
        return BankAccount(owner, name, bank, bik, correspondentAcc, accountNumber)
    }

    override fun apply(model: BankAccount) {
        model.name = name
        model.bank = bank
        model.bik = bik
        model.correspondentAcc = correspondentAcc
        model.accountNumber = accountNumber
    }
}
