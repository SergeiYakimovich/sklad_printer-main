package site.shaerware.store.db.models.docs

import io.ebean.DB
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.refs.Store
import java.util.Date
import java.sql.Timestamp
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Layouting(
    date: Date,
    code: String,
    active: Boolean,

    @ManyToOne
    var incoming: Incoming,

    @ManyToOne
    var store: Store,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: List<LayoutingItem> = emptyList()

) : DocEntity(date, code, active) {
    override fun type(): DocumentEnum {
        return DocumentEnum.Layouting
    }

    override fun process() {
        TODO()
    }

    override fun save() {
        val itms = items.map { it.orderNo }
        val deleting = DB.find(LayoutingItem::class.java)
            .where().eq("owner_id", id)
            .and().notIn("order_no", itms).findList()
        super.save()
        deleting.forEach { it.delete() }
    }
}