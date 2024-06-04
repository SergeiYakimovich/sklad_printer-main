package site.shaerware.store.db.models.auth

import io.ebean.annotation.Index
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt
import javax.persistence.*

@Entity
@Table(name = "auth_documents")
@Index(columnNames = ["owner_id", "entity"], unique = true)
class DocumentPx(
    @ManyToOne(cascade = [CascadeType.ALL])
    override var owner: Role,

    @Enumerated(EnumType.STRING)
    var entity: DocumentEnum,

    var allowCreate: Boolean?,
    var allowRead: Boolean?,
    var allowUpdate: Boolean?,
    var allowActivate: Boolean?,
    var allowDelete: Boolean?,
    var allowList: Boolean?,
) : RefEntity(), SubEntityCt<Role>
