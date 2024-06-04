package site.shaerware.store.api.documents.dto.items

import io.ebean.DB
import site.shaerware.store.db.models.docs.Ordering
import site.shaerware.store.db.models.docs.OrderingItem
import site.shaerware.store.db.models.refs.Cell
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import java.util.*

data class OrderingITO(
    val orderNo: Int,
    val productId: UUID,
    val quantity: BigDecimal,
    val price: BigDecimal,
    val summa: BigDecimal,
    val cellId: UUID?,
    val lotId: UUID?,
) {
    constructor(model: OrderingItem) : this(
        model.orderNo,
        model.product.id,
        model.quantity,
        model.price,
        model.summa,
        model.cell?.id,
        model.lot?.id
    )

    fun create_or_update(owner: Ordering): OrderingItem {
        var dbo = DB.find(OrderingItem::class.java)
            .where().eq("owner_id", owner.id)
            .and().eq("order_no", orderNo).findOne()
        val cell = if (cellId != null)
            DB.find(Cell::class.java, cellId)!!
        else
            null
        val lot = if (lotId != null)
            DB.find(Lot::class.java, lotId)!!
        else
            null
        if (dbo == null) {
            dbo = OrderingItem(
                owner,
                orderNo,
                DB.find(Product::class.java, productId)!!,
                quantity,
                price,
                summa,
                cell,
                lot
            )
        } else {
            dbo.product = DB.find(Product::class.java, productId)!!
            dbo.quantity = quantity
            dbo.price = price
            dbo.summa = summa
            dbo.cell = cell
            dbo.lot = lot
        }
        return dbo
    }
}
