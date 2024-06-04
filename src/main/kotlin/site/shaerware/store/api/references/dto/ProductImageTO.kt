package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.dto.SubEntityDTO
import site.shaerware.store.db.models.refs.ProductImage
import java.util.UUID

data class ProductImageTO(
    val id: UUID,
    override val ownerId: UUID,
    val url: String,
    val type: String?,
    val mimeType: String,
) :SubEntityDTO, ReadDTO<ProductImage>, ListDTO<ProductImage> {
    constructor(model: ProductImage): this(
        model.id,
        model.owner.id,
        model.url,
        model.type,
        model.mimeType
    )
}
