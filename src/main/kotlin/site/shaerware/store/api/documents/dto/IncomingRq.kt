package site.shaerware.store.api.documents.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.documents.IncomingAPI
import site.shaerware.store.api.documents.dto.items.IncomingITO
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.docs.Document
import site.shaerware.store.db.models.docs.Incoming
import site.shaerware.store.db.models.refs.Contract
import site.shaerware.store.db.models.refs.Counterparty
import site.shaerware.store.db.models.refs.Store
import java.util.*

data class IncomingRq(
    val date: Date,
    val code: String,
    val active: Boolean,
    val counterpartyId: UUID,
    val contractId: UUID,
    val currency: Currency?,
    val storeId: UUID?,
    val items: List<IncomingITO>
) : CreateDTO<Incoming>, UpdateDTO<Incoming>, RQ<IncomingAPI> {
    override fun create(): Incoming {
        val store = if (storeId != null)
            DB.find(Store::class.java, storeId)
        else
            null
        val dbo = Incoming(
            date,
            code,
            active,
            DB.find(Counterparty::class.java, counterpartyId)!!,
            DB.find(Contract::class.java, contractId)!!,
            currency,
            store,
        )
        dbo.items = items.map { it.create_or_update(dbo) }
        return dbo
    }

    override fun apply(model: Incoming) {
        model.date = date
        model.code = code
        model.active = active
        model.counterparty = DB.find(Counterparty::class.java, counterpartyId)!!
        model.contract = DB.find(Contract::class.java, contractId)!!
        model.currency = currency
        if (storeId != null)
            model.store = DB.find(Store::class.java, storeId)!!
        else
            model.store = null
        model.items = items.map { it.create_or_update(model) }

    }
}
