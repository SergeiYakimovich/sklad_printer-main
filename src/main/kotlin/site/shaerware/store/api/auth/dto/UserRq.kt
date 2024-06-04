package site.shaerware.store.api.auth.dto

import io.ebean.DB
import io.ebean.annotation.Transactional
import site.shaerware.store.api.auth.UserAPI
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.db.models.auth.Role
import site.shaerware.store.utils.Password
import java.util.*
import site.shaerware.store.db.models.auth.User as DbUser

data class UserRq(
    val name: String,
    val password: String?,
    val roles: List<UUID>?
) : RQ<UserAPI>, CreateDTO<DbUser>, UpdateDTO<DbUser> {

    override fun create(): DbUser {
        val obj = DbUser(name, Password.hash(password!!))
        if (roles != null)
            obj.roles = roles.map { DB.find(Role::class.java, it)!! }
        return obj
    }

    override fun apply(model: DbUser) {
        model.name = name
        if (password != null)
            model.passwordHash = Password.hash(password)
        if (roles != null)
            model.roles = roles.map { DB.find(Role::class.java, it)!! }
    }

}
