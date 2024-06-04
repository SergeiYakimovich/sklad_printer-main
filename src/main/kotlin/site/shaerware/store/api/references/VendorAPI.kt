package site.shaerware.store.api.references

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.contracts.EntityByIdAPI
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.api.references.dto.VendorRq
import site.shaerware.store.api.references.dto.VendorRs
import site.shaerware.store.db.models.refs.Vendor
import java.util.UUID

@Resource("/vendor")
class VendorAPI(
    override val search: String? = null
) : EntityAPI<Vendor, VendorRs, VendorRs, VendorRq>, Search<Vendor> {

    override fun rs(dbo: Vendor): VendorRs {
        return VendorRs(dbo)
    }

    override fun <ID> get(id: ID): Vendor {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Vendor::class.java, uuid)!!
    }

    override fun list(): List<VendorRs> {
        var query = DB.find(Vendor::class.java)
        query = applySearch(query, "name", Search.Mode.Middle, false)
        return query.findList().map { v -> rs(v) }
    }

    @Resource("{id}")
    class Id(
        override val parent: VendorAPI = VendorAPI(),
        override val id: String
    ) : EntityByIdAPI<String, VendorAPI, Vendor, VendorRs, VendorRs, VendorRq, VendorRq>

}