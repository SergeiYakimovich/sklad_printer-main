package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.CreateSubDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.db.models.refs.Contract
import site.shaerware.store.db.models.refs.ContractKind
import site.shaerware.store.db.models.refs.Counterparty
import java.util.*

data class ContractRq(
    val kind: ContractKind,
    val code: String,
    val name: String?,
    val date: Date,
    val expiration: Date?,
    val deferment: Int?
): RQ<CounterpartyAPI.Id.ContractAPI>, CreateSubDTO<Contract, Counterparty>, UpdateDTO<Contract> {
    override fun create(owner: Counterparty): Contract {
        return Contract(owner, kind, code, name, date, expiration, deferment)
    }

    override fun apply(model: Contract) {
        model.kind = kind
        model.code = code
        model.name = name
        model.date = date
        model.expiration = expiration
        model.deferment = deferment
    }
}
