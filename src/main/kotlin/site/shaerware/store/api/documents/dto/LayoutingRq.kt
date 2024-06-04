package site.shaerware.store.api.documents.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.documents.LayoutingAPI
import site.shaerware.store.api.documents.dto.items.LayoutingITO
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.docs.Document
import site.shaerware.store.db.models.docs.Incoming
import site.shaerware.store.db.models.docs.Layouting
import site.shaerware.store.db.models.refs.Store
import java.util.*

data class LayoutingRq(
    val date: Date,
    val code: String,
    val active: Boolean,
    val incomingId: UUID,
    val storeId: UUID,
    val items: List<LayoutingITO>
) : RQ<LayoutingAPI>, CreateDTO<Layouting>, UpdateDTO<Layouting> {
    override fun create(): Layouting {
        val dbo = Layouting(
            date,
            code,
            active,
            DB.find(Incoming::class.java, incomingId)!!,
            DB.find(Store::class.java, storeId)!!
        )
        dbo.items = items.map { it.create_or_update(dbo) }
        return dbo
    }

    override fun apply(model: Layouting) {
        model.date = date
        model.code = code
        model.active = active
        model.incoming = DB.find(Incoming::class.java, incomingId)!!
        model.store = DB.find(Store::class.java, storeId)!!
        model.items = items.map { it.create_or_update(model) }
    }
}
