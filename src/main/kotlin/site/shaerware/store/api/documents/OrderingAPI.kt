package site.shaerware.store.api.documents

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.DocumentAPI
import site.shaerware.store.api.contracts.DocumentByIdAPI
import site.shaerware.store.api.documents.dto.OrderingListRs
import site.shaerware.store.api.documents.dto.OrderingReadRs
import site.shaerware.store.api.documents.dto.OrderingRq
import site.shaerware.store.db.models.docs.Ordering
import java.util.*

@Resource("/ordering")
class OrderingAPI(
    override val from: String? = null,
    override val to: String? = null,
) : DocumentAPI<Ordering, OrderingReadRs, OrderingListRs, OrderingRq> {

    override fun rs(dbo: Ordering): OrderingReadRs {
        return OrderingReadRs(dbo)
    }

    override fun <ID> get(id: ID): Ordering {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Ordering::class.java, uuid)!!
    }

    override fun list(): List<OrderingListRs> {
        var query = DB.find(Ordering::class.java)
        query = applyPeriod(query, "date")
        return query.findList().map { OrderingListRs(it) }
    }

    @Resource("{id}")
    class Id(
        override val parent: OrderingAPI = OrderingAPI(),
        override val id: String
    ) : DocumentByIdAPI<String, OrderingAPI, Ordering, OrderingReadRs, OrderingListRs, OrderingRq, OrderingRq>

}
