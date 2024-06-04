package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.db.models.refs.Store

class StoreCreateRq(
    val name: String,
    val cells: List<CellRq>?
):CreateDTO<Store>, RQ<StoreAPI> {
    override fun create(): Store {
        val dbo = Store(name)
        if (cells != null)
            dbo.cells = cells.map { c -> c.create(dbo) }
        return dbo
    }
}