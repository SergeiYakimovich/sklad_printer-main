package site.shaerware.store.api.reports

import io.ktor.resources.*
import io.ktor.server.application.*
import site.shaerware.store.api.contracts.ReportAPI
import site.shaerware.store.api.reports.dto.VersionRs

@Resource("/version")
class VersionAPI : ReportAPI {
    override fun get(call: ApplicationCall): Any {
        return VersionRs(call.application.environment.developmentMode)
    }
}
