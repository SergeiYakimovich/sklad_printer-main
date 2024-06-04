package site.shaerware.store.db.models.docs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.Model
import io.ebean.annotation.DbDefault
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import javax.persistence.*

//@Index(columnNames = ["owner_id", "order_no"], unique = true)
@Entity
//@IdClass(DocItemId::class)
class IncomingItem(
//    @Id
    @JsonIgnore
    @ManyToOne
//    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "owner_id")
    override var owner: Incoming,

//    @Id
    @JoinColumn(name = "order_no")
    override var orderNo: Int,

    var oemCode: String?,

    @ManyToOne
    var product: Product,

    @DbDefault("0")
    var quantity: BigDecimal,

    @DbDefault("0")
    var price: BigDecimal,

    @DbDefault("0")
    var weight: BigDecimal,

    @DbDefault("0")
    var cost: BigDecimal,

    @ManyToOne
    var lot: Lot?
) : Model(), DocItemCt<Incoming> {
    @EmbeddedId
    var itemId: DocItemId = DocItemId(owner.id, orderNo)

}