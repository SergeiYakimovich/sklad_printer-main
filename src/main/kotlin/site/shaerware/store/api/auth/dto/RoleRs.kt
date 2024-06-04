package site.shaerware.store.api.auth.dto

import site.shaerware.store.api.auth.RoleAPI
import site.shaerware.store.api.auth.dto.px.DocPxTO
import site.shaerware.store.api.auth.dto.px.RefPxTO
import site.shaerware.store.api.auth.dto.px.RepPxTO
import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import java.util.*
import site.shaerware.store.db.models.auth.Role

data class RoleRs(
    val id: UUID,
    val name: String,
    val references: List<RefPxTO>,
    val documents: List<DocPxTO>,
    val reports: List<RepPxTO>
) : RS<RoleAPI>, ReadDTO<Role>, ListDTO<Role> {
    constructor(model: Role) : this(
        model.id,
        model.name,
        model.references.map { p -> RefPxTO(p) },
        model.documents.map { p -> DocPxTO(p) },
        model.reports.map { p -> RepPxTO(p) }
    )
}
