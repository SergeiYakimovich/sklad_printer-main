package site.shaerware.store.api.documents.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.documents.OrderingAPI
import site.shaerware.store.db.models.docs.Ordering
import java.util.*

data class OrderingListRs(
    val id: UUID,
    val date: Date,
    val code: String,
    val active: Boolean,
    val counterpartyId: UUID,
    val contractId: UUID,
    val currency: Currency,
    val storeId: UUID,
) : RS<OrderingAPI>, ListDTO<Ordering> {
    constructor(model: Ordering) : this(
        model.id,
        model.date,
        model.code,
        model.active,
        model.counterparty.id,
        model.contract.id,
        model.currency,
        model.store.id
    )
}
