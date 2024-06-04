package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.dto.SubEntityDTO
import site.shaerware.store.api.references.ProductAPI
import site.shaerware.store.db.models.refs.Lot
import java.math.BigDecimal
import java.util.Date
import java.util.*

data class LotRs(
    val id: UUID,
    override val ownerId : UUID,
    val date: Date,
    val documentId: UUID,
    val oemCode: String?,
    val barcode: String?,
    val incomingQuantity: BigDecimal,
    val price: BigDecimal,
    val priceCurrency: Currency?,
    val weight: BigDecimal?,
    val cost: BigDecimal?,
    val costCurrency: Currency?,
):RS<ProductAPI.Id.LotAPI>, SubEntityDTO, ReadDTO<Lot>, ListDTO<Lot> {
    constructor(model: Lot): this(
        model.id,
        model.owner.id,
        model.date,
        model.document.id,
        model.oemCode,
        model.barcode,
        model.incomingQuantity,
        model.price,
        model.priceCurrency,
        model.weight,
        model.cost,
        model.costCurrency
    )
}
