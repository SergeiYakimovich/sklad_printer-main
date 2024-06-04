package site.shaerware.store.api.references.dto

import io.ebean.annotation.DbDefault
import io.ebean.config.dbplatform.DbDefaultValue
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.dto.SubEntityDTO
import site.shaerware.store.api.references.CounterpartyAPI
import site.shaerware.store.api.references.CounterpartyAPI.Id.ContractAPI
import site.shaerware.store.db.models.refs.Contract
import site.shaerware.store.db.models.refs.ContractKind
import java.util.Date
import java.util.*

data class ContractRs(
    val id : UUID,
    override val ownerId: UUID,
    val kind: ContractKind,
    val code: String,
    val name: String?,
    val date: Date,
    val expiration: Date?,
    val deferment: Int?
): SubEntityDTO, RS<ContractAPI>, ReadDTO<Contract>, ListDTO<Contract> {
    constructor(model : Contract):this(
        model.id,
        model.owner.id,
        model.kind,
        model.code,
        model.name,
        model.date,
        model.expiration,
        model.deferment
    )
}