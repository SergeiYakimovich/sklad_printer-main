package site.shaerware.store.api.print.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.db.models.print.PrintJob
import java.util.UUID

data class JobRs (
    val id: UUID,
    val printerId: UUID,
    val data: String,
) : RS<PrintAPI.PrintJobsAPI>, ListDTO<PrintJob> {
    constructor(model: PrintJob):this(model.id, model.printer.id, model.data)
}
