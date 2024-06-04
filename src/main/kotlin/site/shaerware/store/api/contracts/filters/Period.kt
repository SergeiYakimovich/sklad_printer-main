package site.shaerware.store.api.contracts.filters

import io.ebean.Model
import io.ebean.Query
import java.sql.Date

interface Period<DBO: Model> {
    val from:String?
    val to:String?

    fun applyPeriod(query: Query<DBO>, field: String) : Query<DBO> {
        var result = query
        if (!from.isNullOrEmpty())
            result = result.where().ge(field, Date.valueOf(from)).query()
        if (!to.isNullOrEmpty())
            result = result.where().le(field, Date.valueOf(to)).query()
        return result
    }
}