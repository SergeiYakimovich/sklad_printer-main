package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.references.ProductAPI
import site.shaerware.store.db.models.refs.Product
import java.util.*

data class ProductReadRs(
    val id: UUID,
    val name: String,
    val vendor: VendorRs,
    val oemCode: String?,
    val sku: String?,
    val barcode: String?,
    val lots: List<LotRs>,
    val images: List<ProductImageTO>
) : RS<ProductAPI>, ReadDTO<Product> {
    constructor(model: Product) : this(
        model.id,
        model.name,
        VendorRs(model.vendor),
        model.oemCode,
        model.sku,
        model.barcode,
        model.lots.map { l -> LotRs(l) },
        model.images.map { i -> ProductImageTO(i) }
    )
}
