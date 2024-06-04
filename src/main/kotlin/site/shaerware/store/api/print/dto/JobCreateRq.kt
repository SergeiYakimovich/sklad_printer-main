package site.shaerware.store.api.print.dto

import io.ebean.DB
import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.db.models.print.Host
import site.shaerware.store.db.models.print.PrintJob
import site.shaerware.store.db.models.print.Printer
import java.util.*

data class JobCreateRq(
    val printerId: UUID,
    val data: String
) : RQ<PrintAPI.PrintJobsAPI> {
    fun create(hostId: UUID): PrintJob {
        return PrintJob(
            DB.find(Host::class.java, hostId)!!,
            DB.find(Printer::class.java, printerId)!!,
            Date(),
            null,
            data)
    }
}
