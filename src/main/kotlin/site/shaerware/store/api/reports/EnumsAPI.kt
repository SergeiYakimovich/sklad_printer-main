package site.shaerware.store.api.reports

import io.ktor.resources.*
import io.ktor.server.application.*
import site.shaerware.store.api.contracts.ApiItem
import site.shaerware.store.api.contracts.ReportAPI
import site.shaerware.store.db.models.auth.enums.DocumentEnum
import site.shaerware.store.db.models.auth.enums.ReferenceEnum
import site.shaerware.store.db.models.auth.enums.ReportEnum

@Resource("/enums")
class EnumsAPI : ApiItem {

    @Resource("/permissions")
    class PermissionsAPI(val parent: EnumsAPI = EnumsAPI()) {

        @Resource("/documents")
        class DocumentsAPI(val parent: PermissionsAPI = PermissionsAPI()) : ReportAPI {
            override fun get(call: ApplicationCall): Any {
                return DocumentEnum.values().map { e -> e.name }
            }
        }

        @Resource("/references")
        class ReferencesAPI(val parent: PermissionsAPI = PermissionsAPI()) : ReportAPI {
            override fun get(call: ApplicationCall): Any {
                return ReferenceEnum.values().map { e -> e.name }
            }
        }

        @Resource("/reports")
        class ReportsAPI(val parent: PermissionsAPI = PermissionsAPI()) : ReportAPI {
            override fun get(call: ApplicationCall): Any {
                return ReportEnum.values().map { e -> e.name }
            }
        }
    }
}