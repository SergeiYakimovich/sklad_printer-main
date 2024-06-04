package site.shaerware.store.api.contracts

import io.ebean.annotation.Transactional
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.filters.Period
import site.shaerware.store.db.models.docs.DocEntity

interface DocumentAPI<DBO, R_DTO, L_DTO, C_DTO> : DocumentApiItem<DBO>, Period<DBO>
        where DBO : DocEntity,
              R_DTO : ReadDTO<DBO>,
              R_DTO : RS<out DocumentAPI<DBO, R_DTO, L_DTO, C_DTO>>,
              L_DTO : ListDTO<DBO>,
              L_DTO : RS<out DocumentAPI<DBO, R_DTO, L_DTO, C_DTO>>,
              C_DTO : CreateDTO<DBO>,
              C_DTO : RQ<out DocumentAPI<DBO, R_DTO, L_DTO, C_DTO>>
{
    fun rs(dbo: DBO): R_DTO
    fun list(): List<L_DTO>

    @Transactional
    fun create(dto: C_DTO): R_DTO {
        val dbo = dto.create()
        dbo.save()
        return rs(dbo)
    }
}
