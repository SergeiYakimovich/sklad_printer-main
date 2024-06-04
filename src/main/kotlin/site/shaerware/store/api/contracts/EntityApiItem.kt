package site.shaerware.store.api.contracts

import site.shaerware.store.db.models.refs.RefEntity

interface EntityApiItem<DBO>: ApiItem
where DBO : RefEntity
{
    fun <ID> get(id: ID) : DBO
}