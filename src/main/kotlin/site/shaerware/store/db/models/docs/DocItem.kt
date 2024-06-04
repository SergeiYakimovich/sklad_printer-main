package site.shaerware.store.db.models.docs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.Model
import io.ebean.annotation.Index
import javax.persistence.CascadeType
import javax.persistence.ManyToOne
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Index(columnNames = ["owner_id", "order_no"], unique = true)
open class DocItem<T: DocEntity>(
    @JsonIgnore
    @ManyToOne(cascade = [CascadeType.ALL])
    var owner: T,

    var orderNo: Int,
) : Model()