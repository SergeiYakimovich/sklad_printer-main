package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.db.models.refs.Store
import java.util.UUID

data class StoreRs(
    val id: UUID,
    val name: String,
    val cells: List<CellRs>
):RS<StoreAPI>, ReadDTO<Store>, ListDTO<Store>
{
    constructor(model: Store):this(
        model.id,
        model.name,
        model.cells.map { c -> CellRs(c) }
    )
}
