package site.shaerware.store.db.models.auth.enums

import site.shaerware.store.api.reports.VersionAPI
import kotlin.reflect.KClass

enum class ReportEnum(val cls: KClass<*>) {
    Version(VersionAPI::class)
}