package site.shaerware.store.api.documents.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.documents.IncomingAPI
import site.shaerware.store.db.models.docs.Incoming
import java.util.*

data class IncomingListRs(
    val id: UUID,
    val date: Date,
    val code: String,
    val active: Boolean,
    val counterpartyId: UUID,
    val contractId: UUID,
    val currency: Currency?,
    val storeId: UUID?,
) : ListDTO<Incoming>, RS<IncomingAPI> {
    constructor(model: Incoming) : this(
        model.id,
        model.date,
        model.code,
        model.active,
        model.counterparty.id,
        model.contract.id,
        model.currency,
        model.store?.id
    )
}
