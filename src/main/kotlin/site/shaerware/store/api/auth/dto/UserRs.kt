package site.shaerware.store.api.auth.dto

import site.shaerware.store.api.auth.UserAPI
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.db.models.auth.User
import java.util.*

data class UserRs(
    val id: UUID,
    val name: String,
    val roles: List<UUID>
) : RS<UserAPI>, ReadDTO<User>, ListDTO<User> {
    constructor(model: User) : this(model.id, model.name, model.roles.map { it.id })
}
