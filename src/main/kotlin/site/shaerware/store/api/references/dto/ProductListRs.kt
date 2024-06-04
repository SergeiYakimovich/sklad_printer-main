package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.references.ProductAPI
import site.shaerware.store.db.models.refs.Product
import java.util.*

data class ProductListRs(
    val id: UUID,
    val name: String,
    val vendor: VendorRs,
    val oemCode: String?,
    val sku: String?,
    val barcode: String?,
    val images: List<ProductImageTO>
) : RS<ProductAPI>, ListDTO<Product> {
    constructor(model: Product) : this(
        model.id,
        model.name,
        VendorRs(model.vendor),
        model.oemCode,
        model.sku,
        model.barcode,
        model.images.map { i -> ProductImageTO(i) })
}

