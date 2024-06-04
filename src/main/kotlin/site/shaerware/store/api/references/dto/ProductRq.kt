package site.shaerware.store.api.references.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.ProductAPI
import site.shaerware.store.db.models.refs.Product
import site.shaerware.store.db.models.refs.Vendor
import java.util.*

data class ProductRq(
    val name : String,
    val vendorId: UUID,
    val oemCode: String?,
    val sku: String?,
    val barcode: String?,
) :RQ<ProductAPI>, CreateDTO<Product>, UpdateDTO<Product> {
    override fun create(): Product {
        return Product(
            name,
            DB.find(Vendor::class.java, vendorId)!!,
            oemCode,
            sku,
            barcode
        )
    }

    override fun apply(model: Product) {
        model.name = name
        model.vendor = DB.find(Vendor::class.java, vendorId)!!
        model.oemCode = oemCode
        model.sku = sku
        model.barcode = barcode
    }
}