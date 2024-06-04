package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateSubDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.StoreAPI
import site.shaerware.store.api.references.StoreAPI.Id.CellAPI
import site.shaerware.store.db.models.refs.Cell
import site.shaerware.store.db.models.refs.Store
import java.math.BigDecimal

data class CellRq(
    val code: String,
    val depth: BigDecimal?,
    val width: BigDecimal?,
    val height: BigDecimal?,
    val maxWeight: BigDecimal?,
    val maxVolume: BigDecimal?,
):CreateSubDTO<Cell,Store>, UpdateDTO<Cell>, RQ<CellAPI> {
    override fun create(owner: Store): Cell {
        return Cell(
            owner,
            code, depth, width, height, maxWeight, maxVolume
        )
    }

    override fun apply(model: Cell) {
        model.code = code
        model.depth = depth
        model.width = width
        model.height = height
        model.maxWeight = maxWeight
        model.maxVolume = maxVolume
    }
}