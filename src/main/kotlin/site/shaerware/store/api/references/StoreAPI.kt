package site.shaerware.store.api.references

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.contracts.EntityByIdAPI
import site.shaerware.store.api.contracts.SubEntityAPI
import site.shaerware.store.api.contracts.SubEntityByIdAPI
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.api.references.dto.*
import site.shaerware.store.db.models.refs.Cell
import site.shaerware.store.db.models.refs.Store
import java.util.UUID

@Resource("/store")
class StoreAPI(
    override val search: String? = null
) :EntityAPI<Store, StoreRs, StoreRs, StoreCreateRq>, Search<Store> {
    override fun rs(dbo: Store): StoreRs {
        return StoreRs(dbo)
    }

    override fun <ID> get(id: ID): Store {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Store::class.java, uuid)!!
    }

    override fun list(): List<StoreRs> {
        var query = DB.find(Store::class.java)
        query = applySearch(query, "name", Search.Mode.Middle)
        return query.findList().map { s -> StoreRs(s) }
    }

    @Resource("{storeId}")
    class Id(
        override val parent: StoreAPI = StoreAPI(),
        val storeId: String,
    ) : EntityByIdAPI<String, StoreAPI, Store, StoreRs, StoreRs, StoreCreateRq, StoreUpdateRq> {

        @Resource("/cell")
        class CellAPI(
            override val parent: StoreAPI.Id,
            override val search: String? = null
        ) : SubEntityAPI<String, StoreAPI, StoreAPI.Id, Store, Cell, CellRs, CellRs, CellRq>, Search<Cell> {
            override fun rs(dbo: Cell): CellRs {
                return CellRs(dbo)
            }

            override fun <ID> get(id: ID): Cell {
                val uuid = UUID.fromString(id.toString())
                return DB.find(Cell::class.java, uuid)!!
            }

            override fun list(): List<CellRs> {
                var query = DB.find(Cell::class.java).where().eq("owner_id", parent.item().id).query()
                query = applySearch(query, "code", Search.Mode.Begin)
                return query.findList().map { c -> CellRs(c) }
            }

            @Resource("{cellId}")
            class Id(
                override val parent: CellAPI,
                val cellId: String
            ) : SubEntityByIdAPI<String, StoreAPI, StoreAPI.Id, Store, String, CellAPI, Cell, CellRs, CellRs, CellRq, CellRq> {
                override val id: String
                    get() = cellId
            }

        }

        override val id: String
            get() = storeId
    }

}