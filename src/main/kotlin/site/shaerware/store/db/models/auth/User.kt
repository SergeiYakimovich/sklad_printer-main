package site.shaerware.store.db.models.auth

import io.ebean.DB
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.RefEntity
import javax.persistence.*

@Entity
@Table(name = "auth_users")
@Index(columnNames = ["name"], unique = true)
class User(
    var name: String,
    var passwordHash: String,
): RefEntity() {
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "auth_user_roles",
        joinColumns = arrayOf(JoinColumn(name = "owner_id", referencedColumnName = "id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "id"))
    )
    var roles: List<Role> = emptyList()

    override fun save() {
        super.save()
        if (roles.isNotEmpty()) {
            val links = roles.map { r -> r.id }
            DB.find(UserRole::class.java)
                .where().eq("owner_id", id)
                .and().notIn("role_id", links)
                .delete()
        }
    }
}
