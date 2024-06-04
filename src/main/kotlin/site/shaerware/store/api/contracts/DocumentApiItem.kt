package site.shaerware.store.api.contracts

import site.shaerware.store.db.models.docs.DocEntity

interface DocumentApiItem<DBO>: ApiItem
where DBO: DocEntity
{
    fun <ID> get(id: ID) : DBO
}