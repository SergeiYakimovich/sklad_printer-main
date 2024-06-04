package site.shaerware.store.db.models.refs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.DbDefault
import io.ebean.annotation.Index
import io.ebean.config.dbplatform.DbDefaultValue
import java.util.Date
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Index(columnNames = ["owner_id", "code"], unique = true)
@Index(columnNames = ["owner_id", "kind", "code"], unique = true)
class Contract(
    @JsonIgnore
    @ManyToOne
    override var owner: Counterparty,

    var kind: ContractKind,

    var code: String,

    var name: String?,

    var date: Date,

    var expiration: Date?,

    var deferment: Int?
) : RefEntity(), SubEntityCt<Counterparty>
