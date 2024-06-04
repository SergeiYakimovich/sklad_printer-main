package site.shaerware.store.db.models.refs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.DbDefault
import io.ebean.annotation.Index
import io.ebean.config.dbplatform.DbDefaultValue
import site.shaerware.store.db.models.docs.Document
import java.math.BigDecimal
import java.util.Date
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Index(columnNames = ["date"])
@Index(columnNames = ["oem_code"])
@Index(columnNames = ["owner_id", "barcode"], unique = true)
class Lot(
    @JsonIgnore
    @ManyToOne(cascade = [CascadeType.ALL])
    override var owner: Product,

    var date: Date,

    @ManyToOne
    var document: Document,

    var oemCode: String?,

    var barcode: String?,

    var incomingQuantity: BigDecimal,

    var price: BigDecimal,

    var priceCurrency: Currency?,

    var weight: BigDecimal?,

    var cost: BigDecimal?,

    var costCurrency: Currency?,
) : RefEntity(), SubEntityCt<Product>