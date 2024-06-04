package site.shaerware.store.api.auth.dto

import site.shaerware.store.api.auth.RoleAPI
import site.shaerware.store.api.auth.dto.px.DocPxTO
import site.shaerware.store.api.auth.dto.px.RefPxTO
import site.shaerware.store.api.auth.dto.px.RepPxTO
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.db.models.auth.Role

data class RoleRq(
    val name: String,
    val references: List<RefPxTO>?,
    val documents: List<DocPxTO>?,
    val reports: List<RepPxTO>?,
): RQ<RoleAPI>, CreateDTO<Role>, UpdateDTO<Role> {

    override fun create(): Role {
        val dbo = Role(name)
        if (references != null)
            dbo.references = references.map { p -> p.create(dbo) }
        if (documents != null)
            dbo.documents = documents.map { p -> p.create(dbo) }
        if (reports != null)
            dbo.reports = reports.map { p -> p.create(dbo) }
        return dbo
    }

    override fun apply(model: Role) {
        model.name = name
        if (references != null)
            model.references = references.map { p -> p.create_or_update(model) }
        if (documents != null)
            model.documents = documents.map { p -> p.create_or_update(model) }
        if (reports != null)
            model.reports = reports.map { p -> p.create_or_update(model) }
    }

}
