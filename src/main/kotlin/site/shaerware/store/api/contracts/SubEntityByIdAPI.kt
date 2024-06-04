package site.shaerware.store.api.contracts

import io.ebean.annotation.Transactional
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.*
import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt

interface SubEntityByIdAPI<P_ID, P_API, P_ID_API, P_DBO, ID, P, DBO, R_DTO, L_DTO, C_DTO, U_DTO> :
    SubEntityByIdApiItem<P_ID, P_API, P_ID_API, P_DBO, ID, P, DBO>
where P : SubEntityAPI<P_ID, P_API, P_ID_API, P_DBO, DBO, R_DTO, L_DTO, C_DTO>,
      P_API : EntityApiItem<P_DBO>,
      P_ID_API : EntityByIdApiItem<P_ID, P_API, P_DBO>,
      P_DBO : RefEntity,
      DBO : RefEntity,
      DBO : SubEntityCt<P_DBO>,
      R_DTO : RS<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      R_DTO : ReadDTO<DBO>,
      L_DTO : RS<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      L_DTO : ListDTO<DBO>,
      C_DTO : RQ<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      C_DTO : CreateSubDTO<DBO, P_DBO>,
      U_DTO : RQ<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      U_DTO : UpdateDTO<DBO> {
    fun read(): R_DTO {
        val dbo = parent.get(id)
        return parent.rs(dbo)
    }

    @Transactional
    fun update(dto: U_DTO): R_DTO {
        val dbo = parent.get(id)
        dto.apply(dbo)
        dbo.save()
        return parent.rs(dbo)
    }

    @Transactional
    fun delete(): R_DTO {
        val dbo = parent.get(id)
        dbo.delete()
        return parent.rs(dbo)
    }
}