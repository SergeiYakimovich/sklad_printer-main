package site.shaerware.store.api.contracts

import site.shaerware.store.db.models.refs.RefEntity

interface EntityByIdApiItem<ID, P, DBO>:ApiItem
where P: EntityApiItem<DBO>,
    DBO: RefEntity
{
    val parent: P
    val id: ID

    fun item() : DBO {
        return parent.get(id)
    }
}