package site.shaerware.store.api.documents.dto.items

import io.ebean.DB
import site.shaerware.store.db.models.docs.Layouting
import site.shaerware.store.db.models.docs.LayoutingItem
import site.shaerware.store.db.models.refs.Cell
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import java.util.*

data class LayoutingITO(
    val orderNo: Int,
    val fromId: UUID?,
    val toId: UUID?,
    val productId: UUID,
    val lotId: UUID?,
    val quantity: BigDecimal,
) {
    constructor(model: LayoutingItem) : this(
        model.orderNo,
        model.from?.id,
        model.to?.id,
        model.product.id,
        model.lot?.id,
        model.quantity
    )

    fun create_or_update(owner: Layouting): LayoutingItem {
        var dbo = DB.find(LayoutingItem::class.java)
            .where().eq("owner_id", owner.id)
            .and().eq("order_no", orderNo).findOne()
        val from = if (fromId != null)
            DB.find(Cell::class.java, fromId)!!
        else
            null
        val to = if (toId != null)
            DB.find(Cell::class.java, toId)!!
        else
            null
        val lot = if (lotId != null)
            DB.find(Lot::class.java, lotId)!!
        else
            null
        if (dbo == null) {
            dbo = LayoutingItem(
                owner,
                orderNo,
                from,
                to,
                DB.find(Product::class.java, productId)!!,
                lot,
                quantity
            )
        } else {
            dbo.from = from
            dbo.to = to
            dbo.product = DB.find(Product::class.java, productId)!!
            dbo.lot = lot
            dbo.quantity = quantity
        }
        return dbo
    }
}
