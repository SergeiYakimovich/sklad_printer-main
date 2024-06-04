package site.shaerware.store.api.auth.dto.px

import io.ebean.DB
import site.shaerware.store.db.models.auth.DocumentPx
import site.shaerware.store.db.models.auth.ReferencePx
import site.shaerware.store.db.models.auth.Role
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.auth.enums.ReferenceEnum
import site.shaerware.store.db.models.auth.ReferencePx as DbReferencePx
import site.shaerware.store.db.models.auth.Role as DbRole
import site.shaerware.store.db.models.auth.enums.ReferenceEnum as EnumReference

data class RefPxTO(
    val entity: String,
    val allowCreate: Boolean?,
    val allowRead: Boolean?,
    val allowUpdate: Boolean?,
    val allowDelete: Boolean?,
    val allowList: Boolean?,
) {

    constructor(model: DbReferencePx) : this(
        model.entity.name,
        model.allowCreate,
        model.allowRead,
        model.allowUpdate,
        model.allowDelete,
        model.allowList
    )

    fun create(owner: DbRole): DbReferencePx {
        return DbReferencePx(
            owner,
            EnumReference.valueOf(entity),
            allowCreate,
            allowRead,
            allowUpdate,
            allowDelete,
            allowList
        )
    }

    fun create_or_update(owner: Role) : ReferencePx {
        var dbo = DB.find(ReferencePx::class.java)
            .where().eq("owner_id", owner.id)
            .and().eq("entity", entity).findOne()
        if (dbo == null) {
            dbo = ReferencePx(
                owner,
                ReferenceEnum.valueOf(entity),
                allowCreate, allowRead, allowUpdate, allowDelete, allowList
            )
        } else {
            dbo.allowCreate = allowCreate
            dbo.allowRead = allowRead
            dbo.allowUpdate = allowUpdate
            dbo.allowDelete = allowDelete
            dbo.allowList = allowList
        }
        return dbo
    }
}
