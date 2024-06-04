package site.shaerware.store.api.documents.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.documents.LayoutingAPI
import site.shaerware.store.db.models.docs.Layouting
import java.util.*

data class LayoutingListRs(
    val id: UUID,
    val date: Date,
    val code: String,
    val active: Boolean,
    val incomingId: UUID,
    val storeId: UUID,
) : ListDTO<Layouting>, RS<LayoutingAPI> {
    constructor(model: Layouting) : this(
        model.id,
        model.date,
        model.code,
        model.active,
        model.incoming.id,
        model.store.id
    )
}
