package site.shaerware.store.api.documents.dto.items

import io.ebean.DB
import site.shaerware.store.db.models.docs.DocItemId
import site.shaerware.store.db.models.docs.Incoming
import site.shaerware.store.db.models.docs.IncomingItem
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import java.util.*

data class IncomingITO(
    val orderNo: Int,
    val oemCode: String?,
    val productId: UUID,
    val quantity: BigDecimal,
    val price: BigDecimal,
    val weight: BigDecimal,
    val cost: BigDecimal,
    val lotId: UUID?,
) {
    constructor(model: IncomingItem) : this(
        model.orderNo,
        model.oemCode,
        model.product.id,
        model.quantity,
        model.price,
        model.weight,
        model.cost,
        model.lot?.id
    )

    fun create_or_update(owner: Incoming): IncomingItem {
        var dbo = DB.find(IncomingItem::class.java)
            .where().eq("owner_id", owner.id)
            .and().eq("order_no", orderNo).findOne()
        val lot = if (lotId != null)
            DB.find(Lot::class.java, lotId)
        else
            null
        if (dbo == null) {
            dbo = IncomingItem(
                owner, orderNo,
                oemCode,
                DB.find(Product::class.java, productId)!!,
                quantity,
                price,
                weight,
                cost,
                lot
            )
        } else {
            dbo.oemCode = oemCode
            dbo.product = DB.find(Product::class.java, productId)!!
            dbo.quantity = quantity
            dbo.price = price
            dbo.weight = weight
            dbo.cost = cost
            dbo.lot = lot
        }
        return dbo
    }
}
