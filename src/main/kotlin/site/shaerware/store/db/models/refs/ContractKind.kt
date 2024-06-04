package site.shaerware.store.db.models.refs

import io.ebean.annotation.DbEnumType
import io.ebean.annotation.DbEnumValue

enum class ContractKind(private val dbValue: Int) {
    Buyer(1),
    Seller(2);

    @DbEnumValue(storage = DbEnumType.INTEGER)
    fun value(): Int {
        return dbValue
    }
}