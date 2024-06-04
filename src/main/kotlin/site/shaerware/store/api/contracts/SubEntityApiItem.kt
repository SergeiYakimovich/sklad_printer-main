package site.shaerware.store.api.contracts

import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt

interface SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO> : ApiItem
        where P_API : EntityApiItem<P_DBO>,
              P_DBO : RefEntity,
              DBO : RefEntity,
              DBO : SubEntityCt<P_DBO> {
    fun <ID> get(id: ID): DBO
}