package site.shaerware.store.db.models.auth

import io.ebean.Model
import io.ebean.annotation.Index
import site.shaerware.store.db.models.refs.SubEntityCt
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "auth_user_roles")
@Index(columnNames = ["owner_id", "role_id"], unique = true)
class UserRole(
    @ManyToOne
    override var owner: User,

    @ManyToOne
    var role: Role,
) : Model(), SubEntityCt<User>
