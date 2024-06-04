package site.shaerware.store.api.documents.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.documents.IncomingAPI
import site.shaerware.store.api.documents.dto.items.IncomingITO
import site.shaerware.store.db.models.docs.Incoming
import java.util.*

data class IncomingReadRs(
    val id: UUID,
    val date: Date,
    val code: String,
    val active: Boolean,
    val counterpartyId: UUID,
    val contractId: UUID,
    val currency: Currency?,
    val storeId: UUID?,
    val items: List<IncomingITO>
) : ReadDTO<Incoming>, RS<IncomingAPI> {
    constructor(model: Incoming) : this(model.id,
        model.date,
        model.code,
        model.active,
        model.counterparty.id,
        model.contract.id,
        model.currency,
        model.store?.id,
        model.items.map { IncomingITO(it) })
}
