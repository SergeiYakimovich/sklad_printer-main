package site.shaerware.store.api.contracts

import site.shaerware.store.db.models.docs.DocEntity

interface DocumentByIdApiItem<ID, P, DBO> : ApiItem
where P : DocumentApiItem<DBO>,
DBO: DocEntity
{
    val parent: P
    val id: ID

    fun item() : DBO {
        return parent.get(id)
    }
}