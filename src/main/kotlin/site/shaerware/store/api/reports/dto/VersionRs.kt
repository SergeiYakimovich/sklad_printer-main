package site.shaerware.store.api.reports.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.reports.VersionAPI

data class VersionRs(
    val version: String,
    val development: Boolean,
) : RS<VersionAPI> {
    constructor(development: Boolean) : this(
        System.getProperty("project_version"),
        development
    )
}
