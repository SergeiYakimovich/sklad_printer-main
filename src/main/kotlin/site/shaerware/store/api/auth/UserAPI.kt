package site.shaerware.store.api.auth

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.auth.dto.UserRq
import site.shaerware.store.api.auth.dto.UserRs
import site.shaerware.store.api.contracts.EntityAPI
import site.shaerware.store.api.contracts.EntityByIdAPI
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.db.models.auth.User
import java.util.*

@Resource("/user")
class UserAPI(
    override val search: String? = null
) : EntityAPI<User, UserRs, UserRs, UserRq>, Search<User> {

    override fun rs(dbo: User): UserRs {
        return UserRs(dbo)
    }

    override fun <ID> get(id: ID): User {
        val uuid = UUID.fromString(id.toString())
        return DB.find(User::class.java, uuid)!!
    }

    override fun list(): List<UserRs> {
        var query = DB.find(User::class.java)
        query = applySearch(query, "name", Search.Mode.Begin, false)
        return query.findList().map { u -> rs(u) }
    }

    @Resource("{id}")
    class Id(
        override val parent: UserAPI = UserAPI(),
        override val id: String
    ) : EntityByIdAPI<String, UserAPI, User, UserRs, UserRs, UserRq, UserRq>

}
