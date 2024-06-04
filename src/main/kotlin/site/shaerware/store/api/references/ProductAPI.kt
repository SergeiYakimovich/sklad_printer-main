package site.shaerware.store.api.references

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.contracts.EntityByIdAPI
import site.shaerware.store.api.contracts.SubEntityAPI
import site.shaerware.store.api.contracts.SubEntityByIdAPI
import site.shaerware.store.api.contracts.filters.Period
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.api.references.dto.*
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.util.*

@Resource("/product")
class ProductAPI(
    override val search: String? = null,
    val vendorId: String? = null,
    val code: String? = null,
    val oem: String? = null,
    val sku: String? = null,
    val barcode: String? = null,
) : EntityAPI<Product, ProductReadRs, ProductListRs, ProductRq>, Search<Product> {

    override fun rs(dbo: Product): ProductReadRs {
        return ProductReadRs(dbo)
    }

    override fun <ID> get(id: ID): Product {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Product::class.java, uuid)!!
    }

    override fun list(): List<ProductListRs> {
        var query = DB.find(Product::class.java)
        query = applySearch(query, "name", Search.Mode.Middle, false)
        if (!vendorId.isNullOrEmpty())
            query = query.where().eq("vendor_id", UUID.fromString(vendorId)).query()
        if (!code.isNullOrEmpty()) {
            val pattern = "${code}%"
            query = query.where().ilike("oem_code", pattern)
                .or().ilike("sku", pattern).or().eq("barcode", pattern).query()
        }
        if (!oem.isNullOrEmpty())
            query = query.where().ilike("oem_code", "${oem}%").query()
        if (!sku.isNullOrEmpty())
            query = query.where().ilike("sku", "${sku}%").query()
        if (!barcode.isNullOrEmpty())
            query = query.where().eq("barcode", barcode).query()
        return query.findList().map { p -> ProductListRs(p) }
    }

    @Resource("{productId}")
    class Id(
        override val parent: ProductAPI = ProductAPI(),
        val productId: String
    ) : EntityByIdAPI<String, ProductAPI, Product, ProductReadRs, ProductListRs, ProductRq, ProductRq> {

        override val id: String
            get() = productId

        @Resource("/lot")
        class LotAPI(
            override val parent: ProductAPI.Id,
            override val from: String? = null,
            override val to: String? = null,
            val code: String? = null,
            val oem: String? = null,
            val barcode: String? = null,
        ) : SubEntityAPI<String, ProductAPI, ProductAPI.Id, Product, Lot, LotRs, LotRs, LotRq>, Period<Lot> {

            override fun rs(dbo: Lot): LotRs {
                return LotRs(dbo)
            }

            override fun <ID> get(id: ID): Lot {
                val uuid = UUID.fromString(id.toString())
                return DB.find(Lot::class.java, uuid)!!
            }

            override fun list(): List<LotRs> {
                var query = DB.find(Lot::class.java).where().eq("owner_id", parent.item().id).query()
                query = applyPeriod(query, "date")
                if (!code.isNullOrEmpty()) {
                    val pattern = "${code}%"
                    query = query.where().ilike("oem_code", pattern)
                        .or().eq("barcode", pattern).query()
                }
                if (!oem.isNullOrEmpty())
                    query = query.where().ilike("oem_code", "${oem}%").query()
                if (!barcode.isNullOrEmpty())
                    query = query.where().eq("barcode", barcode).query()
                return query.findList().map { p -> LotRs(p) }
            }

            @Resource("{lotId}")
            class Id(
                override val parent: LotAPI,
                val lotId: String
            ) :SubEntityByIdAPI<String, ProductAPI, ProductAPI.Id, Product, String, LotAPI, Lot, LotRs, LotRs, LotRq, LotRq> {
                override val id: String
                    get() = lotId
            }
        }
    }

}