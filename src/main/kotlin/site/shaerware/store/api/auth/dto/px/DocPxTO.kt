package site.shaerware.store.api.auth.dto.px

import io.ebean.DB
import site.shaerware.store.db.models.auth.DocumentPx
import site.shaerware.store.db.models.auth.Role
import site.shaerware.store.db.models.auth.enums.DocumentEnum

data class DocPxTO(
    val entity: String,
    val allowCreate: Boolean?,
    val allowRead: Boolean?,
    val allowUpdate: Boolean?,
    val allowActivate: Boolean?,
    val allowDelete: Boolean?,
    val allowList: Boolean?,
) {

    constructor(model: DocumentPx) : this(
        model.entity.name,
        model.allowCreate,
        model.allowRead,
        model.allowUpdate,
        model.allowActivate,
        model.allowDelete,
        model.allowList
    )

    fun create(owner: Role): DocumentPx {
        return DocumentPx(
            owner,
            DocumentEnum.valueOf(entity),
            allowCreate,
            allowRead,
            allowUpdate,
            allowActivate,
            allowDelete,
            allowList
        )
    }

    fun create_or_update(owner: Role) : DocumentPx {
        var dbo = DB.find(DocumentPx::class.java)
            .where().eq("owner_id", owner.id)
            .and().eq("entity", entity).findOne()
        if (dbo == null) {
            dbo = DocumentPx(
                owner,
                DocumentEnum.valueOf(entity),
                allowCreate, allowRead, allowUpdate, allowActivate, allowDelete, allowList
            )
        } else {
            dbo.allowCreate = allowCreate
            dbo.allowRead = allowRead
            dbo.allowUpdate = allowUpdate
            dbo.allowActivate = allowActivate
            dbo.allowDelete = allowDelete
            dbo.allowList = allowList
        }
        return dbo
    }
}
