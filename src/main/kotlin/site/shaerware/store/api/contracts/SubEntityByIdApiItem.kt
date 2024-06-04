package site.shaerware.store.api.contracts

import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt

interface SubEntityByIdApiItem<P_ID, P_API, P_ID_API, P_DBO, ID, P, DBO> : ApiItem
        where P : SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>,
              P_API : EntityApiItem<P_DBO>,
              P_DBO : RefEntity,
              DBO : RefEntity,
              DBO : SubEntityCt<P_DBO> {
    val parent : P
    val id: ID
}