package site.shaerware.store.db.models.refs

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ebean.annotation.Index
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
@Index(columnNames = ["owner_id", "name"], unique = true)
@Index(columnNames = ["owner_id", "bank"])
@Index(columnNames = ["bik"])
@Index(columnNames = ["account_number"])
class BankAccount(
    @JsonIgnore
    @ManyToOne
    override var owner: Counterparty,

    var name : String,

    var bank: String,
    var bik: String,
    var correspondentAcc: String,
    var accountNumber :String
) : RefEntity(), SubEntityCt<Counterparty>