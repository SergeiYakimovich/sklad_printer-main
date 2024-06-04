package site.shaerware.store.api.references.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateSubDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.ProductAPI.Id.LotAPI
import site.shaerware.store.db.models.docs.Document
import site.shaerware.store.db.models.refs.Lot
import site.shaerware.store.db.models.refs.Product
import java.math.BigDecimal
import java.util.Date
import java.util.*

data class LotRq(
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
) : RQ<LotAPI>, CreateSubDTO<Lot, Product>, UpdateDTO<Lot> {

    override fun create(owner: Product): Lot {
        return Lot(
            owner,
            date,
            DB.find(Document::class.java, documentId)!!,
            oemCode,
            barcode,
            incomingQuantity,
            price,
            priceCurrency,
            weight,
            cost,
            costCurrency
        )
    }

    override fun apply(model: Lot) {
        model.date = date
        model.document = DB.find(Document::class.java, documentId)!!
        model.oemCode = oemCode
        model.barcode = barcode
        model.incomingQuantity = incomingQuantity
        model.price = price
        model.priceCurrency = priceCurrency
        model.weight = weight
        model.cost = cost
        model.costCurrency = costCurrency
    }
}