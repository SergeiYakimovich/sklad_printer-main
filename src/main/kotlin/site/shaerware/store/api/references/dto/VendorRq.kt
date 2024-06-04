package site.shaerware.store.api.references.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.references.VendorAPI
import site.shaerware.store.db.models.refs.Vendor

data class VendorRq(
    val name: String
) : RQ<VendorAPI>, CreateDTO<Vendor>, UpdateDTO<Vendor> {
    override fun create(): Vendor {
        val dbo = Vendor(name)
        return dbo
    }

    override fun apply(model: Vendor) {
        model.name = name
    }
}
