package site.shaerware.store.api.contracts

import io.ebean.annotation.Transactional
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.db.models.refs.RefEntity

interface EntityByIdAPI<ID, P, DBO, R_DTO, L_DTO, C_DTO, U_DTO> : EntityByIdApiItem<ID, P, DBO>
    where DBO : RefEntity,
          P: EntityAPI<DBO, R_DTO, L_DTO, C_DTO>,
          R_DTO: RS<P>,
          R_DTO : ReadDTO<DBO>,
          L_DTO : RS<P>,
          L_DTO: ListDTO<DBO>,
          C_DTO : RQ<P>,
          C_DTO: CreateDTO<DBO>,
          U_DTO: RQ<P>,
          U_DTO: UpdateDTO<DBO>
{
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