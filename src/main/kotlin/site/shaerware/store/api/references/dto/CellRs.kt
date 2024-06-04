package site.shaerware.store.api.references.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.dto.SubEntityDTO
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.api.references.StoreAPI.Id.CellAPI
import site.shaerware.store.db.models.refs.Cell
import java.math.BigDecimal
import java.util.*

data class CellRs(
    val id: UUID,
    override val ownerId: UUID,
    val code: String,
    val depth: BigDecimal?,
    val width: BigDecimal?,
    val height: BigDecimal?,
    val maxWeight: BigDecimal?,
    val maxVolume: BigDecimal?,
):SubEntityDTO, RS<CellAPI>, ReadDTO<Cell>, ListDTO<Cell> {
    constructor(model: Cell):this(
        model.id,
        model.owner.id,
        model.code,
        model.depth,
        model.width,
        model.height,
        model.maxWeight,
        model.maxVolume
    )
}