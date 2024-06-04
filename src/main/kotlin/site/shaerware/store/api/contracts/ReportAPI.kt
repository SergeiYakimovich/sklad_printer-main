package site.shaerware.store.api.contracts

import io.ktor.server.application.*
import site.shaerware.store.api.contracts.ApiItem

interface ReportAPI : ApiItem {
    fun get(call: ApplicationCall): Any
}
