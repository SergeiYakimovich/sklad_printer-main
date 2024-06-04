package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.references.VendorAPI
import site.shaerware.store.db.models.refs.Vendor
import java.util.UUID

data class VendorRs(
    val id: UUID,
    val name: String
) : RS<VendorAPI>, ReadDTO<Vendor>, ListDTO<Vendor> {
    constructor(model: Vendor):this(model.id, model.name)
}
