package site.shaerware.store.db.models.docs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.Model
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.Cell
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import javax.persistence.*

@Index(columnNames = ["owner_id", "order_no"], unique = true)
@Entity
class OrderingItem(
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id")
    override var owner: Ordering,

    @JoinColumn(name = "order_no")
    override var orderNo: Int,

    @ManyToOne
    var product: Product,

    var quantity: BigDecimal,

    var price: BigDecimal,

    var summa: BigDecimal,

    @ManyToOne
    var cell: Cell?,

    @ManyToOne
    var lot: Lot?,
) : Model(), DocItemCt<Ordering> {
    @EmbeddedId
    var itemId: DocItemId = DocItemId(owner.id, orderNo)
}