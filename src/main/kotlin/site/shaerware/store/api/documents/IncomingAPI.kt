package site.shaerware.store.api.documents

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.DocumentAPI
import site.shaerware.store.api.contracts.DocumentByIdAPI
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.documents.dto.IncomingListRs
import site.shaerware.store.db.models.docs.Incoming
import site.shaerware.store.api.documents.dto.IncomingReadRs
import site.shaerware.store.api.documents.dto.IncomingRq
import java.util.UUID

@Resource("/incoming")
class IncomingAPI(
    override val from: String? = null,
    override val to: String? = null
) : DocumentAPI<Incoming, IncomingReadRs, IncomingListRs, IncomingRq> {
    override fun rs(dbo: Incoming): IncomingReadRs {
        return IncomingReadRs(dbo)
    }

    override fun list(): List<IncomingListRs> {
        var query = DB.find(Incoming::class.java)
        query = applyPeriod(query, "date")
        return query.findList().map { IncomingListRs(it) }
    }

    override fun <ID> get(id: ID): Incoming {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Incoming::class.java, uuid)!!
    }

    @Resource("{id}")
    class Id(
        override val parent: IncomingAPI = IncomingAPI(),
        override val id: String
    ) : DocumentByIdAPI<String, IncomingAPI, Incoming, IncomingReadRs, IncomingListRs, IncomingRq, IncomingRq>
}