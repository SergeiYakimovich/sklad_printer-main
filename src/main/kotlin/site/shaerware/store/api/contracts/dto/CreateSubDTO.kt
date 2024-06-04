package site.shaerware.store.api.contracts.dto

import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt

interface CreateSubDTO<T, O>
        where T : RefEntity,
              O : RefEntity,
              T : SubEntityCt<O> {
    fun create(owner: O): T
}
