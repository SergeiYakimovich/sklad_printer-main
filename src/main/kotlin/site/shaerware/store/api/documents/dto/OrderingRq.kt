package site.shaerware.store.api.documents.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.documents.OrderingAPI
import site.shaerware.store.api.documents.dto.items.OrderingITO
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.docs.Document
import site.shaerware.store.db.models.docs.Ordering
import site.shaerware.store.db.models.refs.Contract
import site.shaerware.store.db.models.refs.Counterparty
import site.shaerware.store.db.models.refs.Store
import java.util.*

data class OrderingRq(
    val date: Date,
    val code: String,
    val active: Boolean,
    val counterpartyId: UUID,
    val contractId: UUID,
    val currency: Currency,
    val storeId: UUID,
    val items: List<OrderingITO>,
) : RQ<OrderingAPI>, CreateDTO<Ordering>, UpdateDTO<Ordering> {
    override fun create(): Ordering {
        val dbo = Ordering(
            date,
            code,
            active,
            DB.find(Counterparty::class.java, counterpartyId)!!,
            DB.find(Contract::class.java, contractId)!!,
            currency,
            DB.find(Store::class.java, storeId)!!
        )
        dbo.items = items.map { it.create_or_update(dbo) }
        return dbo
    }

    override fun apply(model: Ordering) {
        model.date = date
        model.code = code
        model.active = active
        model.counterparty = DB.find(Counterparty::class.java, counterpartyId)!!
        model.contract = DB.find(Contract::class.java, contractId)!!
        model.currency = currency
        model.store = DB.find(Store::class.java, storeId)!!
        model.items = items.map { it.create_or_update(model) }
    }
}
