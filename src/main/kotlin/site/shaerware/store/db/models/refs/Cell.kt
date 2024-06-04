package site.shaerware.store.db.models.refs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.Index
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Index(columnNames = ["owner_id", "code"], unique = true)
class Cell(
    @JsonIgnore
    @ManyToOne
    override var owner: Store,

    var code: String,

    var depth: BigDecimal?,
    var width: BigDecimal?,
    var height: BigDecimal?,
    var maxWeight: BigDecimal?,
    var maxVolume: BigDecimal?,
) : RefEntity(), SubEntityCt<Store>