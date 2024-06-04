package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.db.models.refs.Store

data class StoreUpdateRq(
    val name: String
) : UpdateDTO<Store>, RQ<StoreAPI> {
    override fun apply(model: Store) {
        model.name = name
    }
}
