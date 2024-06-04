package site.shaerware.store.api.documents.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.documents.LayoutingAPI
import site.shaerware.store.api.documents.dto.items.LayoutingITO
import site.shaerware.store.db.models.docs.Layouting
import java.util.*

data class LayoutingReadRs(
    val id: UUID,
    val date: Date,
    val code: String,
    val active: Boolean,
    val incomingId: UUID,
    val storeId: UUID,
    val items: List<LayoutingITO>
) : ReadDTO<Layouting>, RS<LayoutingAPI> {
    constructor(model: Layouting) : this(
        model.id,
        model.date,
        model.code,
        model.active,
        model.incoming.id,
        model.store.id,
        model.items.map { LayoutingITO(it) }
    )
}

