package site.shaerware.store.api.documents

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.DocumentAPI
import site.shaerware.store.api.contracts.DocumentByIdAPI
import site.shaerware.store.api.documents.dto.LayoutingListRs
import site.shaerware.store.api.documents.dto.LayoutingReadRs
import site.shaerware.store.api.documents.dto.LayoutingRq
import site.shaerware.store.db.models.docs.Layouting
import java.util.*

@Resource("/layouting")
class LayoutingAPI(
    override val from: String? = null,
    override val to: String? = null,
) : DocumentAPI<Layouting, LayoutingReadRs, LayoutingListRs, LayoutingRq> {
    override fun rs(dbo: Layouting): LayoutingReadRs {
        return LayoutingReadRs(dbo)
    }

    override fun <ID> get(id: ID): Layouting {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Layouting::class.java, uuid)!!
    }

    override fun list(): List<LayoutingListRs> {
        var query = DB.find(Layouting::class.java)
        query = applyPeriod(query, "date")
        return query.findList().map { LayoutingListRs(it) }
    }

    @Resource("{id}")
    class Id(
        override val parent: LayoutingAPI = LayoutingAPI(),
        override val id: String
    ) : DocumentByIdAPI<String, LayoutingAPI, Layouting, LayoutingReadRs, LayoutingListRs, LayoutingRq, LayoutingRq>

}