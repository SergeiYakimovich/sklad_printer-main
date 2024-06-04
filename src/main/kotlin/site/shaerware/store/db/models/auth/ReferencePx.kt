package site.shaerware.store.db.models.auth

import io.ebean.annotation.Index
import site.shaerware.store.db.models.auth.enums.ReferenceEnum
import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt
import javax.persistence.*

@Entity
@Table(name = "auth_references")
@Index(columnNames = ["owner_id", "entity"], unique = true)
class ReferencePx(
    @ManyToOne(cascade = [CascadeType.ALL])
    override var owner: Role,

    @Enumerated(EnumType.STRING)
    var entity: ReferenceEnum,

    var allowCreate: Boolean?,
    var allowRead: Boolean?,
    var allowUpdate: Boolean?,
    var allowDelete: Boolean?,
    var allowList: Boolean?,
) :RefEntity(), SubEntityCt<Role>
