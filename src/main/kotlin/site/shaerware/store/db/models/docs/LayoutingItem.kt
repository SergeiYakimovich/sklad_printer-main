package site.shaerware.store.db.models.docs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.Model
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.Cell
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import javax.persistence.*

@Entity
class LayoutingItem(
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id")
    override var owner: Layouting,

    @JoinColumn(name = "order_no")
    override var orderNo: Int,

    @ManyToOne
    var from : Cell?,

    @ManyToOne
    var to: Cell?,

    @ManyToOne
    var product: Product,

    @ManyToOne
    var lot: Lot?,

    var quantity: BigDecimal,
) : Model(), DocItemCt<Layouting> {
    @EmbeddedId
    var itemId: DocItemId = DocItemId(owner.id, orderNo)
}