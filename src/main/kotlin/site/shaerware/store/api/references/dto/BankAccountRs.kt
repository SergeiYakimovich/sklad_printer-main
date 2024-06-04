package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.dto.SubEntityDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.BankAccountAPI
import site.shaerware.store.db.models.refs.BankAccount
import java.util.*

data class BankAccountRs(
    val id: UUID,
    override val ownerId: UUID,
    val name : String,
    val bank: String,
    val bik: String,
    val correspondentAcc: String,
    val accountNumber :String
) :SubEntityDTO, ReadDTO<BankAccount>, ListDTO<BankAccount>, RS<BankAccountAPI> {
    constructor(model: BankAccount):this(
        model.id,
        model.owner.id,
        model.name,
        model.bank,
        model.bik,
        model.correspondentAcc,
        model.accountNumber
    )
}
