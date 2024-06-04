package site.shaerware.store.db.models.docs

import io.ebean.DB
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.refs.Contract
import site.shaerware.store.db.models.refs.Counterparty
import site.shaerware.store.db.models.refs.Store
import java.util.Date
import java.sql.Timestamp
import java.util.Currency
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Ordering(
    date: Date,
    code: String,
    active: Boolean,

    @ManyToOne
    var counterparty: Counterparty,

    @ManyToOne
    var contract: Contract,

    var currency: Currency,

    @ManyToOne
    var store: Store,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: List<OrderingItem> = emptyList(),

) : DocEntity(date, code, active) {
    override fun type(): DocumentEnum {
        return DocumentEnum.Ordering
    }

    override fun process() {
        // TODO: implement
    }

    override fun save() {
        val itms = items.map { it.orderNo }
        val deleting = DB.find(OrderingItem::class.java)
            .where().eq("owner_id", id)
            .and().notIn("order_no", itms).findList()
        super.save()
        deleting.forEach { it.delete() }
    }
}