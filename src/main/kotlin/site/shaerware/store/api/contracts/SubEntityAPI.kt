package site.shaerware.store.api.contracts

import io.ebean.annotation.Transactional
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.*
import site.shaerware.store.db.models.refs.RefEntity
import site.shaerware.store.db.models.refs.SubEntityCt

interface SubEntityAPI<P_ID, P_API, P_ID_API, P_DBO, DBO, R_DTO, L_DTO, C_DTO> : SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>
where P_API : EntityApiItem<P_DBO>,
      P_ID_API : EntityByIdApiItem<P_ID, P_API, P_DBO>,
      P_DBO : RefEntity,
      DBO : RefEntity,
      DBO : SubEntityCt<P_DBO>,
      R_DTO : RS<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      R_DTO : ReadDTO<DBO>,
      L_DTO : RS<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      L_DTO : ListDTO<DBO>,
      C_DTO : RQ<out SubEntityApiItem<P_ID, P_API, P_ID_API, P_DBO, DBO>>,
      C_DTO : CreateSubDTO<DBO, P_DBO>
{
    val parent : P_ID_API

    fun rs(dbo: DBO) : R_DTO
    fun list() : List<L_DTO>

    @Transactional
    fun create(dto : C_DTO) : R_DTO {
        val dbo = dto.create(parent.item())
        dbo.save()
        return rs(dbo)
    }
}