package site.shaerware.store.api.print

import io.ebean.DB
import io.ktor.resources.*
import site.shaerware.store.api.contracts.*
import site.shaerware.store.api.contracts.filters.Search
import site.shaerware.store.api.print.dto.HostRq
import site.shaerware.store.api.print.dto.HostRs
import site.shaerware.store.api.print.dto.PrinterRq
import site.shaerware.store.api.print.dto.PrinterRs
import site.shaerware.store.db.models.print.Host
import site.shaerware.store.db.models.print.Printer
import java.util.UUID

@Resource("/print")
class PrintAPI(
    override val search: String? = null
) : EntityAPI<Host, HostRs, HostRs, HostRq>, Search<Host> {
    override fun rs(dbo: Host): HostRs {
        return HostRs(dbo)
    }

    override fun list(): List<HostRs> {
        var query = DB.find(Host::class.java)
        query = applySearch(query, "name", Search.Mode.Middle)
        return query.findList().map { HostRs(it) }
    }

    override fun <ID> get(id: ID): Host {
        val uuid = UUID.fromString(id.toString())
        return DB.find(Host::class.java, uuid)!!
    }

    @Resource("{hostId}")
    class Id(
        override val parent: PrintAPI = PrintAPI(),
        val hostId: String
    ) : EntityByIdAPI<String, PrintAPI, Host, HostRs, HostRs, HostRq, HostRq> {

        override val id: String
            get() = hostId

        @Resource("/printer")
        class PrinterAPI(
            override val parent: PrintAPI.Id,
            override val search: String? = null
        ) : SubEntityAPI<String, PrintAPI, PrintAPI.Id, Host, Printer, PrinterRs, PrinterRs, PrinterRq>, Search<Printer> {

            override fun rs(dbo: Printer): PrinterRs {
                return PrinterRs(dbo)
            }

            override fun list(): List<PrinterRs> {
                var query = DB.find(Printer::class.java).where().eq("owner_id", parent.item().id).query()
                query = applySearch(query, "name", Search.Mode.Middle)
                return query.findList().map { PrinterRs(it) }
            }

            override fun <ID> get(id: ID): Printer {
                val uuid = UUID.fromString(id.toString())
                return DB.find(Printer::class.java, uuid)!!
            }

            @Resource("{printerId}")
            class Id(
                override val parent: PrinterAPI,
                val printerId: String
            ): SubEntityByIdAPI<String, PrintAPI, PrintAPI.Id, Host, String, PrinterAPI, Printer, PrinterRs, PrinterRs, PrinterRq, PrinterRq> {

                override val id: String
                    get() = printerId
            }
        }
    }

    @Resource("/jobs")
    class PrintJobsAPI: ApiItem {}
}