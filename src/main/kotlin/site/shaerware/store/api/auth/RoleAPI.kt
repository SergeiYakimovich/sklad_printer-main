package site.shaerware.store.api.auth

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.auth.dto.RoleRq
import site.shaerware.store.api.auth.dto.RoleRs
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.contracts.EntityByIdAPI
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.db.models.auth.Role
import java.util.*

@Resource("/role")
class RoleAPI(
    override val search: String? = null
) : EntityAPI<Role, RoleRs, RoleRs, RoleRq>, Search<Role> {

    override fun rs(dbo: Role): RoleRs {
        return RoleRs(dbo)
    }

    override fun <ID> get(id: ID): Role {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Role::class.java, uuid)!!
    }

    override fun list(): List<RoleRs> {
        var query = DB.find(Role::class.java)
        query = applySearch(query, "name", Search.Mode.Begin, false)
        return query.findList().map { r -> rs(r) }
    }

    @Resource("{id}")
    class Id(
        override val parent: RoleAPI = RoleAPI(),
        override val id: String
    ) : EntityByIdAPI<String, RoleAPI, Role, RoleRs, RoleRs, RoleRq, RoleRq>
}
